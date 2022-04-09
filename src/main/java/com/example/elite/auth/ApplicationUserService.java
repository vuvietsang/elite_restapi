package com.example.elite.auth;

import com.example.elite.entities.User;
import com.example.elite.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ApplicationUserService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findUserByName();
       if(user==null){
           throw new UsernameNotFoundException("USER_NAME_NOT_FOUND!");
       }
       return new UserDetail(user);
    }
}
