package com.jairoprogramador.repositories;

import com.jairoprogramador.entities.PaymentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentEntity, BigInteger> {
    Optional<PaymentEntity> findByUserIdAndSubscriptionId(BigInteger userId, BigInteger subscriptionId);
    Optional<List<PaymentEntity>> findByUserIdAndExpirationDateAfter(BigInteger userId, LocalDate date);
}