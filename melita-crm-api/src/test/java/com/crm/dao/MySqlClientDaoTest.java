package com.crm.dao;

import com.crm.models.Client;
import com.crm.models.FullName;
import com.crm.models.InstallationAddress;
import com.crm.models.Order;
import com.melita_task.contract.enums.LobTypes;
import com.melita_task.contract.enums.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j(topic = "DEBUG")
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application.yml")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {MySqlClientDao.class, ClientRepository.class}))
class MySqlClientDaoTest {

    @Autowired
    private MySqlClientDao sut;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void save_WithCorrectClient_shouldSaveClientToDatabase() {
        final Client client = new Client(new FullName("firstName", "middleName", "lastName"),
                new InstallationAddress("island", "town", "street", "building"));

        Assertions.assertThat(sut.save(client)).isEqualTo(client);
        Assertions.assertThat(testEntityManager.find(Client.class, client.getId()).getId()).isEqualTo(client.getId());
    }

    @Test
    void save_WithUpdatedCorrectClient_shouldSaveUpdatedClientToDatabase() {
        final Client client = new Client(new FullName("firstName", "middleName", "lastName"),
                new InstallationAddress("island", "town", "street", "building"));

        Assertions.assertThat(sut.save(client)).isEqualTo(client);

        client.getFullName().setMiddleName("middleNameNew");
        client.getInstallationAddress().setIsland("islandNew");

        Assertions.assertThat(sut.save(client)).isEqualTo(client);
        Assertions.assertThat(testEntityManager
                        .find(Client.class, client.getId())
                        .getId())
                        .isEqualTo(client.getId());

        Assertions.assertThat(testEntityManager
                        .find(Client.class, client.getId())
                        .getFullName().getMiddleName())
                        .isEqualTo("middleNameNew");

        Assertions.assertThat(testEntityManager
                        .find(Client.class, client.getId())
                        .getInstallationAddress().getIsland())
                        .isEqualTo("islandNew");
    }


    @Test
    void find_whenGivenExistingClientUUID_shouldReturnOptionalContainingClient() {
        final Client client = new Client(new FullName("firstName", "middleName", "lastName"),
                new InstallationAddress("island", "town", "street", "building"));

        testEntityManager.persistAndFlush(client);

        final Optional<Client> returned = sut.find(client.getId());

        log.info("Returned: <<{}>>", returned);

        Assertions.assertThat(returned)
                .isInstanceOf(Optional.class)
                .isPresent();
    }

    @Test
    void find_whenGivenInvalidClientUUID_shouldReturnEmptyOptional() {

        final Optional<Client> returned = sut.find(UUID.randomUUID());

        log.info("Returned: <<{}>>", returned);

        Assertions.assertThat(returned)
                .isInstanceOf(Optional.class)
                .isEmpty();
    }

    @Test
    void find_whenGivenExistingClientUUIDWithOrders_shouldReturnOptionalContainingClientWithOrders() throws ParseException {
        final Client client = new Client(new FullName("firstName", "middleName", "lastName"),
                new InstallationAddress("island", "town", "street", "building"));

        final String sDate = "31-Dec-2050 23:37:50";
        final Date date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(sDate);
        final Order order = new Order(UUID.randomUUID(), client, 100, LobTypes.INT, date, OrderStatus.SUBMITTED);

        client.getOrders().add(order);

        testEntityManager.persistAndFlush(client);

        final Optional<Client> returned = sut.find(client.getId());

        log.info("Returned: <<{}>>", returned);

        Assertions.assertThat(returned)
                .isInstanceOf(Optional.class)
                .isPresent();

        Assertions.assertThat(returned.get().getOrders())
                .contains(order);
    }
}