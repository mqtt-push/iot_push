package com.lxr.iot.auto.rabbitmq;

import com.lxr.iot.bootstrap.SessionManager;
import com.lxr.iot.bootstrap.bean.MqttChannel;
import com.lxr.iot.properties.InitBean;
import com.lxr.iot.server.bean.SendMqttMessage;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * @author jason
 * @package com.lxr.iot.rabbitmq
 * @Description
 */
@Component
public class QueueListener {

    @Autowired
    private InitBean properties;

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME+"${lxr.iot.server.serverName}")
    public void process(SendMqttMessage msg) {
        if(msg.getNode()!=properties.getServerName()){
            //进行数据转发
            Collection<MqttChannel> channels =  SessionManager.getInstance().getChannelByTopic(msg.getTopic());
            Optional.ofNullable(channels).ifPresent(channelc->channelc.stream().forEach(item->{
                pubMessage(item.getChannel(),msg);
            }));
            System.out.println("topicMessageReceiver  : " +msg);
        }
    }


    private   void pubMessage(Channel channel, SendMqttMessage mqttMessage){
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.PUBLISH,true, mqttMessage.getQos(),mqttMessage.isRetain(),0);
        MqttPublishVariableHeader mqttPublishVariableHeader = new MqttPublishVariableHeader(mqttMessage.getTopic(),mqttMessage.getMessageId());
        MqttPublishMessage mqttPublishMessage = new MqttPublishMessage(mqttFixedHeader,mqttPublishVariableHeader, Unpooled.wrappedBuffer(mqttMessage.getByteBuf()));
        channel.writeAndFlush(mqttPublishMessage);
    }
}
