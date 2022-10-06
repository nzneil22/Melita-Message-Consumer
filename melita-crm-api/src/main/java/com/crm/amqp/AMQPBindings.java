package com.crm.amqp;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface AMQPBindings {

    String LISTEN_CREATE = "crmQueueCreate";
    String LISTEN_UPDATE = "crmQueueUpdate";
    String LISTEN_SUBMIT = "crmQueueSubmit";

    @Input(LISTEN_CREATE)
    SubscribableChannel createClientListener();

    @Input(LISTEN_UPDATE)
    SubscribableChannel updateClientListener();

    @Input(LISTEN_SUBMIT)
    SubscribableChannel submitClientListener();

}
