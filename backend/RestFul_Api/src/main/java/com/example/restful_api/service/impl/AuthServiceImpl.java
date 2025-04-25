package com.example.restful_api.service.impl;

import com.example.restful_api.dtos.request.auth.LoginRequest;
import com.example.restful_api.dtos.request.auth.LoginResponse;
import com.example.restful_api.dtos.response.UserResponse;
import com.example.restful_api.entity.Users;
import com.example.restful_api.service.IAuthService;
import com.example.restful_api.service.IUserService;
import com.example.restful_api.utils.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private AuthenticationManagerBuilder managerBuilder;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IUserService userService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public LoginResponse<Object> handleLogin(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Users usersDB = userService.findByEmail(authentication.getName());
        UserResponse userResponse = mapper.map(usersDB, UserResponse.class);

        String accessToken = securityUtil.createAccessToken(authentication, userResponse);
        LoginResponse<Object> response = new LoginResponse<>(accessToken, userResponse);

        String refreshToken = securityUtil.createReFreshToken(loginRequest.getUsername(), userResponse);
        usersDB.setRefreshToken(refreshToken);
        userService.updateUser(userResponse.getId(), usersDB);

        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
//                .httpOnly(true)
                .build();

        return response;
    }
}
