package com.jairoprogramador.infrastructure.dataproviders.postgress.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Data
public class PaymentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    @Temporal(TemporalType.DATE)
    private LocalDate createDate;
    @Temporal(TemporalType.DATE)
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "id_user")
    UserModel user;

    @ManyToOne
    @JoinColumn(name = "id_subscription")
    SubscriptionModel subscription;

}
