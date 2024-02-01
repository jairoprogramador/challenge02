package com.jairoprogramador.infrastructure.dataproviders.postgress.persistence;

import com.jairoprogramador.infrastructure.dataproviders.postgress.models.PaymentModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentModel, BigInteger> {
    Optional<PaymentModel> findByUserIdAndSubscriptionId(BigInteger userId, BigInteger subscriptionId);
    Optional<List<PaymentModel>> findByUserIdAndExpirationDateAfter(BigInteger userId, LocalDate date);
}