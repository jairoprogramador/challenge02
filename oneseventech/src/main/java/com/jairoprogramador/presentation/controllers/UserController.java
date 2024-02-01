package com.jairoprogramador.presentation.controllers;

import com.jairoprogramador.core.service.UserService;
import com.jairoprogramador.presentation.controllers.factory.UserIO;
import com.jairoprogramador.presentation.controllers.input.UserInput;
import com.jairoprogramador.presentation.controllers.output.UserOutput;
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
    private final UserIO userIO;

    @GetMapping
    public Map<String, String> getUser() {
        return Collections.singletonMap("user", "get");
    }

    @PostMapping()
    public ResponseEntity<UserOutput> createUser(@RequestBody UserInput user) {
        try {
            var userEntity = userService.save(this.userIO.createEntityFromInput(user));
            return ResponseEntity.ok(this.userIO.createOutputFromEntity(userEntity));
        }catch (HttpStatusCodeException exception){
            return ResponseEntity.status(exception.getStatusCode()).build();
        }
    }
}
