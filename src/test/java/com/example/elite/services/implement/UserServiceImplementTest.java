package com.example.elite.services.implement;

import com.example.elite.dto.AddUserDto;
import com.example.elite.dto.UserDto;
import com.example.elite.entities.Role;
import com.example.elite.entities.User;
import com.example.elite.jwt.JwtConfig;
import com.example.elite.repository.RoleRepository;
import com.example.elite.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.util.Optional;

import static org.mockito.Mockito.when;

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
        UserDto userDTO = modelMapper.map(user, UserDto.class);
        Assertions.assertEquals(userService.findByUserName("sangvv"), userDTO);
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
        UserDto userDTO = Mockito.mock(UserDto.class);
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDTO);
        Assertions.assertEquals(userService.deleteUserById(user.getId()), userDTO);
        Assertions.assertFalse(userService.deleteUserById(user.getId()).isStatus());
    }


    @Test
    void addUser_WithValidData_shouldReturnUserDTO() {
        AddUserDto user = Mockito.mock(AddUserDto.class);
        Role role = Mockito.mock(Role.class);
        User userBuild = User.builder()
                .role(role)
                .username(user.getUsername())
                .build();
        UserDto userDTO = Mockito.mock(UserDto.class);
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(null);
        when(roleRepository.findByRoleName(role.getRoleName())).thenReturn(role);
        when(userRepository.save(userBuild)).thenReturn(userBuild);
        when(modelMapper.map(userBuild, UserDto.class)).thenReturn(userDTO);
        Assertions.assertEquals(userService.addUser(user), userDTO);
    }

    @Test
    void updateUser_WithValidData_shouldReturnUserDTO() {
        User user = Mockito.mock(User.class);
        UserDto userDTO = Mockito.mock(UserDto.class);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDTO);
        Assertions.assertEquals(userService.updateUser(user, user.getId()), userDTO);
        Assertions.assertEquals(userService.updateUser(user, user.getId()).getEmail(), userDTO.getEmail());
        Assertions.assertEquals(userService.updateUser(user, user.getId()).getFullName(), userDTO.getFullName());
    }

    @Test
    void getAllUser_WithValidData_shouldReturnUserDTOPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = Mockito.mock(Page.class);
        Page<UserDto> userDTOPage = Mockito.mock(Page.class);
        Mockito.when(userRepository.findAll(pageable)).thenReturn(userPage);

        Mockito.when(userPage.map(user -> modelMapper.map(user, UserDto.class))).thenReturn(userDTOPage);
        var test = userService.getAllUser(0, 10);
        Assertions.assertEquals(userService.getAllUser(pageable.getPageNumber(), pageable.getPageSize()), userDTOPage);
    }
}