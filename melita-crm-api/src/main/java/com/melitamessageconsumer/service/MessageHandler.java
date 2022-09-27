package com.melitamessageconsumer.service;

import com.melita_task.contract.ClientDtoRabbit;
import com.melitamessageconsumer.amqp.MessagePayload;
import com.melitamessageconsumer.dao.ClientRepository;
import com.melitamessageconsumer.models.Client;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MessageHandler {

    private final MapperFacade mapper;

    private ClientRepository cr;

    public void handle(MessagePayload msg){
        logMessage(msg.getAlteration(), msg.getClient());
        applyPersistence(msg.getClient());
    }

    public void applyPersistence(ClientDtoRabbit clientDtoRabbit){
        cr.save(mapper.map(clientDtoRabbit, Client.class));
    }

    public void logMessage(String alt, ClientDtoRabbit clientDtoRabbit){
        log.info(alt + " - " + clientDtoRabbit);
    }
}
