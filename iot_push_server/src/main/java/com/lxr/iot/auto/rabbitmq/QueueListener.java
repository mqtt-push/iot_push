package com.lxr.iot.auto.rabbitmq;

import com.lxr.iot.server.bean.SendMqttMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author jason
 * @package com.lxr.iot.rabbitmq
 * @Description
 */
@Component
public class QueueListener {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME+"${lxr.iot.server.serverName}")
    public void process(SendMqttMessage msg) {
        System.out.println("topicMessageReceiver  : " +msg);
    }
}
