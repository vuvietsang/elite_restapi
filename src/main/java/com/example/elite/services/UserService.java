package com.example.elite.services;

import com.example.elite.dto.LoginDTO;
import com.example.elite.dto.LoginResponseDTO;
import com.example.elite.entities.User;

public interface UserService {
    public User findByUserName(String username);
    public LoginResponseDTO login(LoginDTO user);
    LoginResponseDTO register(User user);

}
