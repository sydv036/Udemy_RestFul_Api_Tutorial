package com.example.restful_api.controller;

import com.example.restful_api.dtos.request.auth.LoginRequest;
import com.example.restful_api.dtos.request.auth.LoginResponse;
import com.example.restful_api.dtos.response.UserGetAccountResponse;
import com.example.restful_api.dtos.response.UserResponse;
import com.example.restful_api.entity.Users;
import com.example.restful_api.service.IAuthService;
import com.example.restful_api.service.IUserService;
import com.example.restful_api.utils.CookieConstant;
import com.example.restful_api.utils.SecurityUtil;
import com.example.restful_api.utils.annotation.ApiMessage;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManagerBuilder managerBuilder;

    @Autowired
    private ModelMapper mapper;


    @PostMapping("/login")
    @ApiMessage("Login")
    public ResponseEntity<?> authLogin(@Valid @RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Users usersDB = userService.findByEmail(authentication.getName());
        UserResponse userResponse = mapper.map(usersDB, UserResponse.class);

        String accessToken = securityUtil.createAccessToken(loginRequest.getUsername(), userResponse);
        LoginResponse<Object> response = new LoginResponse<>(accessToken, userResponse);

        String refreshToken = securityUtil.createReFreshToken(loginRequest.getUsername(), userResponse);
        usersDB.setRefreshToken(refreshToken);
        userService.updateUser(userResponse.getId(), usersDB);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, CookieConstant.cookieCustom(refreshToken, securityUtil.refreshTokenExpiration))
                .body(response);
    }

    @GetMapping("/account")
    @ApiMessage("Fetch account")
    public ResponseEntity<?> refreshUser(HttpHeaders httpHeaders) {
        String email = SecurityUtil.getCurrentUserLogin();
        Users users = userService.findByEmail(email);
        UserResponse userResponse = mapper.map(users, UserResponse.class);
        return ResponseEntity.ok().body(new UserGetAccountResponse(userResponse));
    }

    @GetMapping("/refresh")
    @ApiMessage("Get User by refresh token")
    public ResponseEntity<Object> getRefreshToken(@CookieValue(name = "refresh_token", defaultValue = "refresh_token") String refreshToken) {
        Jwt jwt = securityUtil.checkValidRefreshToken(refreshToken);
        String email = jwt.getSubject();
        Users users = userService.findByEmailAndRefreshToken(email, refreshToken);
        UserResponse userResponse = mapper.map(users, UserResponse.class);

        String accessToken = securityUtil.createAccessToken(email, userResponse);

        String newRefreshToken = securityUtil.createReFreshToken(email, userResponse);
        users.setRefreshToken(newRefreshToken);
        userService.updateUser(users.getId(), users);

        LoginResponse<Object> response = new LoginResponse<>(accessToken, userResponse);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, CookieConstant.cookieCustom(newRefreshToken, securityUtil.refreshTokenExpiration))
                .body(response);
    }

    @GetMapping("/logout")
    @ApiMessage("Logout user")
    public ResponseEntity<Void> authLogout(HttpServletResponse response) {
        String email = SecurityUtil.getCurrentUserLogin();
        Users users = userService.findByEmail(email);
        users.setRefreshToken(null);
        userService.updateUser(users.getId(), users);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, CookieConstant.cookieCustom(null, 0))
                .body(null);
    }
}





