package com.lxr.iot.plugin.mysql;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.lxr.iot.enums.ConfirmStatus;
import com.lxr.iot.plugin.mysql.common.DTO2VOUtil;
import com.lxr.iot.plugin.mysql.entity.*;
import com.lxr.iot.plugin.mysql.enums.ClientState;
import com.lxr.iot.plugin.mysql.service.IClientService;
import com.lxr.iot.plugin.mysql.service.IRetainService;
import com.lxr.iot.plugin.mysql.service.ISubService;
import com.lxr.iot.server.bean.RetainMessage;
import com.lxr.iot.server.bean.SendMqttMessage;
import com.lxr.iot.server.bean.SessionMessage;
import com.lxr.iot.server.bean.WillMeaasge;
import com.lxr.iot.server.plugins.MessageDataBasePlugin;
import io.netty.handler.codec.mqtt.MqttQoS;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author jason
 * @package com.lxr.iot.plugin.mysql
 * @Description
 */
public class MysqlDataBasePlugin implements MessageDataBasePlugin {

    @Resource
    private IClientService clientService;

    @Resource
    private ISubService subService;

    @Resource
    private IRetainService retainService;

    @Override
    public void onlineDevice(String clientId, String serverNode) {

       EntityWrapper entityWrapper = new EntityWrapper();
       entityWrapper.eq("clientid",clientId);
       Client client =  clientService.selectOne(entityWrapper);
       if(null!= client){
           client.setOnlineAt(new Date());
           client.insert();
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
            client.insert();
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
    public boolean checkClientReceiveMsg(String deviceId, int messageId) {



        return false;
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
        return subService.insertBatch(subs);
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
        return null;
    }

    @Override
    public void saveSessionMsg(String deviceId, SessionMessage sessionMessage) {

    }

    @Override
    public Set<SessionMessage> getSessionMsg(String deviceId) {
        return null;
    }

    @Override
    public SendMqttMessage getClientAckMessage(String deviceId, int messageId) {
        return null;
    }

    @Override
    public void addClientAckMessage(String deviceId, int messageId, SendMqttMessage msg) {

    }

    @Override
    public void removeClientAckMessage(String deviceId, Integer messageId) {

    }

    @Override
    public void updateClientAckMessage(String deviceId, Integer messageId, ConfirmStatus status) {

    }

    @Override
    public void addClientReceiveMessage(String deviceId, int messageId) {

    }

    @Override
    public boolean removeClientReceiveMsg(String deviceId, int messageId) {
        return false;
    }

    @Override
    public void saveClientWillMsg(String deviceId, WillMeaasge meaasge) {

    }

    @Override
    public WillMeaasge getClientWillMsg(String deviceId) {
        return null;
    }

    @Override
    public void removeClientWillMsg(String deviceId) {

    }

    @Override
    public Collection<SendMqttMessage> getClientAckMessages(String device) {
        return null;
    }
}
