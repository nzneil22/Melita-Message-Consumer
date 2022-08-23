package com.melitamessageconsumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface AMQPBindings {

    String LISTEN = "amqpMicroService";

    @Input(LISTEN)
    SubscribableChannel amqpMicroServiceListen();

}
