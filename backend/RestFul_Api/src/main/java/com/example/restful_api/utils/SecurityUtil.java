package com.example.restful_api.utils;

import com.example.restful_api.dtos.response.UserResponse;
import com.nimbusds.jose.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    public String createAccessToken(Authentication authentication, UserResponse userResponse) {
        Instant now = Instant.now();
        Instant validity = now.plus(accessTokenExpiration, ChronoUnit.SECONDS);
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(authentication.getName())
                .claim("user", mapUser(userResponse))
                .claim("permission", authentication.getAuthorities())
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
}
