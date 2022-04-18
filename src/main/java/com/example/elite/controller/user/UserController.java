package com.example.elite.controller.user;

import com.example.elite.dto.LoginDTO;
import com.example.elite.dto.LoginResponseDTO;
import com.example.elite.dto.ResponseDTO;
import com.example.elite.entities.User;
import com.example.elite.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("user")
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class UserController {

    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@Validated @RequestBody LoginDTO user){
        ResponseDTO responseDTO = new ResponseDTO();
        LoginResponseDTO loginResponseDTO=null;
        try {
             loginResponseDTO = userService.login(user);
        }catch (Exception ex){
            responseDTO.setErrorCode("WRONG USER NAME OR PASSWORD!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
        if(loginResponseDTO==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
        else{
        responseDTO.setData(loginResponseDTO);
        responseDTO.setSuccessCode("LOGIN_SUCCESS");
        return ResponseEntity.ok().body(responseDTO);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Validated @RequestBody User user){
        ResponseDTO responseDTO = new ResponseDTO();
        LoginResponseDTO loginResponseDTO = null;
        try {
            loginResponseDTO = userService.register(user);
        } catch (Exception e) {
            responseDTO.setErrorCode(e.getMessage());
        }
        if(loginResponseDTO==null){
            responseDTO.setErrorCode("This username already existed!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
        else{
            responseDTO.setData(loginResponseDTO);
            responseDTO.setSuccessCode("REGISTER_SUCCESS");
            return ResponseEntity.ok().body(responseDTO);
        }
    }
}
