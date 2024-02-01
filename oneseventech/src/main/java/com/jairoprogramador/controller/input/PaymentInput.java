package com.jairoprogramador.controller.input;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class PaymentInput {
    private BigInteger userId;
    private BigInteger subscriptionId;
}
