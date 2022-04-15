package com.example.elite.services;


import com.example.elite.dto.OrderDTO;
import com.example.elite.dto.OrderDetailDTO;
import com.example.elite.entities.Orders;
import org.springframework.data.domain.Page;



public interface OrderSevice {
    OrderDTO getOrder(int orderId);

    boolean deleteOrder(int orderId);

    boolean updateOrder(Orders order, int orderId);

    boolean checkout(OrderDetailDTO[] orderDetails);

    boolean confirmOrder(int orderId);

    Page<OrderDTO> getAllOrders(int pageNum, int pageSize);

    Page<OrderDTO> getOrderByUserId(int pageNum, int pageSize, int userId);

    Page<OrderDTO> getOrdersByEmail(int pageNum, int pageSize, String email);
}
