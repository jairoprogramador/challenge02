package com.jairoprogramador.infrastructure.dataproviders.postgress.persistence;

import com.jairoprogramador.infrastructure.dataproviders.postgress.models.ClientModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<ClientModel, BigInteger> {
    Optional<ClientModel> findByClientId(String clientId);
}