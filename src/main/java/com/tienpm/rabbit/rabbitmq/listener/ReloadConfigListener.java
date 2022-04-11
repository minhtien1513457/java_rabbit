package com.tienpm.rabbit.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReloadConfigListener {

    @RabbitListener(queues = "#{rabbitConfig.getInstanceRef().getExclusiveQueue()}")
    private void handler(Message message) {
        try {
        } catch (Exception e) {
        }
    }
}
