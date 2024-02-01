package com.jairoprogramador.controller.output;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class PaymentOutput {
    private BigInteger userId;
    private BigInteger subscriptionId;
}
