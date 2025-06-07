package com.arya.api.infra.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Drone messaging
    public static final String DRONE_QUEUE = "drone.queue";
    public static final String DRONE_EXCHANGE = "drone.exchange";
    public static final String DRONE_ROUTING_KEY = "drone.routing-key";

    // Hub messaging
    public static final String HUB_QUEUE = "hub.queue";
    public static final String HUB_EXCHANGE = "hub.exchange";
    public static final String HUB_ROUTING_KEY = "hub.routing-key";

    // Ocorrencia messaging
    public static final String OCORRENCIA_QUEUE = "ocorrencia.queue";
    public static final String OCORRENCIA_EXCHANGE = "ocorrencia.exchange";
    public static final String OCORRENCIA_ROUTING_KEY = "ocorrencia.routing-key";

    // Drone beans
    @Bean
    public Queue droneQueue() {
        return new Queue(DRONE_QUEUE, true);
    }

    @Bean
    public DirectExchange droneExchange() {
        return new DirectExchange(DRONE_EXCHANGE);
    }

    @Bean
    public Binding droneBinding(@Qualifier("droneQueue") Queue droneQueue,
                                @Qualifier("droneExchange") DirectExchange droneExchange) {
        return BindingBuilder.bind(droneQueue).to(droneExchange).with(DRONE_ROUTING_KEY);
    }

    // Hub beans
    @Bean
    public Queue hubQueue() {
        return new Queue(HUB_QUEUE, true);
    }

    @Bean
    public DirectExchange hubExchange() {
        return new DirectExchange(HUB_EXCHANGE);
    }

    @Bean
    public Binding hubBinding(@Qualifier("hubQueue") Queue hubQueue,
                              @Qualifier("hubExchange") DirectExchange hubExchange) {
        return BindingBuilder.bind(hubQueue).to(hubExchange).with(HUB_ROUTING_KEY);
    }

    // Ocorrencia beans
    @Bean
    public Queue ocorrenciaQueue() {
        return new Queue(OCORRENCIA_QUEUE, true);
    }

    @Bean
    public DirectExchange ocorrenciaExchange() {
        return new DirectExchange(OCORRENCIA_EXCHANGE);
    }

    @Bean
    public Binding ocorrenciaBinding(@Qualifier("ocorrenciaQueue") Queue ocorrenciaQueue,
                                     @Qualifier("ocorrenciaExchange") DirectExchange ocorrenciaExchange) {
        return BindingBuilder.bind(ocorrenciaQueue).to(ocorrenciaExchange).with(OCORRENCIA_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
