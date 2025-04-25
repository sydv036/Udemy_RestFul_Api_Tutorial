package com.example.restful_api.dtos.request.auth;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Please enter Username")
    private String username;
    @NotBlank(message = "Please enter Password")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public @NotBlank(message = "Please enter Username") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Please enter Username") String username) {
        this.username = username;
    }

    public @NotBlank(message = "Please enter Password") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Please enter Password") String password) {
        this.password = password;
    }
}
