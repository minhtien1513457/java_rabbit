package com.tienpm.rabbit.config;

import lombok.Getter;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@Getter
public class RabbitConfig {
    private static RabbitConfig instanceRef;

    private final String exchange;
    private final String queue;
    private final String exclusiveQueue;

    private final String routingKeyReloadConfigDB;
    private final String routingKeyDemoApiSayHello;

    public RabbitConfig(@Value("${rabbitmq.demo-api.exchange}") String exchange,
                        @Value("${rabbitmq.demo-api.queue}") String queue,

                        @Value("${rabbitmq.demo-api.routing-key.demo_api_say_hello}") String routingKeyDemoApiSayHello,
                        @Value("${rabbitmq.demo-api.routing-key.reload-config-db}") String routingKeyReloadConfigDB) {
        this.exchange = exchange;
        this.queue = queue;
        this.exclusiveQueue = String.format("%s.node.%s", queue, UUID.randomUUID());

        //routing key
        this.routingKeyReloadConfigDB = routingKeyReloadConfigDB;
        this.routingKeyDemoApiSayHello = routingKeyDemoApiSayHello;

        initConstructor(this);
    }

    private static void initConstructor(RabbitConfig t) {
        instanceRef = t;
    }

    public static RabbitConfig getInstanceRef() {
        return instanceRef;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory rabbitConnFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnFactory);
        return rabbitTemplate;
    }

    @Bean
    public Declarables bindings() {
        DirectExchange ex = new DirectExchange(this.exchange);
        Queue q = new Queue(this.queue);
        Queue eq = new Queue(this.exclusiveQueue, true, true, true);

        return new Declarables(
                ex,
                q,
                eq,
                BindingBuilder.bind(eq).to(ex).with(this.routingKeyReloadConfigDB),
                BindingBuilder.bind(q).to(ex).with(this.routingKeyDemoApiSayHello)
        );
    }

}
