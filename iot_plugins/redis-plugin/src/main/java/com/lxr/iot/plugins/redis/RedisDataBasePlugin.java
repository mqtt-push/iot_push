package com.lxr.iot.plugins.redis;

import com.lxr.iot.enums.ConfirmStatus;
import com.lxr.iot.server.bean.RetainMessage;
import com.lxr.iot.server.bean.SendMqttMessage;
import com.lxr.iot.server.bean.SessionMessage;
import com.lxr.iot.server.bean.WillMeaasge;
import com.lxr.iot.server.plugins.MessageDataBasePlugin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

/**
 * @author jason
 * @package com.lxr.iot.db.plugins
 * @Description 用做存储的redis插件
 */
@Slf4j
public class RedisDataBasePlugin implements MessageDataBasePlugin {
    private final String KEY_FREFIX = "mqtt:";

    protected RedisTemplate redisTemplate;

    public RedisDataBasePlugin(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onlineDevice(String clientId, String serverNode) {
        Map params = new HashMap();
        params.put("stats", 1);
        params.put("online_time", new Date());
        params.put("serverNode", serverNode);
        redisTemplate.boundHashOps(KEY_FREFIX + "clients:" + clientId).putAll(params);
        //保存每一个节点下的客户端信息
        redisTemplate.opsForHash().put(KEY_FREFIX + "node:" + serverNode, clientId, System.currentTimeMillis());
    }

    @Override
    public void offlineDevice(String clientId, String serverNode) {
        redisTemplate.boundHashOps(KEY_FREFIX + "clients:" + clientId).put("stats", 0);
        redisTemplate.boundHashOps(KEY_FREFIX + "clients:" ).delete(clientId);

    }

    @Override
    public Boolean saveRetainMessage(String topic, RetainMessage retainMessage,boolean isClean) {
        String key= KEY_FREFIX+"retain:"+topic;
        BoundSetOperations setOperations = redisTemplate.boundSetOps(key);
        if(isClean){
          redisTemplate.delete(key);
        }
        setOperations.add(retainMessage);
        return true;
    }

    @Override
    public Set<RetainMessage> getRetainMessage(String topic) {
        String key= KEY_FREFIX+"retain:"+topic;
        BoundSetOperations setOperations = redisTemplate.boundSetOps(key);
        return setOperations.members();
    }


    @Override
    public Boolean saveMessage(SendMqttMessage mqttMessageEntity) {
        return null;
    }

    @Override
    public Boolean saveAckedMessage(String clientId, String topic, String packageId) {
        return null;
    }

    @Override
    public Boolean saveSub(String clientId,String[] topics, Integer qos) {
        Map<String,Object> topicMap = new HashMap<>();
        for (String topic: topics){
            topicMap.put(topic,qos);
        }
        redisTemplate.boundHashOps(KEY_FREFIX + "sub:" + clientId).putAll(topicMap);

        //保存订阅主题和设备之间的关系
        for (String topic: topics){
            redisTemplate.boundSetOps(KEY_FREFIX + "sub-client:" + topic).add(clientId);
        }
        return true;
    }

    @Override
    public Boolean delSub(String clientId,String[] topics) {
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(KEY_FREFIX + "sub:" + clientId);
        boundHashOperations.delete(topics);

        //删除订阅主题和设备之间的关系
        for (String topic: topics){
            redisTemplate.boundSetOps(KEY_FREFIX + "sub-client:" + topic).remove(clientId);
        }
        return true;
    }

    @Override
    public void clearSub(String clientId) {
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(KEY_FREFIX + "sub:" + clientId);
        Set<String> keys = boundHashOperations.keys();
        Optional.ofNullable(keys).ifPresent(key->key.forEach(item->boundHashOperations.delete(item)));
    }

    @Override
    public Collection<String> getSubClients(String[] topics) {
        Set<String> clients = new HashSet<>();
        for (String topic: topics){
           Set topicsClient = redisTemplate.boundSetOps(KEY_FREFIX + "sub-client:" + topic).members();
           clients.addAll(topicsClient);
        }
        return clients;
    }

    @Override
    public void saveSessionMsg(String deviceId, SessionMessage sessionMessage) {
        redisTemplate.boundSetOps(KEY_FREFIX + "session:message:" + deviceId).add(sessionMessage);
    }

    @Override
    public Set<SessionMessage> getSessionMsg(String deviceId) {
        return redisTemplate.boundSetOps(KEY_FREFIX + "session:message:" + deviceId).members();
    }


    @Override
    public SendMqttMessage getClientAckMessage(String deviceId,int messageId) {
       return (SendMqttMessage) redisTemplate.boundHashOps(KEY_FREFIX+"ack:message:"+deviceId).get(String.valueOf(messageId));
    }

    @Override
    public void addClientAckMessage(String deviceId, int messageId, SendMqttMessage msg) {
        redisTemplate.boundHashOps(KEY_FREFIX+"ack:message:"+deviceId).put(String.valueOf(messageId),msg);
    }

    @Override
    public void removeClientAckMessage(String deviceId,Integer messageId) {
        redisTemplate.boundHashOps(KEY_FREFIX+"ack:message:"+deviceId).delete(String.valueOf(messageId));
    }

    @Override
    public void updateClientAckMessage(String deviceId, Integer messageId, ConfirmStatus status) {
      BoundHashOperations hashOperations =   redisTemplate.boundHashOps(KEY_FREFIX+"ack:message:"+deviceId);
        SendMqttMessage sendMqttMessage = (SendMqttMessage) hashOperations.get(String.valueOf(messageId));
        if(sendMqttMessage!=null ){
            if(ConfirmStatus.COMPLETE == status){
                hashOperations.delete(String.valueOf(messageId));
                removeClientReceiveMsg(deviceId,messageId);
            }else{
                sendMqttMessage.setConfirmStatus(status);
                hashOperations.put(String.valueOf(messageId),sendMqttMessage);
            }
        }else{
            log.error("确认消息不存在  "+messageId);
        }
    }

    @Override
    public Collection<SendMqttMessage> getClientAckMessages(String deviceId) {
        BoundHashOperations hashOperations =   redisTemplate.boundHashOps(KEY_FREFIX+"ack:message:"+deviceId);
        return hashOperations.values();
    }

    @Override
    public void addClientReceiveMessage(String deviceId,int messageId) {
        redisTemplate.boundSetOps(KEY_FREFIX+"client_msg:"+deviceId).add(String.valueOf(messageId));
    }

    @Override
    public boolean checkClientReceiveMsg(String deviceId, int messageId) {
        return redisTemplate.boundSetOps(KEY_FREFIX+"client_msg:"+deviceId).isMember(String.valueOf(messageId));
    }

    @Override
    public boolean removeClientReceiveMsg(String deviceId, int messageId) {
        return redisTemplate.boundSetOps(KEY_FREFIX+"client_msg:"+deviceId).remove(String.valueOf(messageId))>0?true:false;
    }

    @Override
    public void saveClientWillMsg(String deviceid, WillMeaasge meaasge) {
        redisTemplate.boundHashOps(KEY_FREFIX+"will").put(deviceid,meaasge);
    }

    @Override
    public WillMeaasge getClientWillMsg(String deviceId) {
        return (WillMeaasge) redisTemplate.boundHashOps(KEY_FREFIX+"will").get(deviceId);
    }

    @Override
    public void removeClientWillMsg(String deviceId) {
        redisTemplate.boundHashOps(KEY_FREFIX+"will").delete(deviceId);
    }


}
