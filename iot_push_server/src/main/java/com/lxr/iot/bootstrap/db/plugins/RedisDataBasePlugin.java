package com.lxr.iot.bootstrap.db.plugins;

import com.lxr.iot.bootstrap.db.entity.MqttMessageEntity;
import com.lxr.iot.bootstrap.db.MessageDataBasePlugin;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jason
 * @package com.lxr.iot.db.plugins
 * @Description 用做存储的redis插件
 */
public class RedisDataBasePlugin implements MessageDataBasePlugin {
    private final  String KEY_FREFIX="mqtt:";

    protected  RedisTemplate<String,Object> redisTemplate;

    public RedisDataBasePlugin(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onlineDevice(String clientId,  String serverNode) {
        Map params = new HashMap();
        params.put("stats",1);
        params.put("online_time",new Date());
        params.put("serverNode",serverNode);
        redisTemplate.boundHashOps(KEY_FREFIX+"clients:"+clientId).putAll(params);
        //保存每一个节点下的客户端信息
        redisTemplate.opsForHash().put(KEY_FREFIX+"node:"+serverNode,clientId,System.currentTimeMillis());
    }

    @Override
    public void  offlineDevice(String clientId,String serverNode) {
        redisTemplate.boundHashOps(KEY_FREFIX+"clients:"+clientId).put("stats",0);
        redisTemplate.opsForHash().delete(KEY_FREFIX+"node:"+serverNode,clientId);
    }

    @Override
    public Boolean saveRetainMessage(MqttMessageEntity mqttMessageEntity) {
        return null;
    }

    @Override
    public Boolean saveMessage(MqttMessageEntity mqttMessageEntity) {
        return null;
    }

    @Override
    public Boolean saveAckedMessage(String clientId, String topic, String packageId) {
        return null;
    }

    @Override
    public Boolean saveSub(String clientId, String topic, Integer qos) {
        return null;
    }
}
