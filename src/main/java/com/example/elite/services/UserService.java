package com.example.elite.services;

import com.example.elite.entities.User;

public interface UserService {
    public User findByUserName();
    boolean register(User user);

}
