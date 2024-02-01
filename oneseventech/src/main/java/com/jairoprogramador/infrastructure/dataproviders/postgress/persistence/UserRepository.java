package com.jairoprogramador.infrastructure.dataproviders.postgress.persistence;

import com.jairoprogramador.infrastructure.dataproviders.postgress.models.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserModel, BigInteger>{
   Optional<UserModel> findByEmail(String email);
}