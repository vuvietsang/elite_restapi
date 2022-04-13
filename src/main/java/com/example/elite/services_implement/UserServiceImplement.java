package com.example.elite.services_implement;

import com.example.elite.dto.LoginDTO;
import com.example.elite.dto.LoginResponseDTO;
import com.example.elite.entities.Role;
import com.example.elite.entities.User;
import com.example.elite.jwt.JwtConfig;
import com.example.elite.repository.RoleRepository;
import com.example.elite.repository.UserRepository;
import com.example.elite.services.UserService;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
@AllArgsConstructor
@Service
public class UserServiceImplement implements UserService {
    private UserRepository userRepository;
    private SecretKey secretKey;
    private JwtConfig jwtConfig;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private RoleRepository roleRepository;

    @Override
    public User findByUserName(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public LoginResponseDTO login(LoginDTO user) throws BadCredentialsException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        LoginResponseDTO loginResponseDTO =null;
            Authentication authenticate = authenticationManager.authenticate(authentication);
            if(authenticate.isAuthenticated()){
                User userAuthenticated = userRepository.findUserByUsername(user.getUsername());
                String token = Jwts.builder().setSubject(authenticate.getName())
                        .claim("authorities",authenticate.getAuthorities())
                        .claim("userId",userAuthenticated.getId())
                        .setIssuedAt((new Date())).setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays()))).signWith(secretKey).compact();
                loginResponseDTO = LoginResponseDTO.builder()
                        .userId(userAuthenticated.getId())
                        .fullName(userAuthenticated.getFullName())
                        .username(userAuthenticated.getUsername())
                        .email(userAuthenticated.getEmail())
                        .roleName(userAuthenticated.getRole().getRoleName())
                        .token(jwtConfig.getTokenPrefix()+token).build();
            }
        return loginResponseDTO;
    }
    @Override
    public LoginResponseDTO register(User user) throws Exception {
        LoginResponseDTO loginResponseDTO =null;
        User checkUser = userRepository.findUserByUsername(user.getUsername());
        Role role =roleRepository.findByRoleName("USER");
        if(role==null) return null;
        if(checkUser==null){
            User userTmp = User.builder().username(user.getUsername()).email(user.getEmail()).fullName(user.getFullName()).role(role)
                            .password(passwordEncoder.encode(user.getPassword())).status(true).createDate(LocalDate.now()).build();
           try {
               userRepository.save(userTmp);
           }
           catch (Exception e){
               throw new Exception();
           }
            LoginDTO loginDTO = new LoginDTO(user.getUsername(),user.getPassword());
            loginResponseDTO = login(loginDTO);
        }
        return loginResponseDTO;
    }
    @Override
    public boolean deleteUserById(int userId) {
        User user = userRepository.getById(userId);
        if(user==null){
            throw new UsernameNotFoundException("USER_NOT_FOUND");
        }
        user.setStatus(false);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean addUser(User user) {
        User userTmp = userRepository.findUserByUsername(user.getUsername());
        if(userTmp==null){
            user.setStatus(true);
            user.setCreateDate(LocalDate.now());
            user.setRole(Role.builder().id(1).roleName("USER").build());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(User user, int id) {
        User userTmp = userRepository.getById(id);
        if(userTmp==null){
            throw new UsernameNotFoundException("USER_NOT_FOUND");
        }
        userTmp.setFullName(user.getFullName());
        userTmp.setEmail(user.getEmail());
        userRepository.save(userTmp);
        return true;
    }
}
