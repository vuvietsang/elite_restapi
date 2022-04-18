package com.example.elite.utils;

import com.example.elite.entities.User;
import com.example.elite.jwt.JwtConfig;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
@Configurable
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class Utils {
    public static String buildJWT(Authentication authenticate, User userAuthenticated,SecretKey secretKey,JwtConfig jwtConfig){
        String token = Jwts.builder().setSubject(authenticate.getName())
                .claim("authorities",authenticate.getAuthorities())
                .claim("userId",userAuthenticated.getId())
                .setIssuedAt((new Date())).setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays()))).signWith(secretKey).compact();
        return token;
    };
}
