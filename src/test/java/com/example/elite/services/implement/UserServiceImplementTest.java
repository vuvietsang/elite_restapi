package com.example.elite.services.implement;

import com.example.elite.repository.UserRepository;
import com.example.elite.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceImplementTest {
    @Mock
    private UserRepository userRepository;

    @Autowired
    UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void findByUserName() {
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