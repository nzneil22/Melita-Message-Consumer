package com.crm.dao;


import com.crm.models.Client;

import java.util.Optional;
import java.util.UUID;

public interface ClientDao {

    Client save(Client client);

    Optional<Client> find(UUID id);
}
