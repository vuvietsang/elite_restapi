package com.example.elite.services.implement;

import com.example.elite.dto.OrderDTO;
import com.example.elite.entities.Orders;
import com.example.elite.entities.User;
import com.example.elite.repository.OrderDetailsRepository;
import com.example.elite.repository.OrderRepository;
import com.example.elite.repository.ProductRepository;
import com.example.elite.repository.UserRepository;
import com.example.elite.services.OrderSevice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplementTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    OrderDetailsRepository orderDetailsRepository;

    @InjectMocks
    private OrderServiceImplement orderSevice;

    ModelMapper modelMapper;

    @BeforeEach
    void setUp(){
        modelMapper = new ModelMapper();
    }
    @Test
    void getOrder() {
        User user = User.builder().id(1).build();
        Optional<Orders> orders = Optional.of(Orders.builder()
                .createDate(LocalDate.now())
                .totalPrice(5000)
                .id(1)
                .status(true)
                .user(user)
                .build());
        Mockito.when(orderRepository.findById(1)).thenReturn(orders);
        OrderDTO orderDTO = modelMapper.map(orders.get(), OrderDTO.class);
        Assertions.assertEquals(orderSevice.getOrderById(1), orderDTO);
    }

    @Test
    void deleteOrder() {
        User user = User.builder().id(1).build();
        Optional<Orders> orders = Optional.of(Orders.builder()
                .createDate(LocalDate.now())
                .totalPrice(5000)
                .id(1)
                .status(true)
                .user(user)
                .build());
        Mockito.when(orderRepository.findById(1)).thenReturn(orders);
        Mockito.when(orderRepository.save(orders.get())).thenReturn(orders.get());
        OrderDTO orderDTO = modelMapper.map(orders.get(), OrderDTO.class);
        orderDTO.setStatus(false);
        Assertions.assertEquals(orderSevice.deleteOrder(1), orderDTO);
    }

    @Test
    void checkout() {

    }

    @Test
    void confirmOrder() {
        User user = User.builder().id(1).build();
        Optional<Orders> orders = Optional.of(Orders.builder()
                .createDate(LocalDate.now())
                .totalPrice(5000)
                .id(1)
                .status(true)
                .isConfirmed(false)
                .user(user)
                .build());
        Mockito.when(orderRepository.findById(1)).thenReturn(orders);
        Mockito.when(orderRepository.save(orders.get())).thenReturn(orders.get());

        OrderDTO orderDTO = modelMapper.map(orders.get(), OrderDTO.class);
        orderDTO.setConfirmed(true);
        Assertions.assertEquals(orderSevice.confirmOrder(1), orderDTO);
    }

    @Test
    void getAllOrders() {
        Pageable pageable = PageRequest.of(0, 10);
        Optional<Orders> orders1 = Optional.of(Orders.builder()
                .createDate(LocalDate.now())
                .totalPrice(5000)
                .id(1)
                .status(true)
                .isConfirmed(false)
                .build());
        Optional<Orders> orders2 = Optional.of(Orders.builder()
                .createDate(LocalDate.now())
                .totalPrice(5000)
                .id(1)
                .status(true)
                .isConfirmed(false)
                .build());
        Optional<Orders> orders3 = Optional.of(Orders.builder()
                .createDate(LocalDate.now())
                .totalPrice(5000)
                .id(1)
                .status(true)
                .isConfirmed(false)
                .build());
        Page<Orders> ordersPage = new PageImpl<Orders>(List.of(orders1.get(), orders2.get(), orders3.get()), pageable, 3);
        Mockito.when(orderRepository.findAll(pageable)).thenReturn(ordersPage);
        Page<OrderDTO> orderDTOPage = ordersPage.map(order -> modelMapper.map(order, OrderDTO.class));

        Assertions.assertEquals(orderSevice.getAllOrders(pageable.getPageNumber(), pageable.getPageSize()), orderDTOPage);
        Assertions.assertEquals(orderSevice.getAllOrders(pageable.getPageNumber(), pageable.getPageSize()).getTotalPages(), orderDTOPage.getTotalPages());
        Assertions.assertEquals(orderSevice.getAllOrders(pageable.getPageNumber(), pageable.getPageSize()).getSize(), orderDTOPage.getSize());

    }

    @Test
    void getOrderByUserId() {
        User user = User.builder().id(1).build();
        Pageable pageable = PageRequest.of(0, 10);
        Optional<Orders> orders1 = Optional.of(Orders.builder()
                .createDate(LocalDate.now())
                .totalPrice(5000)
                .id(1)
                .status(true)
                .isConfirmed(false)
                .build());

        Optional<Orders> orders2 = Optional.of(Orders.builder()
                .createDate(LocalDate.now())
                .totalPrice(5000)
                .id(1)
                .status(true)
                .isConfirmed(false)
                .build());
        Page<Orders> ordersPage = new PageImpl<Orders>(List.of(orders1.get(), orders2.get()), pageable, 2);
        Mockito.when(orderRepository.getOrdersByUserId(pageable, user.getId())).thenReturn(ordersPage);
        Page<OrderDTO> orderDTOPage = ordersPage.map(orders -> modelMapper.map(orders, OrderDTO.class));

        Assertions.assertEquals(orderSevice.getOrderByUserId(pageable.getPageNumber(), pageable.getPageSize(), user.getId()), orderDTOPage);
        Assertions.assertEquals(orderSevice.getOrderByUserId(pageable.getPageNumber(), pageable.getPageSize(), user.getId()).getTotalPages(), orderDTOPage.getTotalPages());
        Assertions.assertEquals(orderSevice.getOrderByUserId(pageable.getPageNumber(), pageable.getPageSize(), user.getId()).getSize(), orderDTOPage.getSize());

    }

    @Test
    void getOrdersByEmail() {
        User user = User.builder().id(1).email("vuvietsang10a9@gmail.com").build();
        Pageable pageable = PageRequest.of(0, 10);
        Optional<Orders> orders1 = Optional.of(Orders.builder()
                .createDate(LocalDate.now())
                .totalPrice(5000)
                .id(1)
                .status(true)
                .isConfirmed(false)
                .build());

        Optional<Orders> orders2 = Optional.of(Orders.builder()
                .createDate(LocalDate.now())
                .totalPrice(5000)
                .id(1)
                .status(true)
                .isConfirmed(false)
                .build());
        Page<Orders> ordersPage = new PageImpl<Orders>(List.of(orders1.get(), orders2.get()), pageable, 2);
        Mockito.when(orderRepository.getOrdersByUserEmail(pageable, user.getEmail())).thenReturn(ordersPage);
        Page<OrderDTO> orderDTOPage = ordersPage.map(orders -> modelMapper.map(orders, OrderDTO.class));

        Assertions.assertEquals(orderSevice.getOrdersByEmail(pageable.getPageNumber(), pageable.getPageSize(), user.getEmail()), orderDTOPage);
        Assertions.assertEquals(orderSevice.getOrdersByEmail(pageable.getPageNumber(), pageable.getPageSize(), user.getEmail()).getTotalPages(), orderDTOPage.getTotalPages());
        Assertions.assertEquals(orderSevice.getOrdersByEmail(pageable.getPageNumber(), pageable.getPageSize(), user.getEmail()).getSize(), orderDTOPage.getSize());
    }
}