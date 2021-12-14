package com.example.natlex.services;

import com.example.natlex.dtos.requests.user.CreateUserRequest;
import com.example.natlex.exceptions.BadRequestException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
     Long createUser(CreateUserRequest request)
        throws BadRequestException;
}
