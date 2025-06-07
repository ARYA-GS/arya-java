package com.arya.api.infra.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HubConsumer {

    @RabbitListener(queues = RabbitMQConfig.HUB_QUEUE)
    public void receive(Object message) {
        log.info("Hub queue received: {}", message);
    }
}

