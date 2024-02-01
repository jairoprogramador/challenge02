package com.jairoprogramador.presentation.controllers.common;

import com.jairoprogramador.core.model.entity.UserEntity;
import com.jairoprogramador.presentation.controllers.factory.UserIO;
import com.jairoprogramador.presentation.controllers.input.UserInput;
import com.jairoprogramador.presentation.controllers.output.UserOutput;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommonUserIO implements UserIO {

    @Override
    public UserEntity createEntityFromInput(UserInput user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setLastName(user.getLastName());
        userEntity.setNewsletters(user.getNewsletters());
        return  userEntity;
    }

    @Override
    public UserOutput createOutputFromEntity(UserEntity user) {
        UserOutput userOutput = new UserOutput();
        userOutput.setName(user.getName());
        userOutput.setEmail(user.getEmail());
        userOutput.setPassword(user.getPassword());
        userOutput.setLastName(user.getLastName());
        userOutput.setNewsletters(user.getNewsletters());
        return  userOutput;
    }
}
