package com.melitamessageconsumer.dao;

import com.melitamessageconsumer.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    List<Client> findAllById(String id);
}
