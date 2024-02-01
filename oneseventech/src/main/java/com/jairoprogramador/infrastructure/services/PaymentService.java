package com.jairoprogramador.infrastructure.services;

import com.jairoprogramador.core.model.aggregate.SubcriptionAgregate;
import com.jairoprogramador.core.service.UserSubscribeService;
import com.jairoprogramador.infrastructure.dataproviders.postgress.models.PaymentModel;
import com.jairoprogramador.infrastructure.dataproviders.postgress.models.SubscriptionModel;
import com.jairoprogramador.infrastructure.dataproviders.postgress.models.UserModel;
import com.jairoprogramador.infrastructure.dataproviders.postgress.persistence.PaymentRepository;
import com.jairoprogramador.infrastructure.dataproviders.postgress.persistence.SubscriptionRepository;
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
public class PaymentService implements UserSubscribeService {

    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public SubcriptionAgregate subscribe(SubcriptionAgregate subcriptionAgregate) {
        Optional<SubscriptionModel> subscriptionExist = this.subscriptionRepository.findById(subcriptionAgregate.getType().getId());
        if(subscriptionExist.isEmpty()){
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Subscription not exist");
        }

        PaymentModel paymentEntity = new PaymentModel();
        paymentEntity.setUser(new UserModel(subcriptionAgregate.getUser().getId()));
        paymentEntity.setSubscription(new SubscriptionModel(subscriptionExist.get().getId()));
        paymentEntity.setCreateDate(LocalDate.now());
        paymentEntity.setExpirationDate(subcriptionAgregate.calculateExpiration(subscriptionExist.get().getType()));

        var payment = this.paymentRepository.save(paymentEntity);
        subcriptionAgregate.setId(payment.getId());
        return subcriptionAgregate;
    }

    public List<PaymentModel> findByUserId(BigInteger userId) {
        return this.paymentRepository.findByUserIdAndExpirationDateAfter(userId, LocalDate.now()).orElse(Collections.emptyList());
    }

}