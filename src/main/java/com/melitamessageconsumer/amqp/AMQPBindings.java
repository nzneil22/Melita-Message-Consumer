package com.melitamessageconsumer.amqp;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface AMQPBindings {

    String LISTEN = "crmQueue";

    @Input(LISTEN)
    SubscribableChannel amqpMicroServiceListen();

}
