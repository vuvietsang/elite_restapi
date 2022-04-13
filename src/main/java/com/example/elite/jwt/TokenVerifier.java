package com.example.elite.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@RequiredArgsConstructor
public class TokenVerifier extends OncePerRequestFilter {
    private final JwtConfig jwtConfig;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader==null||authorizationHeader==""||!authorizationHeader.startsWith(jwtConfig.getTokenPrefix())){
            filterChain.doFilter(request,response);
            return;
        }
        try {
            String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(),"");
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes())).build().parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            String username = body.getSubject();
            var authorities = (List<Map<String, String>>)body.get("authorities");
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(m->new SimpleGrantedAuthority("ROLE_"+m.get("authority"))).collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,simpleGrantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (JwtException e){
            throw new IllegalStateException("Token can not be trusted");
        }
        filterChain.doFilter(request,response);
    }
}
