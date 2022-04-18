package com.example.elite.controller.user;

import com.example.elite.dto.OrderDetailDTO;
import com.example.elite.dto.ResponseDTO;
import com.example.elite.services.OrderSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("orders")
@RequiredArgsConstructor
public class UserOrderController {
    private final OrderSevice service;

    @PostMapping("/checkout")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseDTO> checkout(@Validated @RequestBody OrderDetailDTO[] orderDetails) {
        ResponseDTO response = new ResponseDTO();
        try {
            response.setData(this.service.checkout(orderDetails));
            response.setSuccessCode("ORDER SUCCESSFULLY");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setErrorCode("ORDER FAILED");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/myorder/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseDTO> getOrderByUserId(@PathVariable("id") int userId,@RequestParam int pageNum, @RequestParam int pageSize){
        ResponseDTO response = new ResponseDTO();
        try {
            response.setData(service.getOrderByUserId(pageNum,pageSize,userId));
            response.setSuccessCode("GET ORDERS SUCCESSFULLY");
            return ResponseEntity.ok().body(response);
        }
        catch (Exception e){
            e.printStackTrace();
            response.setErrorCode("GET ORDERS FAILED");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
