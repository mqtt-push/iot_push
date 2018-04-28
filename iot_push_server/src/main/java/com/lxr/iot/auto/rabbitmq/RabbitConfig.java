package com.lxr.iot.auto.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

/**
 * @author jason
 * @package com.lxr.iot.auto
 * @Description
 */
@Configuration
@EnableRabbit
public class RabbitConfig  {

    public static final String QUEUE_NAME="topic.server.messages";
    public static final String QUEUE_EXCHANGE="topic.server.exchange";


    @Bean
    public Queue queueMessages() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(QUEUE_EXCHANGE);
    }

    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }

}
