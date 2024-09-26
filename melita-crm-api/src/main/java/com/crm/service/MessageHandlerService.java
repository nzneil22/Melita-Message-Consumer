package com.crm.service;


import com.melita_task.contract.ClientDto;
import com.melita_task.contract.enums.EventTypes;
import com.melita_task.contract.events.CreateClientEventDto;
import com.melita_task.contract.events.SubmitOrdersEventDto;
import com.melita_task.contract.events.UpdateClientEventDto;
import com.crm.dao.ClientRepository;
import com.crm.models.Client;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MessageHandlerService {

    private final MapperFacade mapper;

    private ClientRepository cr;

    public void handle(CreateClientEventDto msg){
        logMessage(msg.getType(), msg.getClient());
        applyPersistence(msg.getClient());
    }

    public void handle(UpdateClientEventDto msg){
        logMessage(msg.getType(), msg.getClient());
        applyPersistence(msg.getClient());
    }

    public void handle(SubmitOrdersEventDto msg){
        logMessage(msg.getType(), msg.getClient());
        applyPersistence(msg.getClient());
    }

    public void applyPersistence(ClientDto clientDto){
        cr.save(mapper.map(clientDto, Client.class));
    }

    public void logMessage(EventTypes type, ClientDto clientDto){
        log.info("{} - [{}]", type, clientDto);
    }
}
