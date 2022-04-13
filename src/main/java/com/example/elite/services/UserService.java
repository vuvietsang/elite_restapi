package com.example.elite.services;

import com.example.elite.dto.LoginDTO;
import com.example.elite.dto.LoginResponseDTO;
import com.example.elite.entities.User;

public interface UserService {
    public User findByUserName(String username);
    public LoginResponseDTO login(LoginDTO user);
    public LoginResponseDTO register(User user) throws Exception;
    public boolean deleteUserById(int userId);
    public boolean addUser(User user);
    public boolean updateUser(User user,int id);
}
