package com.example.elite.services;


import com.example.elite.dto.OrderDetailDTO;
import com.example.elite.entities.Orders;
import org.springframework.data.domain.Page;



public interface OrderSevice {
    Orders getOrder(int orderId);

    boolean deleteOrder(int orderId);

    boolean updateOrder(Orders order, int orderId);

    boolean checkout(OrderDetailDTO[] orderDetails);

    boolean confirmOrder(int orderId);

    Page<Orders> getAllOrders(int pageNum, int pageSize);

    Page<Orders> getOrderByUserId(int pageNum, int pageSize, int userId);

    Page<Orders> getOrdersByEmail(int pageNum, int pageSize, String email);
}
