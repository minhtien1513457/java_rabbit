package com.tienpm.rabbit.rabbitmq.worker;

import com.tienpm.rabbit.config.RabbitConfig;
import org.springframework.stereotype.Component;

@Component
public class WorkerManager {

    private static WorkerManager instanceRef;

    private final SayHelloWorker sayHelloWorker;

    public WorkerManager(
            SayHelloWorker sayHelloWorker
    ) {
        this.sayHelloWorker = sayHelloWorker;
        initInstanceRef(this);
    }

    private static void initInstanceRef(WorkerManager t) {
        instanceRef = t;
    }

    public static WorkerManager getInstanceRef() {
        return instanceRef;
    }

    public static Worker getWorker(String name) throws RuntimeException {

        if (RabbitConfig.getInstanceRef().equals(name)) {
            return WorkerManager.getInstanceRef().sayHelloWorker;
        }

        throw new RuntimeException(String.format("No supported worker %s.", name));
    }
}
