package com.lxr.iot.auto.rabbitmq;

import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author jason
 * @package com.lxr.iot.rabbitmq
 * @Description
 */
@Component
public class QueueListener {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void process(MqttMessage msg) {
        System.out.println("topicMessageReceiver  : " +msg);
    }
}
