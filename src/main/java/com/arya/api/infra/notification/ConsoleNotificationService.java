package com.arya.api.infra.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConsoleNotificationService implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleNotificationService.class);

    @Override
    public void enviarAlerta(String mensagem) {
        LOGGER.info("ALERTA: {}", mensagem);
    }
}
