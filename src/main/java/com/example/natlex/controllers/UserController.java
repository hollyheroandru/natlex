package com.example.natlex.controllers;

import com.example.natlex.dtos.requests.user.CreateUserRequest;
import com.example.natlex.exceptions.BadRequestException;
import com.example.natlex.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/natlex/api/v1/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createUser(@Valid @RequestBody CreateUserRequest request)
            throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }
}
