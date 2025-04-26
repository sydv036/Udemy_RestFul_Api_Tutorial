package com.example.restful_api.dtos.response;

public class UserGetAccountResponse {
    private UserResponse user;

    public UserGetAccountResponse() {
    }

    public UserGetAccountResponse(UserResponse user) {
        this.user = user;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
