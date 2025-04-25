package com.example.restful_api.service;

import com.example.restful_api.dtos.request.auth.LoginRequest;
import com.example.restful_api.dtos.request.auth.LoginResponse;

public interface IAuthService {

    LoginResponse<Object> handleLogin(LoginRequest loginRequest);
}
