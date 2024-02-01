package com.jairoprogramador.core.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class UserEntity {

    private BigInteger id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private Boolean newsletters;

}
