package com.jairoprogramador.services;

import com.jairoprogramador.entities.SubscriptionEntity;
import com.jairoprogramador.repositories.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public  Iterable<SubscriptionEntity> findAll(){
        return subscriptionRepository.findAll();
    }

}