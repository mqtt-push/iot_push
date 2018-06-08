package com.lxr.iot.bootstrap;/**
 * Created by wangcy on 2017/11/23.
 */

import com.lxr.iot.bootstrap.bean.MqttChannel;
import com.lxr.iot.server.bean.WillMeaasge;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;

import java.util.List;
import java.util.Map;

/**
 * 消息处理
 *
 * @author lxr
 * @create 2017-11-23 11:38
 **/
public interface ChannelService {

    MqttChannel getMqttChannel(String deviceId);

    boolean connectSuccess(String s, MqttChannel build);


    void suscribeSuccess(String deviceId,Map<String,Integer> topics);


    void loginSuccess(Channel channel, String deviceId, MqttConnectMessage mqttConnectMessage);

    void publishSuccess(Channel channel, MqttPublishMessage mqttPublishMessage);

    void closeSuccess(String deviceId,boolean isDisconnect);

    void sendWillMsg(WillMeaasge willMeaasge, String deviceId);

    String  getDeviceId(Channel channel);

    void unsubscribe(String deviceId, List<String> topics1);

    void  doPubrel(Channel channel, int mqttMessage);

    void  doPubrec(Channel channel, int mqttMessage);

}
