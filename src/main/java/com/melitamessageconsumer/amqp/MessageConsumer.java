package com.melitamessageconsumer.amqp;

import com.melitamessageconsumer.models.Client;
import com.melitamessageconsumer.models.Orders;
import com.melitamessageconsumer.service.MessageHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@EnableBinding(AMQPBindings.class)
@AllArgsConstructor
public class MessageConsumer {

    private MessageHandler mh;

    public static void main(String[] args) {
        SpringApplication.run(MessageConsumer.class, args);
    }

    @StreamListener(target = AMQPBindings.LISTEN)
    public void onMessage(MessagePayload msg){
        mh.handle(msg);
    }


}