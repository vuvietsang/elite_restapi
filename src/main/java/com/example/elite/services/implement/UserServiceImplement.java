package com.example.elite.services.implement;

import com.example.elite.dto.LoginDTO;
import com.example.elite.dto.LoginResponseDTO;
import com.example.elite.dto.UserDTO;
import com.example.elite.entities.Role;
import com.example.elite.entities.User;
import com.example.elite.handle_exception.UserDisableException;
import com.example.elite.handle_exception.UserNameExistException;
import com.example.elite.handle_exception.UserNotFoundException;
import com.example.elite.handle_exception.UsernameOrPasswordNotFoundException;
import com.example.elite.jwt.JwtConfig;
import com.example.elite.repository.RoleRepository;
import com.example.elite.repository.UserRepository;
import com.example.elite.services.UserService;
import com.example.elite.utils.Utils;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.management.relation.RoleNotFoundException;
import java.time.LocalDate;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
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
    public LoginResponseDTO login(LoginDTO user) throws AuthenticationException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        LoginResponseDTO loginResponseDTO =null;
        Authentication authenticate = authenticationManager.authenticate(authentication);
            if(authenticate.isAuthenticated()){
                User userAuthenticated = userRepository.findUserByUsername(user.getUsername());
                if(!userAuthenticated.isStatus()){
                    throw new UserDisableException("THIS USER IS NOT AVAILABLE AT THIS TIME");
                }
                String token = Utils.buildJWT(authenticate,userAuthenticated,secretKey,jwtConfig);
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
    public LoginResponseDTO register(User user) throws UsernameNotFoundException, RoleNotFoundException {
        LoginResponseDTO loginResponseDTO =null;
        User checkUser = userRepository.findUserByUsername(user.getUsername());
        Role role =roleRepository.findByRoleName("USER");
        if(role==null){
            throw new RoleNotFoundException("ROLE NOT FOUND!");
        }
        if(checkUser!=null){
            throw new UserNameExistException("THIS USERNAME ALREADY EXISTED!");
        }
        if(checkUser==null){
            User userTmp = User.builder().username(user.getUsername()).email(user.getEmail()).fullName(user.getFullName()).role(role).phone(user.getPhone())
                            .password(passwordEncoder.encode(user.getPassword())).status(true).createDate(LocalDate.now()).build();
            userRepository.save(userTmp);
            LoginDTO loginDTO = new LoginDTO(user.getUsername(),user.getPassword());
            loginResponseDTO = login(loginDTO);
        }
        return loginResponseDTO;
    }
    @Override
    public boolean deleteUserById(int userId) throws NoSuchElementException {
        Optional<User> user;
            user = userRepository.findById(userId);
        user.get().setStatus(false);
        userRepository.save(user.get());
        return true;
    }
    @Override
    public boolean addUser(User user) {
        User userTmp = userRepository.findUserByUsername(user.getUsername());
        if(userTmp!=null){
            throw new UserNameExistException("THIS USERNAME ALREADY EXISTED!");
        }
        user.setStatus(true);
        user.setCreateDate(LocalDate.now());
        user.setRole(Role.builder().id(1).roleName("USER").build());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean updateUser(User user, int id) throws NoSuchElementException {
        Optional<User> userTmp = userRepository.findById(id);
        userTmp.get().setFullName(user.getFullName());
        userTmp.get().setEmail(user.getEmail());
        userRepository.save(userTmp.get());
        return true;
    }

    @Override
    public Page<UserDTO> getAllUser(int pageNum, int pageSize) {
        ModelMapper modelMapper = new ModelMapper();
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<User> pageUser = userRepository.findAll(pageable);
        Page<UserDTO> pageUserDTO = pageUser.map(user->modelMapper.map(user,UserDTO.class));
        return pageUserDTO;
    }
}
