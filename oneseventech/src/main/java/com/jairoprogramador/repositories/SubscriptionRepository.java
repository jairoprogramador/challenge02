package com.jairoprogramador.repositories;

import com.jairoprogramador.entities.SubscriptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, BigInteger> {

}