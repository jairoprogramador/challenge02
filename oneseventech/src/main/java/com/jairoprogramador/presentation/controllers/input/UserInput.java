package com.jairoprogramador.presentation.controllers.input;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInput {
    private String email;
    private String password;
    private String name;
    private String lastName;
    private Boolean newsletters;
}
