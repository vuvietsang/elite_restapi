package com.example.elite.controller.user;

import com.example.elite.dto.OrderDetailDto;
import com.example.elite.dto.ResponseDto;
import com.example.elite.services.OrderSevice;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ResponseDto> checkout(@Validated @RequestBody OrderDetailDto[] orderDetails) {
        ResponseDto response = new ResponseDto();
            response.setData(this.service.checkout(orderDetails));
            response.setSuccessMessage("ORDER SUCCESSFULLY");
            return ResponseEntity.ok().body(response);
    }

    @GetMapping("/myorder/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseDto> getOrderByUserId(@PathVariable("id") int userId, @RequestParam int pageNumber, @RequestParam int pageSize){
            ResponseDto response = new ResponseDto();
            response.setData(service.getOrderByUserId(pageNumber,pageSize,userId));
            response.setSuccessMessage("GET ORDERS SUCCESSFULLY");
            return ResponseEntity.ok().body(response);
    }
}
