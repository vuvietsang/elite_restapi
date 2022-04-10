package com.example.elite.services_implement;

import com.example.elite.dto.LoginDTO;
import com.example.elite.dto.LoginResponseDTO;
import com.example.elite.entities.Role;
import com.example.elite.entities.User;
import com.example.elite.jwt.JwtConfig;
import com.example.elite.repository.RoleRepository;
import com.example.elite.repository.UserRepository;
import com.example.elite.services.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;

@Service
public class UserServiceImplement implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  SecretKey secretKey;
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User findByUserName(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public LoginResponseDTO login(LoginDTO user) throws BadCredentialsException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        LoginResponseDTO loginResponseDTO =null;
            Authentication authenticate = authenticationManager.authenticate(authentication);
            if(authenticate.isAuthenticated()){
                User userAuthenticated = userRepository.findUserByUsername(user.getUsername());
                String token = Jwts.builder().setSubject(authenticate.getName())
                        .claim("authorities",authenticate.getAuthorities())
                        .claim("userId",userAuthenticated.getId())
                        .setIssuedAt((new Date())).setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1))).signWith(secretKey).compact();
                loginResponseDTO = LoginResponseDTO.builder()
                        .userId(userAuthenticated.getId())
                        .fullName(userAuthenticated.getFullName())
                        .username(userAuthenticated.getUsername())
                        .email(userAuthenticated.getEmail())
                        .roleName(userAuthenticated.getRole().getRoleName())
                        .token(jwtConfig.getTokenPrefix()+token).build();
            }

        return loginResponseDTO;
    }
    @Override
    public LoginResponseDTO register(User user) {
        LoginResponseDTO loginResponseDTO =null;
        User checkUser = userRepository.findUserByUsername(user.getUsername());
        Role role =roleRepository.findByRoleName("USER");
        if(checkUser==null){
            User userTmp = User.builder().username(user.getUsername()).email(user.getEmail()).fullName(user.getFullName()).role(role)
                            .password(passwordEncoder.encode(user.getPassword())).status(true).createDate(LocalDate.now()).build();
            userRepository.save(userTmp);
            LoginDTO loginDTO = new LoginDTO(user.getUsername(),user.getPassword());
            loginResponseDTO = login(loginDTO);
        }
        return loginResponseDTO;
    }
}
