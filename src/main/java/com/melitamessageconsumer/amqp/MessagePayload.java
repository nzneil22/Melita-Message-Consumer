package com.melitamessageconsumer.amqp;

import com.melitamessageconsumer.models.Client;
import com.melitamessageconsumer.models.Orders;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessagePayload {
    private String alteration;
    private Client client;
    private Orders order;
}
