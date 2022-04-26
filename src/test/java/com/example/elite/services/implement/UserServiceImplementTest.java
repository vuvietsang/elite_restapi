package com.example.elite.services.implement;

import com.example.elite.dto.AddUserDTO;
import com.example.elite.dto.UserDTO;
import com.example.elite.entities.Role;
import com.example.elite.entities.User;
import com.example.elite.jwt.JwtConfig;
import com.example.elite.repository.RoleRepository;
import com.example.elite.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplementTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private SecretKey secretKey;
    @Mock
    private JwtConfig jwtConfig;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    UserServiceImplement userService;

    @Mock
    private ModelMapper modelMapper;


    @Test
    void findByUserName_WithValidData_shouldReturnUserDTO() {
        User user = new User();
        user.setUsername("sangvv");
        Mockito.when(userRepository.findUserByUsername("sangvv")).thenReturn(user);
        UserDTO userDTO = modelMapper.map(user,UserDTO.class);
        Assertions.assertEquals(userService.findByUserName("sangvv"),userDTO);
    }

    @Test
    void login_WithValidData_shouldReturnUserDTO() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("sang", "123");
        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
    }

    @Test
    void register() {

    }

    @Test
    void deleteUserById_WithValidData_shouldReturnUserDTO() {
        User user = Mockito.mock(User.class);
        Optional<User> userOptional = Optional.of(user);
        when(userRepository.findById(user.getId())).thenReturn(userOptional);
        when(userRepository.save(user)).thenReturn(user);
        UserDTO userDTO = Mockito.mock(UserDTO.class);
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user,UserDTO.class)).thenReturn(userDTO);
        Assertions.assertEquals(userService.deleteUserById(user.getId()),userDTO);
        Assertions.assertFalse(userService.deleteUserById(user.getId()).isStatus());
    }



    @Test
    void addUser_WithValidData_shouldReturnUserDTO() {
        AddUserDTO user = Mockito.mock(AddUserDTO.class);
        Role role = Mockito.mock(Role.class);
        User userBuild =  User.builder()
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
        UserDTO userDTO = Mockito.mock(UserDTO.class);
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(null);
        when(roleRepository.findByRoleName(role.getRoleName())).thenReturn(role);

        when(userRepository.save(userBuild)).thenReturn(userBuild);
        when(modelMapper.map(userBuild,UserDTO.class)).thenReturn(userDTO);

        Assertions.assertEquals(userService.addUser(user),userDTO);
    }

    @Test
    void updateUser_WithValidData_shouldReturnUserDTO() {
        User user = Mockito.mock(User.class);
        UserDTO userDTO = Mockito.mock(UserDTO.class);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user,UserDTO.class)).thenReturn(userDTO);
        Assertions.assertEquals(userService.updateUser(user,user.getId()), userDTO);
        Assertions.assertEquals(userService.updateUser(user,user.getId()).getEmail(), userDTO.getEmail());
        Assertions.assertEquals(userService.updateUser(user,user.getId()).getFullName(), userDTO.getFullName());
    }

    @Test
    void getAllUser_WithValidData_shouldReturnUserDTOPage() {
        Pageable pageable = PageRequest.of(0,10);
        Page<User> userPage = Mockito.mock(Page.class);
        Page<UserDTO> userDTOPage = Mockito.mock(Page.class);
        Mockito.when(userRepository.findAll(pageable)).thenReturn(userPage);
        Mockito.when(userPage.map(user-> modelMapper.map(user,UserDTO.class))).thenReturn(userDTOPage);
        Assertions.assertEquals(userService.getAllUser(pageable.getPageNumber(),pageable.getPageSize()),userDTOPage);
    }
}