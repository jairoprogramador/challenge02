package com.jairoprogramador.controller;

import com.jairoprogramador.entities.SubscriptionEntity;
import com.jairoprogramador.services.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

@RestController
@AllArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<Iterable<SubscriptionEntity>> get() {
        try {
            return ResponseEntity.ok(subscriptionService.findAll());
        }catch (HttpStatusCodeException exception){
            return ResponseEntity.status(exception.getStatusCode()).build();
        }
    }

}
