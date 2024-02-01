package com.jairoprogramador.infrastructure.dataproviders.postgress.factory;

import com.jairoprogramador.core.model.entity.UserEntity;
import com.jairoprogramador.infrastructure.dataproviders.postgress.models.UserModel;

public interface UserFactory {
    UserModel createModel(UserEntity user);
    UserEntity createEntity(UserModel user);
}
