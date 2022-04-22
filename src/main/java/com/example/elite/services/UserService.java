package com.example.elite.services;

import com.example.elite.dto.LoginDTO;
import com.example.elite.dto.LoginResponseDTO;
import com.example.elite.dto.UserDTO;
import com.example.elite.entities.User;
import org.springframework.data.domain.Page;

import javax.management.relation.RoleNotFoundException;

public interface UserService {
    public User findByUserName(String username);

    public LoginResponseDTO login(LoginDTO user);

    public LoginResponseDTO register(User user) throws RoleNotFoundException;

    public UserDTO deleteUserById(int userId);

    public UserDTO addUser(User user);

    public UserDTO updateUser(User user, int id);

    public Page<UserDTO> getAllUser(int pageNum, int pageSize);
}
