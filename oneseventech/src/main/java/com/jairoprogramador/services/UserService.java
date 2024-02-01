package com.jairoprogramador.services;

import com.jairoprogramador.entities.RoleEntity;
import com.jairoprogramador.entities.UserEntity;
import com.jairoprogramador.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserEntity save(UserEntity user) {
        Optional<UserEntity> userExist =this.userRepository.findByEmail(user.getEmail());
        if(userExist.isPresent()){
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User already exist");
        }

        RoleEntity roleAdmin = new RoleEntity();
        roleAdmin.setName("ROLE_ADMIN");

        user.setCreateDate(LocalDate.now());
        user.setRoles(Set.of(roleAdmin));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return this.userRepository.save(user);
    }

}