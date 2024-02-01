package com.jairoprogramador.presentation.controllers.output;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserOutput {
    private String email;
    private String password;
    private String name;
    private String lastName;
    private Boolean newsletters;
}
