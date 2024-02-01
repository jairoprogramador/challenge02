package com.jairoprogramador.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table(name = "subscriptions")
@Data
@NoArgsConstructor
public class SubscriptionEntity {

    public SubscriptionEntity(BigInteger id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String type;
    private BigDecimal price;
    private Boolean status;
}
