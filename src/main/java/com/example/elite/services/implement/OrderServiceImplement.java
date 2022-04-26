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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public OrderDTO getOrderById(int orderId) throws NoSuchElementException {
        Optional<Orders> orders = orderRepository.findById(orderId);
        OrderDTO orderDTO = modelMapper.map(orders.get(), OrderDTO.class);
        return orderDTO;
    }

    @Override
    public OrderDTO deleteOrder(int orderId) throws NoSuchElementException {
        Optional<Orders> orders = orderRepository.findById(orderId);
        orders.get().setStatus(false);
        OrderDTO orderDTO = modelMapper.map(orderRepository.save(orders.get()), OrderDTO.class);
        return orderDTO;
    }

    @Override
    public OrderDTO updateOrder(Orders order, int orderId) {
        return null;
    }

    @Override
    public OrderDTO checkout(OrderDetailDTO[] orderDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        User userInDb = this.userRepository.findUserByUsername(userName);
        double totalPrice = 0;
        Orders order = Orders.builder().createDate(LocalDate.now()).status(false).user(userInDb).build();
        orderRepository.save(order);
        for (int i = 0; i < orderDetails.length; i++) {
            Product product = this.productRepository
                    .findById(orderDetails[i].getProductId()).get();
            int quantity = orderDetails[i].getQuantity();
                double price = quantity * product.getPrice();
                totalPrice+=price;
                OrderDetail oDetails = OrderDetail.builder().orders(order).product(product).quantity(quantity)
                        .price(price).build();
                this.orderDetailsRepository.save(oDetails);
        }
        order.setTotalPrice(totalPrice);
        return modelMapper.map(orderRepository.save(order), OrderDTO.class);
    }

    @Override
    public OrderDTO confirmOrder(int orderId) throws NoSuchElementException {
        Optional<Orders> orders = orderRepository.findById(orderId);
        orders.get().setConfirmed(true);
        orderRepository.save(orders.get());
        return modelMapper.map(orderRepository.save(orders.get()), OrderDTO.class);
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
