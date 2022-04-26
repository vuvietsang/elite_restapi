package com.example.elite.services;


import com.example.elite.dto.OrderDto;
import com.example.elite.dto.OrderDetailDto;
import com.example.elite.entities.Orders;
import org.springframework.data.domain.Page;



public interface OrderSevice {
    OrderDto getOrderById(int orderId);

    OrderDto deleteOrder(int orderId);

    OrderDto updateOrder(Orders order, int orderId);

    OrderDto checkout(OrderDetailDto[] orderDetails);

    OrderDto confirmOrder(int orderId);

    Page<OrderDto> getAllOrders(int pageNum, int pageSize);

    Page<OrderDto> getOrderByUserId(int pageNum, int pageSize, int userId);

    Page<OrderDto> getOrdersByEmail(int pageNum, int pageSize, String email);
}
