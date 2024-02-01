package com.jairoprogramador.controller;

import com.jairoprogramador.controller.input.PaymentInput;
import com.jairoprogramador.entities.PaymentEntity;
import com.jairoprogramador.services.PaymentService;
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

    @PostMapping()
    public ResponseEntity<PaymentEntity> createPayment(@RequestBody PaymentInput paymentInput) {
        try {
            return ResponseEntity.ok(paymentService.save(paymentInput));
        }catch (HttpStatusCodeException exception){
            return ResponseEntity.status(exception.getStatusCode()).build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PaymentEntity>> get(@PathVariable(required = true) int id) {
        try {
            return ResponseEntity.ok(paymentService.findByUserId(BigInteger.valueOf(id)));
        }catch (HttpStatusCodeException exception){
            return ResponseEntity.status(exception.getStatusCode()).build();
        }
    }
}
