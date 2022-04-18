package com.example.elite.controller.user;

import com.example.elite.repository.UserRepository;
import com.example.elite.services.implement.UserServiceImplement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class UserControllerTest {

    @Mock
    private UserRepository userRepository;
    private UserServiceImplement undertest;
    @BeforeEach
    void setUp(){
        undertest = new UserServiceImplement(null,null,null,null,null,null);
    }
    @Test
    void login() {
    }

    @Test
    void register() {
    }
}