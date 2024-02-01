package com.jairoprogramador.presentation.controllers.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscriptionOutput {
    private BigInteger id;
    private BigInteger userId;
    private BigInteger subscriptionId;
}
