package com.example.elite.services.implement;

import com.example.elite.dto.UserDTO;
import com.example.elite.entities.User;
import com.example.elite.jwt.JwtConfig;
import com.example.elite.repository.RoleRepository;
import com.example.elite.repository.UserRepository;
import com.example.elite.services.UserService;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@ExtendWith(MockitoExtension.class)
class UserServiceImplementTest {
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
    @InjectMocks
    UserServiceImplement userService;

    private ModelMapper modelMapper;


    @BeforeEach
    void setUp(){
        modelMapper = new ModelMapper();
    }
    @Test
    void findByUserName() {
        User user = new User();
        user.setUsername("sangvv");
        Mockito.when(userRepository.findUserByUsername("sangvv")).thenReturn(user);
        UserDTO userDTO = modelMapper.map(user,UserDTO.class);
        Assertions.assertEquals(userService.findByUserName("sangvv"),userDTO);
    }

    @Test
    void login() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("sang", "123");
        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
    }

    @Test
    void register() {

    }

    @Test
    void deleteUserById() {
    }

    @Test
    void addUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void getAllUser() {
    }
}