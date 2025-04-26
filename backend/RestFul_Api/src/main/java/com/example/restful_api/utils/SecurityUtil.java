package com.example.restful_api.utils;

import com.example.restful_api.dtos.response.UserResponse;
import com.nimbusds.jose.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class SecurityUtil {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Value("${jwt.base64-secret}")
    private String secret;

    @Value("${jwt.access-token-validity-in-seconds}")
    private Integer accessTokenExpiration;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    public Integer refreshTokenExpiration;


    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;


    private Map<String, String> mapUser(UserResponse userResponse) {
        Map<String, String> mapUser = new HashMap<>();
        mapUser.put("id", String.valueOf(userResponse.getId()));
        mapUser.put("email", userResponse.getEmail());
        mapUser.put("name", userResponse.getName());
        return mapUser;
    }

    public String createAccessToken(String email, UserResponse userResponse) {
        Instant now = Instant.now();
        Instant validity = now.plus(accessTokenExpiration, ChronoUnit.SECONDS);
        List<String> listAuthority = new ArrayList<>(List.of("ROLE_ADMIN", "ROLE_USER"));
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(email)
                .claim("user", mapUser(userResponse))
                .claim("permission", listAuthority)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsSet)).getTokenValue();
    }

    public String createReFreshToken(String email, UserResponse userResponse) {
        Instant now = Instant.now();
        Instant validity = now.plus(refreshTokenExpiration, ChronoUnit.SECONDS);

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(email)
                .claim("user", mapUser(userResponse))
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsSet)).getTokenValue();
    }

    public static String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        var result = securityContext.getAuthentication().getName();
        return Optional.ofNullable(result).isPresent() ? result : null;
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(secret).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, JWT_ALGORITHM.getName());
    }

    public Jwt checkValidRefreshToken(String refreshToken) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(getSecretKey()).macAlgorithm(JWT_ALGORITHM).build();
        try {
            return jwtDecoder.decode(refreshToken);
        } catch (AuthenticationException e) {
            throw e;
        }
    }
}
