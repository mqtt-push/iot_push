package com.lxr.iot.bootstrap.channel;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.lxr.iot.bootstrap.SessionManager;
import com.lxr.iot.bootstrap.bean.MqttChannel;
import com.lxr.iot.bootstrap.bean.RetainMessage;
import com.lxr.iot.bootstrap.BaseApi;
import com.lxr.iot.bootstrap.ChannelService;
import com.lxr.iot.bootstrap.channel.cache.CacheMap;
import com.lxr.iot.bootstrap.db.MessageDataBasePlugin;
import com.lxr.iot.bootstrap.db.plugins.RedisDataBasePlugin;
import com.lxr.iot.bootstrap.scan.ScanRunnable;
import com.lxr.iot.util.SpringBeanUtils;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.Op;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 抽象类
 *
 * @author lxr
 * @create 2017-12-12 20:01
 **/
@Slf4j
public abstract class AbstractChannelService extends PublishApiSevice implements ChannelService ,BaseApi {


    protected AttributeKey<Boolean> _login = AttributeKey.valueOf("login");

    protected   AttributeKey<String> _deviceId = AttributeKey.valueOf("deviceId");

    protected  static char SPLITOR = '/';

    protected ExecutorService executorService =Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);


    protected static CacheMap<String,MqttChannel> cacheMap= new CacheMap<>();


//    protected static ConcurrentHashMap<String ,MqttChannel> mqttChannels = new ConcurrentHashMap<>(); // deviceId - mqChannel 登录

    protected  static  ConcurrentHashMap<String,ConcurrentLinkedQueue<RetainMessage>> retain = new ConcurrentHashMap<>(); // topic - 保留消息



    protected  static  Cache<String, Collection<MqttChannel>> mqttChannelCache = CacheBuilder.newBuilder().maximumSize(100).build();

    public AbstractChannelService(ScanRunnable scanRunnable) {
        super(scanRunnable);
    }


    protected  Collection<MqttChannel> getChannels(String topic,TopicFilter topicFilter){
        MessageDataBasePlugin dataBasePlugin =  SpringBeanUtils.getBean(MessageDataBasePlugin.class);
        Collection<String> clients =   dataBasePlugin.getSubClients(getTopic(topic));
        Collection<MqttChannel> mqttChannels = new HashSet<>();
        for (String client:clients){
            MqttChannel channel = SessionManager.getInstance().getChannel(client);
            if(null!=channel){
                mqttChannels.add(channel);
            }
        }
        if(mqttChannels.size() == 0){
            return null;
        }else{
            return mqttChannels;
        }
    }


    @FunctionalInterface
    interface TopicFilter{
        Collection<MqttChannel> filter(String topic);
    }

    protected boolean deleteChannel(String topic,MqttChannel mqttChannel){
      return  Optional.ofNullable(topic).map(s -> {
            mqttChannelCache.invalidate(s);
            return  cacheMap.delete(getTopic(s),mqttChannel);
        }).orElse(false);
    }

    protected boolean addChannel(String topic,MqttChannel mqttChannel)
    {
        return  Optional.ofNullable(topic).map(s -> {
            mqttChannelCache.invalidate(s);
            return cacheMap.putData(getTopic(s),mqttChannel);
        }).orElse(false);
    }

    /**
     * 获取channel
     */
    @Override
    public MqttChannel getMqttChannel(String deviceId){
        return Optional.ofNullable(deviceId).map(s -> SessionManager.getInstance().getChannel(s))
                .orElse(null);

    }

    /**
     * 获取channelId
     */
    @Override
    public String  getDeviceId(Channel channel){
        return  Optional.ofNullable(channel).map( channel1->channel1.attr(_deviceId).get())
                .orElse(null);
    }



    protected String[] getTopic(String topic)  {
        return Optional.ofNullable(topic).map(s ->
             StringUtils.split(topic,SPLITOR)
        ).orElse(null);
    }



}
