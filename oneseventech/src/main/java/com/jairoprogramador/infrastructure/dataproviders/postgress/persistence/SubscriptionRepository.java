package com.jairoprogramador.infrastructure.dataproviders.postgress.persistence;

import com.jairoprogramador.infrastructure.dataproviders.postgress.models.SubscriptionModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionModel, BigInteger> {

}