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

    private ModelMapper modelMapper;


    @BeforeEach
    void setUp(){
        modelMapper = new ModelMapper();
    }
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
        Optional<User> user = Optional.of(User.builder().id(1).status(true).build());
        Mockito.when(userRepository.findById(1)).thenReturn(user);
        UserDTO userDTO = modelMapper.map(user.get(),UserDTO.class);
        userDTO.setStatus(false);
        Assertions.assertEquals(userService.deleteUserById(1),userDTO);
    }

    @Test
    void addUser_WithValidData_shouldReturnUserDTO() {
        AddUserDTO addUserDTO = AddUserDTO.builder().roleName("ADMIN").username("sangvv").email("vuvietsang10a9@gmail.com").build();
        Role role = Role.builder().roleName("ADMIN").id(1).build();
        Mockito.when(userRepository.findUserByUsername("sangvv")).thenReturn(null);
        Mockito.when(roleRepository.findByRoleName(role.getRoleName())).thenReturn(role);
        User user = modelMapper.map(addUserDTO,User.class);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        UserDTO userDTO = modelMapper.map(user,UserDTO.class);
        Assertions.assertEquals(userService.addUser(addUserDTO),userDTO);
    }

    @Test
    void updateUser_WithValidData_shouldReturnUserDTO() {
        Optional<User> user = Optional.of(User.builder().id(1).status(true).build());
        user.get().setFullName("HIHI");
        user.get().setEmail("vuvuvuvu@gmail.com");
        Mockito.when(userRepository.findById(1)).thenReturn(user);
        UserDTO userDTO = modelMapper.map(user.get(),UserDTO.class);
        Assertions.assertEquals(userService.updateUser(user.get(),1), userDTO);
    }

    @Test
    void getAllUser_WithValidData_shouldReturnUserDTOPage() {
        Pageable pageable = PageRequest.of(0,10);
        Optional<User> user1 = Optional.of(User.builder().id(1).status(true).build());
        Optional<User> user2 = Optional.of(User.builder().id(2).status(true).build());
        Page<User> page = new PageImpl(List.of(user1.get(),user2.get()),pageable,2);
        Mockito.when(userRepository.findAll(pageable)).thenReturn(page);
        Page<UserDTO> userDTOPage = page.map(user->modelMapper.map(user,UserDTO.class));
        Assertions.assertEquals(userService.getAllUser(pageable.getPageNumber(),pageable.getPageSize()),userDTOPage);
    }
}