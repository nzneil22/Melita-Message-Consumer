package com.melitamessageconsumer.mapper;

import com.melita_task.contract.ClientDtoRabbit;
import com.melita_task.contract.NewClientRequestDto;
import com.melita_task.contract.OrderDto;
import com.melitamessageconsumer.dao.ClientRepository;
import com.melitamessageconsumer.models.Client;
import com.melitamessageconsumer.models.Order;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class OrikaCustomMapper extends ConfigurableMapper {

    private ClientRepository cr;

    @Override
    public void configure(MapperFactory factory) {
        super.configure(factory);
        factory.classMap(Client.class, ClientDtoRabbit.class)
                .byDefault()
                .register();

        factory.classMap(NewClientRequestDto.class, Client.class)
                .byDefault()
                .register();

        factory.classMap(NewClientRequestDto.class, ClientDtoRabbit.class)
                .byDefault()
                .register();

        factory.classMap(Order.class, OrderDto.class)
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapBtoA(OrderDto orderDto, Order order, MappingContext context) {
                        super.mapBtoA(orderDto, order, context);
                        order.setClient(cr.findById(orderDto.getClientId()).orElseThrow());
                    }
                })
                .byDefault()
                .register();
    }
}
