package com.example.elite.services;

import com.example.elite.dto.AddUserDto;
import com.example.elite.dto.LoginDto;
import com.example.elite.dto.LoginResponseDto;
import com.example.elite.dto.UserDto;
import com.example.elite.entities.User;
import org.springframework.data.domain.Page;

import javax.management.relation.RoleNotFoundException;

public interface UserService {
    public UserDto findByUserName(String username);

    public LoginResponseDto login(LoginDto user);

    public LoginResponseDto register(User user) throws RoleNotFoundException;

    public UserDto deleteUserById(int userId);

    public UserDto addUser(AddUserDto user);

    public UserDto updateUser(User user, int id);

    public Page<UserDto> getAllUser(int pageNum, int pageSize);

    public UserDto getUserById(int userId);
}
