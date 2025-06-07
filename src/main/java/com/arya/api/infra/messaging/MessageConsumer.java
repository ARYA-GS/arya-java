package com.arya.api.infra.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receive(String message) {
        log.info("Received message: {}", message);
    }
}
