package com.jairoprogramador.infrastructure.services;

import com.jairoprogramador.core.model.entity.UserEntity;
import com.jairoprogramador.core.service.UserService;
import com.jairoprogramador.infrastructure.dataproviders.postgress.factory.UserFactory;
import com.jairoprogramador.infrastructure.dataproviders.postgress.models.UserModel;
import com.jairoprogramador.infrastructure.dataproviders.postgress.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserFactory userFactory;

    @Override
    public UserEntity findByEmail(String email) {
        var userModel = this.getByEmail(email);
        return this.userFactory.createEntity(userModel);
    }

    public UserEntity save(UserEntity user) {
        var userOp = this.userRepository.findByEmail(user.getEmail());
        if(userOp.isPresent()){
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User exits");
        }

        var userModelSave = this.userRepository.save(userFactory.createModel(user));
        return this.userFactory.createEntity(userModelSave);
    }

    private UserModel getByEmail(String email){
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User not found"));
    }

}