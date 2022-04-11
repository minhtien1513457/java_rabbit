package com.tienpm.rabbit.rabbitmq.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SayHelloWorker implements Worker {

    @Override
    public void run(String message) throws RuntimeException {
        try {
           System.out.println(String.format("Handle logic %s here: ", message));
        } catch (Exception e) {
        }
    }
}
