package com.example.elite.services.implement;

import com.example.elite.dto.OrderDto;
import com.example.elite.dto.OrderDetailDto;
import com.example.elite.entities.OrderDetail;
import com.example.elite.entities.Orders;
import com.example.elite.entities.Product;
import com.example.elite.entities.User;
import com.example.elite.repository.OrderDetailsRepository;
import com.example.elite.repository.OrderRepository;
import com.example.elite.repository.ProductRepository;
import com.example.elite.repository.UserRepository;
import com.example.elite.services.OrderSevice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderServiceImplement implements OrderSevice {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDto getOrderById(int orderId) throws NoSuchElementException {
        Optional<Orders> orders = orderRepository.findById(orderId);
        OrderDto orderDTO = modelMapper.map(orders.get(), OrderDto.class);
        return orderDTO;
    }

    @Override
    public OrderDto deleteOrder(int orderId) throws NoSuchElementException {
        Optional<Orders> orders = orderRepository.findById(orderId);
        orders.get().setStatus(false);
        OrderDto orderDTO = modelMapper.map(orderRepository.save(orders.get()), OrderDto.class);
        return orderDTO;
    }

    @Override
    public OrderDto updateOrder(Orders order, int orderId) {
        return null;
    }

    @Override
    public OrderDto checkout(OrderDetailDto[] orderDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        User userInDb = this.userRepository.findUserByUsername(userName);
        double totalPrice = 0;
        Orders order = Orders.builder().createDate(LocalDate.now()).status(false).user(userInDb).build();
        orderRepository.save(order);
        List<OrderDetail> orderDetailList  = new ArrayList<>();
        for (int i = 0; i < orderDetails.length; i++) {
            Product product = this.productRepository
                    .findById(orderDetails[i].getProductId()).get();
            int quantity = orderDetails[i].getQuantity();
                double price = quantity * product.getPrice();
                totalPrice+=price;
                OrderDetail oDetails = OrderDetail.builder().orders(order).product(product).quantity(quantity)
                        .price(price).build();
                orderDetailList.add(oDetails);
        }
        this.orderDetailsRepository.saveAll(orderDetailList);
        order.setTotalPrice(totalPrice);
        return modelMapper.map(orderRepository.save(order), OrderDto.class);
    }

    @Override
    public OrderDto confirmOrder(int orderId) throws NoSuchElementException {
        Optional<Orders> orders = orderRepository.findById(orderId);
        orders.get().setConfirmed(true);
        orderRepository.save(orders.get());
        return modelMapper.map(orderRepository.save(orders.get()), OrderDto.class);
    }

    @Override
    public Page<OrderDto> getAllOrders(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Orders> pageOrders = orderRepository.findAll(pageable);
        Page<OrderDto> orderDTOPage = pageOrders.map(order -> modelMapper.map(order, OrderDto.class));
        return orderDTOPage;
    }

    @Override
    public Page<OrderDto> getOrderByUserId(int pageNum, int pageSize, int userId) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Orders> pageOrders = orderRepository.getOrdersByUserId(pageable, userId);
        Page<OrderDto> orderDTOPage = pageOrders.map(order -> modelMapper.map(order, OrderDto.class));
        return orderDTOPage;
    }
    @Override
    public Page<OrderDto> getOrdersByEmail(int pageNum, int pageSize, String email) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Orders> pageOrders = orderRepository.getOrdersByUserEmail(pageable, email);
        Page<OrderDto> orderDTOPage = pageOrders.map(order -> modelMapper.map(order, OrderDto.class));
        return orderDTOPage;
    }
}
