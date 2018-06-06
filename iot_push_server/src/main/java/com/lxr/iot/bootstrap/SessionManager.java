package com.lxr.iot.bootstrap;

import com.lxr.iot.bootstrap.bean.MqttChannel;

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





}
