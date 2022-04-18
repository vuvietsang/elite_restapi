package com.example.elite.services.implement;

import com.example.elite.dto.OrderDTO;
import com.example.elite.dto.OrderDetailDTO;
import com.example.elite.entities.OrderDetail;
import com.example.elite.entities.Orders;
import com.example.elite.entities.Product;
import com.example.elite.entities.User;
import com.example.elite.repository.OrderDetailsRepository;
import com.example.elite.repository.OrderRepository;
import com.example.elite.repository.ProductRepository;
import com.example.elite.repository.UserRepository;
import com.example.elite.services.OrderSevice;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OrderServiceImplement implements OrderSevice {
    OrderRepository orderRepository;
    UserRepository userRepository;
    ProductRepository productRepository;
    OrderDetailsRepository orderDetailsRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public OrderDTO getOrder(int orderId) {
        Orders orders = orderRepository.getById(orderId);
        OrderDTO orderDTO = modelMapper.map(orders,OrderDTO.class);
        return orderDTO;
    }

    @Override
    public boolean deleteOrder(int orderId) {
        Orders orders = orderRepository.getById(orderId);
        if(orders!=null){
            orders.setStatus(false);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean updateOrder(Orders order, int orderId) {
        return false;
    }

    @Override
    public boolean checkout(OrderDetailDTO[] orderDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        User userInDb = this.userRepository.findUserByUsername(userName);
        double totalPrice = 0;
        Orders order = Orders.builder().createDate(LocalDate.now()).status(false).user(userInDb).build();
        orderRepository.save(order);
        for (int i = 0; i < orderDetails.length; i++) {
            Product product = this.productRepository
                        .findById(Long.parseLong(orderDetails[i].getProductId())).get();
                int quantity = orderDetails[i].getQuantity();
                double price = quantity * product.getPrice();
                totalPrice+=price;
                OrderDetail oDetails = OrderDetail.builder().orders(order).product(product).quantity(quantity)
                        .price(price).build();
                this.orderDetailsRepository.save(oDetails);
        }
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean confirmOrder(int orderId) {
        Orders orders = orderRepository.getById(orderId);
        if(orders!=null){
            orders.setStatus(true);
            orderRepository.save(orders);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public Page<OrderDTO> getAllOrders(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Orders> pageOrders = orderRepository.findAll(pageable);
        Page<OrderDTO> orderDTOPage = pageOrders.map(order -> modelMapper.map(order,OrderDTO.class));
        return orderDTOPage;
    }

    @Override
    public Page<OrderDTO> getOrderByUserId(int pageNum, int pageSize, int userId) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Orders> pageOrders = orderRepository.getOrdersByUserId(pageable, userId);
        Page<OrderDTO> orderDTOPage = pageOrders.map(order -> modelMapper.map(order,OrderDTO.class));
        return orderDTOPage;
    }
    @Override
    public Page<OrderDTO> getOrdersByEmail(int pageNum, int pageSize, String email) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Orders> pageOrders = orderRepository.getOrdersByUserEmail(pageable, email);
        Page<OrderDTO> orderDTOPage = pageOrders.map(order -> modelMapper.map(order,OrderDTO.class));
        return orderDTOPage;
    }
}
