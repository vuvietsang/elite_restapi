package com.example.elite.services.implement;

import com.example.elite.dto.AddUserDto;
import com.example.elite.dto.LoginDto;
import com.example.elite.dto.LoginResponseDto;
import com.example.elite.dto.UserDto;
import com.example.elite.entities.Role;
import com.example.elite.entities.User;
import com.example.elite.exceptions.UserDisableException;
import com.example.elite.exceptions.UserNameExistException;
import com.example.elite.jwt.JwtConfig;
import com.example.elite.repository.RoleRepository;
import com.example.elite.repository.UserRepository;
import com.example.elite.services.UserService;
import com.example.elite.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.management.relation.RoleNotFoundException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecretKey secretKey;
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto findByUserName(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) throw new UsernameNotFoundException("USER NAME NOT FOUND!");
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public LoginResponseDto login(LoginDto user) throws AuthenticationException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        LoginResponseDto loginResponseDTO = null;
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (authenticate.isAuthenticated()) {
            User userAuthenticated = userRepository.findUserByUsername(user.getUsername());
            if (!userAuthenticated.isStatus()) {
                throw new UserDisableException("THIS USER IS NOT AVAILABLE AT THIS TIME");
            }
            String token = Utils.buildJWT(authenticate, userAuthenticated, secretKey, jwtConfig);
            loginResponseDTO = LoginResponseDto.builder()
                    .userId(userAuthenticated.getId())
                    .fullName(userAuthenticated.getFullName())
                    .username(userAuthenticated.getUsername())
                    .email(userAuthenticated.getEmail())
                    .avatar(userAuthenticated.getAvatar())
                    .roleName(userAuthenticated.getRole().getRoleName())
                    .token(jwtConfig.getTokenPrefix() + token).build();
        }
        return loginResponseDTO;
    }

    @Override
    public LoginResponseDto register(User user) throws UsernameNotFoundException, RoleNotFoundException {
        LoginResponseDto loginResponseDTO = null;
        User checkUser = userRepository.findUserByUsername(user.getUsername());
        if (checkUser != null) {
            throw new UserNameExistException("THIS USERNAME ALREADY EXISTED!");
        }
        Role role = roleRepository.findByRoleName("USER");
        if (role == null) {
            throw new RoleNotFoundException("ROLE NOT FOUND!");
        }
        User userTmp = User.builder().username(user.getUsername()).email(user.getEmail()).fullName(user.getFullName()).role(role).avatar(user.getAvatar()).phone(user.getPhone())
                .password(passwordEncoder.encode(user.getPassword())).status(true).createDate(LocalDate.now()).build();
        userRepository.save(userTmp);
        LoginDto loginDTO = new LoginDto(user.getUsername(), user.getPassword());
        loginResponseDTO = login(loginDTO);
        return loginResponseDTO;
    }

    @Override
    public UserDto deleteUserById(int userId) throws NoSuchElementException {
        Optional<User> user = userRepository.findById(userId);
        user.get().setStatus(false);
        userRepository.save(user.get());
        UserDto userDTO = modelMapper.map(user.get(), UserDto.class);
        return userDTO;
    }

    @Override
    public UserDto addUser(AddUserDto user) {
        User userTmp = userRepository.findUserByUsername(user.getUsername());
        if (userTmp != null) {
            throw new UserNameExistException("THIS USERNAME ALREADY EXISTED!");
        }
        Role role = roleRepository.findByRoleName(user.getRoleName());
        if (role == null) {
            throw new com.example.elite.exceptions.RoleNotFoundException("THIS ROLE DOES NOT EXISTED");
        }
        User userBuild = User.builder()
                .status(true)
                .createDate(LocalDate.now())
                .role(role)
                .phone(user.getPhone())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        User inRepo = userRepository.save(userBuild);
        UserDto userDTO = modelMapper.map(inRepo, UserDto.class);
        return userDTO;
    }

    @Override
    public UserDto updateUser(User user, int id) throws NoSuchElementException {
        Optional<User> userTmp = userRepository.findById(id);
        userTmp.get().setFullName(user.getFullName());
        userTmp.get().setEmail(user.getEmail());
        userRepository.save(userTmp.get());
        UserDto userDTO = modelMapper.map(userTmp.get(), UserDto.class);
        return userDTO;
    }

    @Override
    public Page<UserDto> getAllUser(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<User> pageUser = userRepository.findAll(pageable);
        Page<UserDto> pageUserDTO = pageUser.map(user -> modelMapper.map(user, UserDto.class));
        return pageUserDTO;
    }

    @Override
    public UserDto getUserById(int userId) throws NoSuchElementException {
        User user = userRepository.findById(userId).get();
        UserDto userDto = modelMapper.map(user,UserDto.class);
        return userDto;
    }
}
