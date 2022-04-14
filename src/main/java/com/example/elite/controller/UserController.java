package com.example.elite.controller;

import com.example.elite.dto.LoginDTO;
import com.example.elite.dto.LoginResponseDTO;
import com.example.elite.dto.ResponseDTO;
import com.example.elite.dto.UserDTO;
import com.example.elite.entities.User;
import com.example.elite.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody @Valid LoginDTO user){
        ResponseDTO responseDTO = new ResponseDTO();
        LoginResponseDTO loginResponseDTO=null;
        try {
             loginResponseDTO = userService.login(user);
        }catch (Exception ex){
            responseDTO.setErrorCode(ex.getMessage());
            return ResponseEntity.status(400).body(responseDTO);
        }
        if(loginResponseDTO==null){
            return ResponseEntity.status(400).body(responseDTO);
        }
        else{
        responseDTO.setData(loginResponseDTO);
        responseDTO.setSuccessCode("LOGIN_SUCCESS");
        return ResponseEntity.ok().body(responseDTO);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody @Valid User user){
        ResponseDTO responseDTO = new ResponseDTO();
        LoginResponseDTO loginResponseDTO = null;
        try {
            loginResponseDTO = userService.register(user);
        } catch (Exception e) {
            responseDTO.setErrorCode(e.getMessage());
        }
        if(loginResponseDTO==null){
            responseDTO.setErrorCode("REGISTER_FAIL");
            return ResponseEntity.status(400).body(responseDTO);
        }
        else{
            responseDTO.setData(loginResponseDTO);
            responseDTO.setSuccessCode("REGISTER_SUCCESS");
            return ResponseEntity.ok().body(responseDTO);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable(name="id") String userId){
        ResponseDTO responseDTO = new ResponseDTO();
        boolean check = userService.deleteUserById(Integer.parseInt(userId));
        if(check){
            responseDTO.setSuccessCode("DELETE SUCCESSFULLY");
            return ResponseEntity.ok().body(responseDTO);
        }
        else  {
            responseDTO.setErrorCode("DELETE FAIL");
            return ResponseEntity.status(400).body(responseDTO);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> add(@RequestBody User user){
        ResponseDTO responseDTO = new ResponseDTO();
        boolean check = userService.addUser(user);
        if(check){
            responseDTO.setSuccessCode("ADD SUCCESSFULLY");
            return ResponseEntity.ok().body(responseDTO);
        }
        else  {
            responseDTO.setErrorCode("ADD FAIL");
            return ResponseEntity.status(400).body(responseDTO);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> update(@RequestBody User user, @PathVariable(name="id") int id){
        ResponseDTO responseDTO = new ResponseDTO();
        boolean check = userService.updateUser(user,id);
        if(check){
            responseDTO.setSuccessCode("UPDATE SUCCESSFULLY");
            return ResponseEntity.ok().body(responseDTO);
        }
        else  {
            responseDTO.setErrorCode("UPDATE FAIL");
            return ResponseEntity.status(400).body(responseDTO);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pageNum={num}&pageSize={size}")
    public ResponseEntity<ResponseDTO> finAll(@PathVariable(name="num") int pageNum, @PathVariable(name="size") int pageSize){
        ResponseDTO responseDTO = new ResponseDTO();

        Page<UserDTO> users = userService.getAllUser(pageNum,pageSize);
        if(users!=null){
            responseDTO.setData(users);
            responseDTO.setSuccessCode("GET ALL SUCCESSFULLY");
            return ResponseEntity.ok().body(responseDTO);
        }
        else  {
            responseDTO.setErrorCode("GET ALL FAIL");
            return ResponseEntity.status(400).body(responseDTO);
        }
    }
}
