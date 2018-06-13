package com.lxr.iot.plugin.mysql;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.lxr.iot.enums.ConfirmStatus;
import com.lxr.iot.plugin.mysql.entity.*;
import com.lxr.iot.plugin.mysql.enums.ClientState;
import com.lxr.iot.plugin.mysql.service.*;
import com.lxr.iot.server.bean.RetainMessage;
import com.lxr.iot.server.bean.SendMqttMessage;
import com.lxr.iot.server.bean.SessionMessage;
import com.lxr.iot.server.bean.WillMeaasge;
import com.lxr.iot.server.plugins.MessageDataBasePlugin;
import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author jason
 * @package com.lxr.iot.plugin.mysql
 * @Description
 */
@Slf4j
public class MysqlDataBasePlugin implements MessageDataBasePlugin {

    @Resource
    private IClientService clientService;

    @Resource
    private ISubService subService;

    @Resource
    private IRetainService retainService;

    @Resource
    private IWillMsgService willMsgService;

    @Resource
    private IAckMsgService ackMsgService;

    @Resource
    private IReceiveMsgService receiveMsgService;

    @Override
    public void onlineDevice(String clientId, String serverNode) {

       EntityWrapper entityWrapper = new EntityWrapper();
       entityWrapper.eq("clientid",clientId);
       Client client =  clientService.selectOne(entityWrapper);
       if(null!= client){
           client.setOnlineAt(new Date());
           client.updateById();
       }else{
           client = new Client();
           client.setOnlineAt(new Date());
           client.setClientid(clientId);
           client.setState(ClientState.ONLINE);
           client.setNode(serverNode);
           client.insert();
       }
    }

