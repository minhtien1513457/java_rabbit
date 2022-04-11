package com.tienpm.rabbit.rabbitmq.listener;

import com.tienpm.rabbit.rabbitmq.worker.WorkerManager;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DemoListener {

    @RabbitListener(queues = "#{rabbitConfig.getInstanceRef().getQueue()}")
    private void process(Message message) {
        try {
            String routingKey = message.getMessageProperties().getReceivedRoutingKey();
            WorkerManager.getWorker(routingKey).run(new String(message.getBody()));
        } catch (Exception e) {
        }
    }
}
