package com.jairoprogramador.core.model.aggregate;

import com.jairoprogramador.core.model.entity.UserEntity;
import com.jairoprogramador.core.model.valueobject.TypeSubscriptionValueObject;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
public class SubcriptionAgregate {

    private BigInteger id;
    private LocalDate expirationDate;
    private UserEntity user;
    private TypeSubscriptionValueObject type;

    public SubcriptionAgregate(UserEntity user, TypeSubscriptionValueObject type) {
        this.user = user;
        this.type = type;
        this.expirationDate = this.calculateExpiration(type);
    }

    public LocalDate calculateExpiration(TypeSubscriptionValueObject type){
        if(type.getType() != null){
            if(type.getType().equals("ANUAL")){
                return expirationDate.plusYears(1);
            }

            if(type.getType().equals("MENSUAL")){
                return  expirationDate.plusMonths(1);
            }
        }
        return LocalDate.now();
    }

    public LocalDate calculateExpiration(String type){
        if(type != null){
            if(type.equals("ANUAL")){
                return expirationDate.plusYears(1);
            }

            if(type.equals("MENSUAL")){
                return  expirationDate.plusMonths(1);
            }
        }
        return LocalDate.now();
    }
}
