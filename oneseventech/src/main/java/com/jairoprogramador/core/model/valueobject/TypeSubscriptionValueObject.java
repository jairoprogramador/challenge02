package com.jairoprogramador.core.model.valueobject;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

@Getter
public class TypeSubscriptionValueObject {

    BigInteger id;
    private String type;
    private BigDecimal price;

    public TypeSubscriptionValueObject(BigInteger id) {
        this.id = id;
    }

    public TypeSubscriptionValueObject(String type, BigDecimal price) {
        this.type = type;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeSubscriptionValueObject that = (TypeSubscriptionValueObject) o;
        return Objects.equals(type, that.type) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, price);
    }
}
