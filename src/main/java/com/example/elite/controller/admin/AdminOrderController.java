package com.example.elite.controller.admin;

import com.example.elite.dto.ResponseDTO;
import com.example.elite.services.OrderSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("orders")
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderSevice service;

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteOrder(@PathVariable("id") int orderId) {
        ResponseDTO response = new ResponseDTO();
        try {
            response.setData(this.service.deleteOrder(orderId));
            response.setSuccessCode("DELETE SUCCESSFULLY");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setErrorCode("DELETE FAILED");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/orders")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ResponseDTO> getAllOrders(@RequestParam int pageNum, @RequestParam int pageSize) {
        ResponseDTO response = new ResponseDTO();
        try {
            response.setData(this.service.getAllOrders(pageNum, pageSize));
            response.setSuccessCode("GET ALL ORDERS SUCCESSFULLY");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setErrorCode("GET ALL ORDERS FAILED");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/confirm/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseDTO> confirmOrder(@PathVariable("id") int orderId) {
        ResponseDTO response = new ResponseDTO();
        try {
            response.setData(this.service.confirmOrder(orderId));
            response.setSuccessCode("CONFIRM ORDER SUCCESSFULLY");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setErrorCode("CONFIRM ORDER FAILED");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ResponseDTO> getOrderById(@PathVariable("id") int orderId){
        ResponseDTO response = new ResponseDTO();
        try {
            response.setData(service.getOrder(orderId));
            response.setSuccessCode("GET ORDER SUCCESSFULLY");
            return ResponseEntity.ok().body(response);
        }
        catch (Exception e){
            e.printStackTrace();
            response.setErrorCode("GET ORDER FAILED!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getOrderByEmail(@PathVariable("email") String email,@RequestParam int pageNum,@RequestParam int pageSize){
        ResponseDTO response = new ResponseDTO();
        try {
            response.setData(service.getOrdersByEmail(pageNum, pageSize, email));
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
