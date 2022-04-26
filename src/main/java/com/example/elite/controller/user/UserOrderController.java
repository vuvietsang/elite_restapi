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
            response.setData(this.service.checkout(orderDetails));
            response.setSuccessMessage("ORDER SUCCESSFULLY");
            return ResponseEntity.ok().body(response);
    }

    @GetMapping("/myorder/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseDTO> getOrderByUserId(@PathVariable("id") int userId,@RequestParam int pageNumber, @RequestParam int pageSize){
            ResponseDTO response = new ResponseDTO();
            response.setData(service.getOrderByUserId(pageNumber,pageSize,userId));
            response.setSuccessMessage("GET ORDERS SUCCESSFULLY");
            return ResponseEntity.ok().body(response);
    }
}
