package com.jairoprogramador.controller;

import com.jairoprogramador.entities.UserEntity;
import com.jairoprogramador.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Collections;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public Map<String, String> getUser() {
        return Collections.singletonMap("user", "get");
    }

    @PostMapping()
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        try {
            return ResponseEntity.ok(userService.save(user));
        }catch (HttpStatusCodeException exception){
            return ResponseEntity.status(exception.getStatusCode()).build();
        }
    }
}
