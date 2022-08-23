package com.melitamessageconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
@SpringBootApplication
@EnableBinding(AMQPBindings.class)
public class MessageConsumer{

    public static void main(String[] args) {
        SpringApplication.run(MessageConsumer.class, args);
    }

    @StreamListener(target = AMQPBindings.LISTEN)
    public void onMessage(String msg){
        System.out.println("Melita Consumes Message - " + msg);
    }


}