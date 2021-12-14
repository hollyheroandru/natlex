package com.example.natlex.dtos.requests.user;

import javax.validation.constraints.Size;

public class CreateUserRequest {
    @Size(min = 3, message = "Username cannot be less than 3 sings")
    private String username;
    @Size(min = 3, message = "Password cannot be less than 3 sings")
    private String password;

    public CreateUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public CreateUserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
