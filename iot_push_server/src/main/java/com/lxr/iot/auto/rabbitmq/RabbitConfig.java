package com.lxr.iot.auto.rabbitmq;

import com.lxr.iot.properties.InitBean;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jason
 * @package com.lxr.iot.auto
 * @Description
 */
@Configuration
public class RabbitConfig  {

    @Autowired
    private InitBean propertiers;

    public static final String QUEUE_NAME="server_messages.";

    public static final String  EXCHANGE ="server-message-exchange";

    @Bean("serverMsgQueue")
    public Queue initServerQueue() {
        return new Queue(QUEUE_NAME+propertiers.getServerName());
    }

    //配置广播路由器
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE);
    }

    @Bean
    Binding bindingExchangeA(@Qualifier("serverMsgQueue") Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }


}
