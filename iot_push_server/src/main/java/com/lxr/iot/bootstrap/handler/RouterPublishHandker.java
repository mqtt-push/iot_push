package com.lxr.iot.bootstrap.handler;

import com.lxr.iot.util.ByteBufUtil;
import com.lxr.iot.util.SpringBeanUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jason
 * @package cn.magicbeans.lot.server.bootstrap.handler
 * @Description
 */
@Slf4j
public class RouterPublishHandker extends ChannelInboundHandlerAdapter {

    private RabbitTemplate rabbitTemplate;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            MqttPublishMessage publishMessage = (MqttPublishMessage)msg;
            String topic = publishMessage.variableHeader().topicName();
            String msgContent = new String(ByteBufUtil.copyByteBuf(publishMessage.payload().copy()));
            Map<String,String> sendData = new HashMap<>();
            sendData.put("topic",topic);
            sendData.put("msg",msgContent);
            SpringBeanUtils.getBean(RabbitTemplate.class).convertAndSend("mqtt_rec_queue",sendData);
            log.info("发布消息的消息体 "+msgContent);
        } catch (AmqpException e) {
            e.printStackTrace();
        }
        super.channelRead(ctx, msg);
    }
}
