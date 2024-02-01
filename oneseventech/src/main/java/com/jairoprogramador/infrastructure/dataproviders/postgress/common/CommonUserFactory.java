package com.jairoprogramador.infrastructure.dataproviders.postgress.common;

import com.jairoprogramador.core.model.entity.UserEntity;
import com.jairoprogramador.infrastructure.dataproviders.postgress.factory.UserFactory;
import com.jairoprogramador.infrastructure.dataproviders.postgress.models.RoleModel;
import com.jairoprogramador.infrastructure.dataproviders.postgress.models.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
@AllArgsConstructor
public class CommonUserFactory implements UserFactory {

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserModel createModel(UserEntity user) {

        RoleModel roleAdmin = new RoleModel();
        roleAdmin.setName("ROLE_ADMIN");

        UserModel userModel = new UserModel();
        userModel.setName(user.getName());
        userModel.setEmail(user.getEmail());
        userModel.setPassword(passwordEncoder.encode(user.getPassword()));
        userModel.setLastName(user.getLastName());
        userModel.setCreateDate(LocalDate.now());
        userModel.setNewsletters(user.getNewsletters());
        userModel.setRoles(Set.of(roleAdmin));
        return  userModel;
    }

    @Override
    public UserEntity createEntity(UserModel user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setLastName(user.getLastName());
        userEntity.setNewsletters(user.getNewsletters());
        return  userEntity;
    }
}
