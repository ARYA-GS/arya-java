package com.arya.api.infra.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProducer {

    private final AmqpTemplate amqpTemplate;

    public void send(String message) {
        amqpTemplate.convertAndSend(
                RabbitMQConfig.DRONE_EXCHANGE,
                RabbitMQConfig.DRONE_ROUTING_KEY,
                message
        );
    }
}
