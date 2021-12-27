package com.example.natlex.services.impl;

import com.example.natlex.dtos.requests.user.CreateUserRequest;
import com.example.natlex.exceptions.BadRequestException;
import com.example.natlex.models.User;
import com.example.natlex.repositories.UserRepository;
import com.example.natlex.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public Long createUser(CreateUserRequest request) throws BadRequestException {
        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BadRequestException("Username already taken");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return user.getId();
    }
}
