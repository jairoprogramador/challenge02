package com.jairoprogramador.repositories;

import com.jairoprogramador.entities.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<ClientEntity, BigInteger> {
    Optional<ClientEntity> findByClientId(String clientId);
}