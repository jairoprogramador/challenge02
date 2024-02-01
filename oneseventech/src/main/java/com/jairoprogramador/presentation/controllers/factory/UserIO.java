package com.jairoprogramador.presentation.controllers.factory;

import com.jairoprogramador.core.model.entity.UserEntity;
import com.jairoprogramador.presentation.controllers.input.UserInput;
import com.jairoprogramador.presentation.controllers.output.UserOutput;

public interface UserIO {
    UserEntity createEntityFromInput(UserInput user);
    UserOutput createOutputFromEntity(UserEntity user);
}
