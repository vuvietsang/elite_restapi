package com.example.elite.utils;

import com.example.elite.entities.Product;
import com.example.elite.entities.User;
import com.example.elite.filter.ProductSpecificationBuilder;
import com.example.elite.jwt.JwtConfig;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static
    Specification buildProductSpecifications(String search){
        ProductSpecificationBuilder builder = new ProductSpecificationBuilder();

        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");

        Matcher matcher = pattern.matcher(search + ",");

        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<Product> spec = builder.build();
        return spec;
    }
}
