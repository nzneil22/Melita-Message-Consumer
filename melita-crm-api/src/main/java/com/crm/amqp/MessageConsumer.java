package com.crm.amqp;

import com.melita_task.contract.events.CreateClientEventDto;
import com.melita_task.contract.events.SubmitOrdersEventDto;
import com.melita_task.contract.events.UpdateClientEventDto;
import com.crm.service.MessageHandlerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@EnableBinding(AMQPBindings.class)
@AllArgsConstructor
public class MessageConsumer {

    private MessageHandlerService handler;

    public static void main(String[] args) {
        SpringApplication.run(MessageConsumer.class, args);
    }

    @StreamListener(target = AMQPBindings.LISTEN_CREATE)
    public void onCreateMessage(CreateClientEventDto eventDto){
        handler.handle(eventDto);
    }

    @StreamListener(target = AMQPBindings.LISTEN_UPDATE)
    public void onUpdateMessage(UpdateClientEventDto eventDto){
        handler.handle(eventDto);
    }

    @StreamListener(target = AMQPBindings.LISTEN_SUBMIT)
    public void onSubmitMessage(SubmitOrdersEventDto eventDto){
        handler.handle(eventDto);
    }

}