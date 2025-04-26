package com.example.restful_api.dtos.request.auth;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


public class LoginResponse<T> {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("user")
    private T data;

    public LoginResponse() {
    }

    public LoginResponse(String accessToken, T data) {
        this.accessToken = accessToken;
        this.data = data;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
