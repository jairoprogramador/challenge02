package com.jairoprogramador.controller;

import com.jairoprogramador.entities.SubscriptionEntity;
import com.jairoprogramador.services.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Map;

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

    @GetMapping("/payment/{id}")
    public ResponseEntity<Map<String, String>> getUrlPayment(@PathVariable(required = true) int id) {
        try {
            var url = subscriptionService.getURLPayment(BigInteger.valueOf(id));
            return ResponseEntity.ok(Collections.singletonMap("url", url));
        }catch (HttpStatusCodeException exception){
            return ResponseEntity.status(exception.getStatusCode()).build();
        }
    }

}
