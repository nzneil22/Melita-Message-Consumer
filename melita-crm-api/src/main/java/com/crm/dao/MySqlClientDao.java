package com.crm.dao;

import com.crm.models.Client;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;

@Data
@Service
@RequiredArgsConstructor
public class MySqlClientDao implements ClientDao {

    private final ClientRepository clientRepository;

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Client save(Client client) {
        clientRepository.save(client);
        return client;
    }

    @Override

    public Optional<Client> find(UUID clientId) {
        return clientRepository.findById(clientId);
    }
}
