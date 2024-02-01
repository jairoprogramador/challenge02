package com.jairoprogramador.presentation.controllers;

import com.jairoprogramador.infrastructure.dataproviders.postgress.models.PaymentModel;
import com.jairoprogramador.infrastructure.services.PaymentService;
import com.jairoprogramador.presentation.controllers.factory.UserSubscriptionIO;
import com.jairoprogramador.presentation.controllers.input.UserSubscriptionInput;
import com.jairoprogramador.presentation.controllers.output.UserSubscriptionOutput;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import java.math.BigInteger;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserSubscriptionIO userSubscriptionIO;

    @PostMapping()
    public ResponseEntity<UserSubscriptionOutput> createPayment(@RequestBody UserSubscriptionInput paymentInput) {
        try {
            var subscription =  paymentService.subscribe(userSubscriptionIO.createEntityFromInput(paymentInput));
            return ResponseEntity.ok(this.userSubscriptionIO.createOutputFromEntity(subscription));
        }catch (HttpStatusCodeException exception){
            return ResponseEntity.status(exception.getStatusCode()).build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PaymentModel>> get(@PathVariable(required = true) int id) {
        try {
            return ResponseEntity.ok(paymentService.findByUserId(BigInteger.valueOf(id)));
        }catch (HttpStatusCodeException exception){
            return ResponseEntity.status(exception.getStatusCode()).build();
        }
    }
}
