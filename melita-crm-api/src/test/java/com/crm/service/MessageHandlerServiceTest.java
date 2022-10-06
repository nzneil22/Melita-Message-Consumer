package com.crm.service;

import com.crm.dao.ClientRepository;
import com.crm.dao.MySqlClientDao;
import com.crm.models.Client;
import com.crm.models.FullName;
import com.crm.models.InstallationAddress;
import com.crm.models.Order;
import com.melita_task.api.mapper.CustomMapper;
import com.melita_task.api.models.FullNameUpdate;
import com.melita_task.api.models.InstallationAddressUpdate;
import com.melita_task.api.models.requests.UpdateClientRequest;
import com.melita_task.contract.ClientDto;
import com.melita_task.contract.enums.LobTypes;
import com.melita_task.contract.enums.OrderStatus;
import com.melita_task.contract.events.CreateClientEventDto;
import com.melita_task.contract.events.SubmitOrdersEventDto;
import com.melita_task.contract.events.UpdateClientEventDto;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j

@ExtendWith(SpringExtension.class)

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        value = {MySqlClientDao.class, ClientRepository.class}))

@TestPropertySource("classpath:application.yml")

@ContextConfiguration(classes = MessageHandlerServiceTest.MessageHandlerServiceTestConfig.class)
class MessageHandlerServiceTest {

    @Autowired
    private MessageHandlerService sut;

    @Autowired
    private MapperFacade mapper;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void handle_whenGivenCreateClientRequestDto_shouldSaveTheClientToDatabase(){

        final Client client = new Client(new FullName("firstName", "middleName", "lastName"),
                new InstallationAddress("island", "town", "street", "building"));

        final ClientDto clientDto = mapper.map(client, ClientDto.class);
        final CreateClientEventDto createClientEventDto = new CreateClientEventDto(clientDto);

        sut.handle(createClientEventDto);
        Assertions.assertThat(testEntityManager.find(Client.class, client.getId()).getId()).isEqualTo(client.getId());

    }

    @Test
    void handle_whenGivenUpdateClientRequestDto_shouldUpdateTheClientInDatabase(){

        final Client client = new Client(new FullName("firstName", "middleName", "lastName"),
                new InstallationAddress("island", "town", "street", "building"));

        testEntityManager.persistAndFlush(client);

        final UpdateClientRequest updateClientRequest = new UpdateClientRequest(
                new FullNameUpdate(null, "middleName2", null),
                new InstallationAddressUpdate("islandNew", "townNew", "streetNew", "buildingNew"));

        client.update(updateClientRequest);

        final ClientDto clientDto = mapper.map(client, ClientDto.class);

        final UpdateClientEventDto updateClientEventDto = new UpdateClientEventDto(clientDto);

        sut.handle(updateClientEventDto);
        Assertions.assertThat(testEntityManager.find(Client.class, client.getId())
                .getFullName()
                .getMiddleName())
                .isNotEqualTo("middleName");

        Assertions.assertThat(testEntityManager.find(Client.class, client.getId())
                .getFullName()
                .getMiddleName())
                .isEqualTo("middleName2");

        Assertions.assertThat(testEntityManager.find(Client.class, client.getId())
                .getInstallationAddress()
                .getIsland())
                .isNotEqualTo("island");

        Assertions.assertThat(testEntityManager.find(Client.class, client.getId())
                .getInstallationAddress()
                .getIsland())
                .isEqualTo("islandNew");
    }

    @Test
    void handle_whenGivenSubmitClientRequestDtoWithOrders_shouldWriteTheClientAndItsOrdersToDatabase() throws ParseException {

        final Client client = new Client(new FullName("firstName", "middleName", "lastName"),
                new InstallationAddress("island", "town", "street", "building"));

        testEntityManager.persistAndFlush(client);

        final String sDate = "31-Dec-2050 23:37:50";
        final Date date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(sDate);
        final Order order1 = new Order(UUID.randomUUID(), client, 100, LobTypes.INT, date, OrderStatus.SUBMITTED);
        final Order order2 = new Order(UUID.randomUUID(), client, 200, LobTypes.MOB, date, OrderStatus.SUBMITTED);
        final Order order3 = new Order(UUID.randomUUID(), client, 300, LobTypes.TV, date, OrderStatus.SUBMITTED);

        client.getOrders().add(order1);
        client.getOrders().add(order2);
        client.getOrders().add(order3);

        order1.setClient(null);
        order2.setClient(null);
        order3.setClient(null);

        final ClientDto clientDto = mapper.map(client, ClientDto.class);

        final SubmitOrdersEventDto submitOrdersEventDto = new SubmitOrdersEventDto(clientDto);

        sut.handle(submitOrdersEventDto);
        Assertions.assertThat(testEntityManager.find(Client.class, client.getId())
                        .getOrders())
                .isNotEmpty()
                .contains(order1)
                .contains(order2)
                .contains(order3);
    }



    @EntityScan(basePackages = "com.crm")
    @EnableJpaRepositories(basePackages = "com.crm")
    public static class MessageHandlerServiceTestConfig {

        @Bean
        public MessageHandlerService messageHandlerService(final CustomMapper mapper,
                                                           final ClientRepository cr) {
            return new MessageHandlerService(mapper, cr);
        }

        @Bean
        public CustomMapper customMapper(){
            return new CustomMapper();
        }
    }

}