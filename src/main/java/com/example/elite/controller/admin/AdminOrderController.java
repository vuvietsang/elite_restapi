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
            response.setData(this.service.deleteOrder(orderId));
            response.setSuccessMessage("DELETE SUCCESSFULLY");
            return ResponseEntity.ok().body(response);
    }

    @GetMapping("/orders")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ResponseDTO> getAllOrders(@RequestParam int pageNumber, @RequestParam int pageSize) {
        ResponseDTO response = new ResponseDTO();
        try {
            response.setData(this.service.getAllOrders(pageNumber, pageSize));
            response.setSuccessMessage("GET ALL ORDERS SUCCESSFULLY");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setErrorMessage("GET ALL ORDERS FAILED");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/confirm/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseDTO> confirmOrder(@PathVariable("id") int orderId) {
        ResponseDTO response = new ResponseDTO();
            response.setData(this.service.confirmOrder(orderId));
            response.setSuccessMessage("CONFIRM ORDER SUCCESSFULLY");
            return ResponseEntity.ok().body(response);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ResponseDTO> getOrderById(@PathVariable("id") int orderId){
        ResponseDTO response = new ResponseDTO();
            response.setData(service.getOrderById(orderId));
        response.setSuccessMessage("GET ORDER SUCCESSFULLY");
            return ResponseEntity.ok().body(response);
    }
    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getOrderByEmail(@PathVariable("email") String email,@RequestParam int pageNumber,@RequestParam int pageSize){
        ResponseDTO response = new ResponseDTO();
            response.setData(service.getOrdersByEmail(pageNumber, pageSize, email));
            response.setSuccessMessage("GET ORDERS SUCCESSFULLY");
            return ResponseEntity.ok().body(response);
    }

}
