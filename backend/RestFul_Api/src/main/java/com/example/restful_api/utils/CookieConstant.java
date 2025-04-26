package com.example.restful_api.utils;

import org.springframework.http.ResponseCookie;

public class CookieConstant {
    public static String cookieCustom(String refreshToken, Integer expiration) {
        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", refreshToken)
                .httpOnly(true)
                .path("/")
                .secure(true)
                .maxAge(expiration)
                .build();
        return responseCookie.toString();
    }

}
