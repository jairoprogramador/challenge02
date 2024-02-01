package com.jairoprogramador.core.service;

import com.jairoprogramador.core.model.entity.UserEntity;

public interface UserService {
    UserEntity save(UserEntity user);
    UserEntity findByEmail(String email);
}
