package com.example.elite.services;


import com.example.elite.dto.OrderDTO;
import com.example.elite.dto.OrderDetailDTO;
import com.example.elite.entities.Orders;
import org.springframework.data.domain.Page;



public interface OrderSevice {
    OrderDTO getOrderById(int orderId);

    OrderDTO deleteOrder(int orderId);

    OrderDTO updateOrder(Orders order, int orderId);

    OrderDTO checkout(OrderDetailDTO[] orderDetails);

    OrderDTO confirmOrder(int orderId);

    Page<OrderDTO> getAllOrders(int pageNum, int pageSize);

    Page<OrderDTO> getOrderByUserId(int pageNum, int pageSize, int userId);

    Page<OrderDTO> getOrdersByEmail(int pageNum, int pageSize, String email);
}
