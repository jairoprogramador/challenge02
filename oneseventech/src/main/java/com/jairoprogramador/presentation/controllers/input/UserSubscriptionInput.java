package com.jairoprogramador.presentation.controllers.input;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class UserSubscriptionInput {
    private BigInteger userId;
    private BigInteger subscriptionId;
}
