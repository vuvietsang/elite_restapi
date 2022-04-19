package com.example.elite.controller.user;

import com.example.elite.jwt.JwtConfig;
import com.example.elite.repository.RoleRepository;
import com.example.elite.repository.UserRepository;
import com.example.elite.services.implement.UserServiceImplement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private SecretKey secretKey;
    @Mock
    private JwtConfig jwtConfig;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private RoleRepository roleRepository;

    private UserServiceImplement undertest;

    @BeforeEach
    void setUp(){
        undertest = new UserServiceImplement(userRepository,secretKey,jwtConfig,passwordEncoder,authenticationManager,roleRepository);
    }



    @Test
    void login_WithValidArgument_ShoudReturnLoginResponseDTO() {

    }

    @Test
    @Disabled
    void register() {
    }
}