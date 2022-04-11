package com.tienpm.rabbit.rabbitmq.worker;


public interface Worker {

    void run(String message) throws RuntimeException;
}
