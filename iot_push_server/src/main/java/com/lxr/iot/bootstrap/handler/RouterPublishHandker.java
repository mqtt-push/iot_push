package com.lxr.iot.bootstrap.handler;

import com.lxr.iot.auto.rabbitmq.RabbitConfig;
import com.lxr.iot.enums.ConfirmStatus;
import com.lxr.iot.server.bean.SendMqttMessage;
import com.lxr.iot.util.ByteBufUtil;
import com.lxr.iot.util.SpringBeanUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Date;
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

    private String nodeName;

    public RouterPublishHandker(String nodeName) {
        this.nodeName = nodeName;
    }



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if(msg instanceof MqttPublishMessage){
                MqttPublishMessage publishMessage = (MqttPublishMessage)msg;
                String msgContent = new String(ByteBufUtil.copyByteBuf(publishMessage.payload().copy()));
                SendMqttMessage sendMqttMessage = SendMqttMessage.builder()
                        .messageId(publishMessage.variableHeader().packetId())
                        .time(System.currentTimeMillis())
                        .qos(publishMessage.fixedHeader().qosLevel())
                        .isRetain(publishMessage.fixedHeader().isRetain())
                        .confirmStatus(ConfirmStatus.PUB)
                        .topic(publishMessage.variableHeader().topicName())
                        .byteBuf(ByteBufUtil.copyByteBuf(publishMessage.payload().copy()))
                        .node(nodeName)
                        .build();
                Map<String,Object> sendData = new HashMap<>();
                sendData.put("topic",publishMessage.variableHeader().topicName());
                sendData.put("msg",msgContent);
                if(null == rabbitTemplate){
                    rabbitTemplate = SpringBeanUtils.getBean(RabbitTemplate.class);
                }
                rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE,"",sendMqttMessage);
                log.info("发布消息的消息体 "+msgContent);
            }
        } catch (AmqpException e) {
            e.printStackTrace();
        }
        super.channelRead(ctx, msg);
    }
}
