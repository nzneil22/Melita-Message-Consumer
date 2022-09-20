package com.melitamessageconsumer.dao;

import com.melitamessageconsumer.models.Client;

import java.util.Optional;

public interface ClientDao {

    Client save(Client client);

    Optional<Client> find(String id);
}
