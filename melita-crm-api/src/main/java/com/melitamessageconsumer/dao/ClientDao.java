package com.melitamessageconsumer.dao;

import com.melitamessageconsumer.models.Client;

import java.util.Optional;
import java.util.UUID;

public interface ClientDao {

    Client save(Client client);

    Optional<Client> find(UUID id);
}