    @Override
    public void offlineDevice(String clientId, String serverNode) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("clientid",clientId);
        Client client =  clientService.selectOne(entityWrapper);
        if(null!= client){
            client.setOfflineAt(new Date());
            client.updateById();
        }
    }

    @Override
    public Boolean saveRetainMessage(String topic, RetainMessage retainMessage, boolean isClean) {
        Retain retain = new Retain();
        retain.setPayload(retainMessage.getString());
        retain.setQos(retainMessage.getQoS().value());
        retain.setTopic(topic);
        retain.insert();
        return true;
    }

    @Override
    public Collection<RetainMessage> getRetainMessage(String topic) {
        Wrapper wrapper = new EntityWrapper<>();
        wrapper.eq("topic",topic);
        List<Retain> retainList = retainService.selectList(wrapper);
        List<RetainMessage> retainMessages = new ArrayList<>();
        for (Retain  retain : retainList){
         RetainMessage retainMessage =    RetainMessage.builder().byteBuf(retain.getPayload().getBytes()).qoS(MqttQoS.valueOf(retain.getQos())).build();
         retainMessages.add(retainMessage);
        }
        return retainMessages;
    }







    @Override
    public Boolean saveMessage(SendMqttMessage message) {
        Msg msg = new Msg();
        msg.setMsgid(String.valueOf(message.getMessageId()));
        msg.setQos(message.getQos().value());
        msg.setRetain(message.isRetain()?1:0);
        msg.setTopic(message.getTopic());
        msg.setSender(message.getDeviceId());
        msg.setPayload(new String(message.getByteBuf()));
        return  msg.insert();
    }

    @Override
    public Boolean saveAckedMessage(String clientId, String topic, String packageId) {

        Acked acked  = new Acked();
        acked.setClientid(clientId);
        acked.setTopic(topic);
        acked.setMid(packageId);
        return acked.insert();
    }

    @Override
    public Boolean saveSub(String clientId, String[] topics, Integer qos) {
        List<Sub> subs = new ArrayList<>();
        for (String topic : topics){
            Sub sub = new Sub();
            sub.setClientid(clientId);
            sub.setTopic(topic);
            sub.setQos(qos);
            subs.add(sub);
        }
        return subService.saveSub(subs);
    }

    @Override
    public Boolean delSub(String clientId, String[] topics) {
        return  subService.delSub(clientId,topics);
    }

    @Override
    public void clearSub(String clientId) {
        Wrapper<Sub> wrapper = new EntityWrapper<>();
        wrapper.eq("clientid",clientId);
        subService.delete(wrapper);
    }

    @Override
    public Collection<String> getSubClients(String[] topics) {
        return subService.selectSubClients(topics);
    }

    @Override
    public void saveSessionMsg(String deviceId, SessionMessage sessionMessage) {


    }

    @Override
    public Set<SessionMessage> getSessionMsg(String deviceId) {
        Set<SessionMessage> sessioinMsgs = new HashSet<>();
        Wrapper<AckMsg> wrapper = new EntityWrapper<>();
        wrapper.eq("deviceId",deviceId);
        List<AckMsg> ackMsgList =  ackMsgService.selectList(wrapper);
        for (AckMsg msg:ackMsgList){
            SessionMessage sesMsg = SessionMessage.builder()
                   .byteBuf(msg.getContent().getBytes())
                   .qoS(MqttQoS.valueOf(msg.getQos()))
                   .topic(msg.getTopic())
                   .build();
           sessioinMsgs.add(sesMsg);

        }
        return sessioinMsgs;
    }

    @Override
    public SendMqttMessage getClientAckMessage(String deviceId, int messageId) {
        Wrapper<AckMsg> wrapper = new EntityWrapper<>();
        wrapper.eq("deviceId",deviceId).and().eq("mid",messageId);
        AckMsg msg = ackMsgService.selectOne(wrapper);
        return SendMqttMessage.builder()
                .byteBuf(msg.getContent().getBytes())
                .confirmStatus(ConfirmStatus.valueOf(msg.getConfirmStatus()))
                .deviceId(msg.getDeviceId())
                .isRetain(msg.getRetain())
                .qos(MqttQoS.valueOf(msg.getQos()))
                .time(msg.getArrive().getTime())
                .build();
    }

    @Override
    public void addClientAckMessage(String deviceId, int messageId, SendMqttMessage msg) {

        AckMsg ackMsg = new AckMsg();
        ackMsg.setArrive(new Date(msg.getTime()));
        ackMsg.setConfirmStatus(msg.getConfirmStatus().ordinal());
        ackMsg.setContent(new String(msg.getByteBuf()));
        ackMsg.setMid(msg.getMessageId());
        ackMsg.setQos(msg.getQos().value());
        ackMsg.setTopic(msg.getTopic());
        ackMsg.setRetain(msg.isRetain());
        ackMsg.setDeviceId(msg.getDeviceId());
        ackMsg.insert();

    }

    @Override
    public void removeClientAckMessage(String deviceId, Integer messageId) {
        Wrapper<AckMsg> wrapper = new EntityWrapper<>();
        wrapper.eq("deviceId",deviceId).and().eq("mid",messageId);
        ackMsgService.delete(wrapper);

    }

    @Override
    public void updateClientAckMessage(String deviceId, Integer messageId, ConfirmStatus status) {
        Wrapper<AckMsg> wrapper = new EntityWrapper<>();
        wrapper.eq("deviceId",deviceId).and().eq("mid",messageId);
        AckMsg ackMsg = ackMsgService.selectOne(wrapper);
        if(null!=ackMsg){
            if(ackMsg.getConfirmStatus()==  ConfirmStatus.COMPLETE.ordinal() ){
                ackMsgService.delete(wrapper);
            }else{
                ackMsg.setConfirmStatus(status.ordinal());
                ackMsgService.updateById(ackMsg);
            }
        }else{
            log.error("确认消息不存在  "+messageId);
        }

    }

    @Override
    public void clearAckMsg(String deviceId) {
        Wrapper<AckMsg> wrapper = new EntityWrapper<>();
        wrapper.eq("deviceId",deviceId);
        ackMsgService.delete(wrapper);
    }

    @Override
    public Collection<SendMqttMessage> getClientAckMessages(String device) {
        Wrapper<AckMsg> wrapper = new EntityWrapper<>();
        wrapper.eq("deviceId",device);
        List<AckMsg> ackMsgList = ackMsgService.selectList(wrapper);
        List<SendMqttMessage> sendMqttMessageList = new ArrayList<>();
        for (AckMsg msg : ackMsgList){
           SendMqttMessage sendMqttMessage =  SendMqttMessage.builder()
                    .byteBuf(msg.getContent().getBytes())
                    .confirmStatus(ConfirmStatus.valueOf(msg.getConfirmStatus()))
                    .deviceId(msg.getDeviceId())
                    .isRetain(msg.getRetain())
                    .qos(MqttQoS.valueOf(msg.getQos()))
                    .time(msg.getArrive().getTime())
                    .messageId(msg.getMid())
                    .build();
           sendMqttMessageList.add(sendMqttMessage);
        }
        return sendMqttMessageList;
    }

    @Override
    public void addClientReceiveMessage(String deviceId, int messageId) {

        ReceiveMsg receiveMsg = new ReceiveMsg();
        receiveMsg.setDeviceId(deviceId);
        receiveMsg.setMid(messageId);
        receiveMsgService.insert(receiveMsg);

    }

    @Override
    public boolean checkClientReceiveMsg(String deviceId, int messageId) {
        Wrapper<ReceiveMsg> wrapper = new EntityWrapper<>();
        wrapper.eq("deviceId",deviceId).and().eq("mid",messageId);
        return receiveMsgService.selectOne(wrapper)!=null?true:false;
    }

    @Override
    public boolean removeClientReceiveMsg(String deviceId, int messageId) {
        Wrapper<ReceiveMsg> wrapper = new EntityWrapper<>();
        wrapper.eq("deviceId",deviceId).and().eq("mid",messageId);
        return receiveMsgService.delete(wrapper);
    }

    @Override
    public void saveClientWillMsg(String deviceId, WillMeaasge meaasge) {

        Wrapper<WillMsg> wrapper = new EntityWrapper<>();
        wrapper.eq("deviceId",deviceId).and().eq("willTopic",meaasge.getWillTopic());
        WillMsg willMsg = willMsgService.selectOne(wrapper);
        if(null!=willMsg){
            willMsg.setWillMessage(meaasge.getWillMessage());
            willMsg.setQos(meaasge.getQos());
            willMsg.setRetain(meaasge.isRetain());
            willMsg.updateById();
        }else{
            willMsg = new WillMsg();
            willMsg.setQos(meaasge.getQos());
            willMsg.setRetain(meaasge.isRetain());
            willMsg.setWillTopic(meaasge.getWillTopic());
            willMsg.setWillMessage(meaasge.getWillMessage());
            willMsg.setDeviceId(deviceId);
            willMsg.insert();
        }
    }

    @Override
    public WillMeaasge getClientWillMsg(String deviceId) {
        Wrapper<WillMsg> wrapper = new EntityWrapper<>();
        wrapper.eq("deviceId",deviceId);
        WillMsg willMsg =  willMsgService.selectOne(wrapper);
        if(null== willMsg){
            return null;
        }
        return WillMeaasge.builder()
                .willMessage(willMsg.getWillMessage())
                .willTopic(willMsg.getWillTopic())
                .isRetain(willMsg.isRetain())
                .qos(willMsg.getQos())
                .build();
    }

    @Override
    public void removeClientWillMsg(String deviceId) {
        Wrapper<WillMsg> wrapper = new EntityWrapper<>();
        wrapper.eq("deviceId",deviceId);
        willMsgService.delete(wrapper);
    }



}
