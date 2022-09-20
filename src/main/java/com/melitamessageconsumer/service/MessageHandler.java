package com.melitamessageconsumer.service;

import com.melitamessageconsumer.amqp.MessagePayload;
import com.melitamessageconsumer.dao.ClientRepository;
import com.melitamessageconsumer.dao.OrderRepository;
import com.melitamessageconsumer.models.Client;
import com.melitamessageconsumer.models.Orders;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@AllArgsConstructor
public class MessageHandler {

    private ClientRepository cr;
    private OrderRepository or;

    public void handle(MessagePayload msg){
        logMessage(msg.getAlteration(), msg.getClient(), msg.getOrder());
        applyPersistence(msg.getClient(), msg.getOrder());
    }

    public void applyPersistence(Client c, Orders o){
        if(nonNull(c)) cr.save(c);
        else or.save(o);
    }

    public void logMessage(String alt, Client c, Orders o){
        if(nonNull(c)) log.info(alt + " - " + c.toString());
        else log.info(alt + " - " + o.toString());
    }
}
