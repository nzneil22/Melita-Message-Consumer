package com.crm.amqp;

import com.melita_task.api.models.Client;
import com.melita_task.api.models.FullName;
import com.melita_task.api.models.InstallationAddress;
import com.melita_task.contract.ClientDto;
import com.melita_task.contract.enums.EventTypes;
import com.melita_task.contract.events.CreateClientEventDto;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.core.MessageProducer;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(classes = MessageConsumer.class)
class MessageConsumerTest {
    @Autowired
    private MessageConsumer sut;

    @Autowired
    private AMQPBindings cs;

    @Autowired
    private MapperFacade mapper;

    @Test
    void onMessage_whenReceivingCreateClientEvent_shouldInvokeMessageHandlerUsingTheCreateClientRequest(){
        final Client client = new Client(new FullName("name", "middleName", "surname"),
                new InstallationAddress("island", "town", "street", "building"));

        final CreateClientEventDto createClientEventDto = new CreateClientEventDto(EventTypes.CLIENT_CREATED, mapper.map(client, ClientDto.class));

        //Send message



    }
}