package com.example.restful_api.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsersRequest {

    @NotBlank(message = "Please enter name")
    private String name;

    @NotBlank(message = "Please enter email")
    @Email(message = "Email isn't valid")
    private String email;

    @NotBlank(message = "Please enter email")
    private String password;

    public UsersRequest() {
    }

    public UsersRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public @NotBlank(message = "Please enter name") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Please enter name") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Please enter email") @Email(message = "Email isn't valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Please enter email") @Email(message = "Email isn't valid") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Please enter email") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Please enter email") String password) {
        this.password = password;
    }
}
