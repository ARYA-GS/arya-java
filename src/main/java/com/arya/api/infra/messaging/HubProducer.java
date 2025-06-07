package com.arya.api.infra.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubProducer {

    private final AmqpTemplate amqpTemplate;

    public void send(Object message) {
        amqpTemplate.convertAndSend(
                RabbitMQConfig.HUB_EXCHANGE,
                RabbitMQConfig.HUB_ROUTING_KEY,
                message
        );
    }
}

