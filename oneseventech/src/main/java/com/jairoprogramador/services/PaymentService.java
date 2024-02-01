package com.jairoprogramador.services;

import com.jairoprogramador.controller.input.PaymentInput;
import com.jairoprogramador.entities.PaymentEntity;
import com.jairoprogramador.entities.SubscriptionEntity;
import com.jairoprogramador.entities.UserEntity;
import com.jairoprogramador.repositories.PaymentRepository;
import com.jairoprogramador.repositories.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;

    public PaymentEntity save(PaymentInput payment) {

        Optional<SubscriptionEntity> subscriptionExist = this.subscriptionRepository.findById(payment.getSubscriptionId());
        if(subscriptionExist.isEmpty()){
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Subscription not exist");
        }

        LocalDate expirationDate = LocalDate.now();
        SubscriptionEntity subscription = subscriptionExist.get();
        if(subscription.getType().equals("ANUAL")){
            expirationDate = expirationDate.plusYears(1);
        }

        if(subscription.getType().equals("MENSUAL")){
            expirationDate = expirationDate.plusMonths(1);
        }

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setUser(new UserEntity(payment.getUserId()));
        paymentEntity.setSubscription(new SubscriptionEntity(payment.getSubscriptionId()));
        paymentEntity.setCreateDate(LocalDate.now());
        paymentEntity.setExpirationDate(expirationDate);

        return this.paymentRepository.save(paymentEntity);
    }

    public List<PaymentEntity> findByUserId(BigInteger userId) {
        return this.paymentRepository.findByUserIdAndExpirationDateAfter(userId, LocalDate.now()).orElse(Collections.emptyList());
    }

}