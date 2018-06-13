package com.lxr.iot.bootstrap;

import com.lxr.iot.bootstrap.bean.MqttChannel;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jason
 * @package com.lxr.iot.bootstrap
 * @Description
 */
public class SessionManager {

    private static SessionManager instance = new SessionManager();
    private ConcurrentHashMap<String ,MqttChannel> channelMap = new ConcurrentHashMap();

    public SessionManager() {
    }

    public static SessionManager getInstance() {
        return instance;
    }

    /**
     * 保存当前连接的数据
     * @param deviceId
     * @param channel
     */
    public void addChannel(String deviceId,MqttChannel channel){
        channelMap.put(deviceId,channel);
    }

    /**
     * 根据设备号移除连接信息
     * @param deviceId
     */
    public void removeChannel(String deviceId){
        channelMap.remove(deviceId);
    }

    /**
     * 根据设备号获取当前连接
     * @param deviceId
     * @return
     */
    public MqttChannel getChannel(String deviceId){
        return channelMap.get(deviceId);
    }

    /**
     * 获取所有的在线设备列表
     * @return
     */
    public Collection<String> getDevices(){
        return channelMap.keySet();
    }

    /**
     * 根据主题获取连接
     * @param topic
     * @return
     */
    public Collection<MqttChannel> getChannelByTopic(String topic){
        List<MqttChannel> results = new ArrayList<>();
        Collection<MqttChannel> channels =  channelMap.values();
        Collection<String> topics = Arrays.asList(getTopic(topic));
        if(null != results){
            channels.stream().forEach(channel->{
                if(channel.getTopic().containsAll(topics)){
                    results.add(channel);
                }
            });
            return results;
        }
        return null;
    }



    protected String[] getTopic(String topic)  {
        return Optional.ofNullable(topic).map(s ->
                StringUtils.split(topic,"/")
        ).orElse(null);
    }


}
