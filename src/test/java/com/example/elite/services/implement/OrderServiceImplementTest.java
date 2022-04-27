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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;

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

    @Mock
    SecurityContextHolder securityContextHolder;

    @InjectMocks
    private OrderServiceImplement orderSevice;

    @Mock
    private ModelMapper modelMapper;
    @Test
    void getOrder_WithValidData_shouldReturnOrderDTO() {
        Optional<Orders> orders = Optional.of(Mockito.mock(Orders.class));
        Optional<OrderDto> orderDto = Optional.of(Mockito.mock(OrderDto.class));
        Mockito.when(orderRepository.findById(orders.get().getId())).thenReturn(orders);
        Mockito.when(modelMapper.map(orders.get(),OrderDto.class)).thenReturn(orderDto.get());
        Assertions.assertEquals(orderSevice.getOrderById(orders.get().getId()), orderDto.get());
    }

    @Test
    void deleteOrder_WithValidData_shouldReturnOrderDTO() {
        Optional<Orders> orders = Optional.of(Mockito.mock(Orders.class));
        Optional<OrderDto> orderDto = Optional.of(Mockito.mock(OrderDto.class));
        Mockito.when(orderRepository.findById(orders.get().getId())).thenReturn(orders);
        Mockito.when(orderRepository.save(orders.get())).thenReturn(orders.get());
        Mockito.when(modelMapper.map(orders.get(),OrderDto.class)).thenReturn(orderDto.get());
        Assertions.assertEquals(orderSevice.deleteOrder(orders.get().getId()), orderDto.get());
        Assertions.assertEquals(orderSevice.deleteOrder(orders.get().getId()).isStatus(), orderDto.get().isStatus());
    }

    @Test
    void checkout() {
        User user = User.builder().id(1).username("vuvietsang").build();
        Optional<Product> product = Optional.of(Product.builder().price(1000).id(1).build());
        Optional<Product> product1 = Optional.of(Product.builder().price(1000).id(2).build());
        double totalPrice = 0;

        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("vuvietsang");
        Mockito.when(userRepository.findUserByUsername("vuvietsang")).thenReturn(user);

        Orders order = Orders.builder().createDate(LocalDate.now()).status(false).user(user).build();
        Mockito.when(orderRepository.save(order)).thenReturn(order);

        OrderDetailDto[] orderDetailDTO ={OrderDetailDto.builder().productId(1L).quantity(10).build(), OrderDetailDto.builder().productId(2L).quantity(10).build()};
        Mockito.when(productRepository.findById(orderDetailDTO[0].getProductId())).thenReturn(product);
        Mockito.when(productRepository.findById(orderDetailDTO[1].getProductId())).thenReturn(product1);

        OrderDetail oDetails = OrderDetail.builder().orders(order).product(product.get()).quantity(product.get().getQuantity())
                .price(product.get().getPrice()).build();
        Mockito.when(orderDetailsRepository.save(oDetails)).thenReturn(oDetails);
        totalPrice += oDetails.getPrice()*oDetails.getQuantity();

        OrderDetail oDetails1 = OrderDetail.builder().orders(order).product(product.get()).quantity(product.get().getQuantity())
                .price(product.get().getPrice()).build();
        Mockito.when(orderDetailsRepository.save(oDetails1)).thenReturn(oDetails1);
        totalPrice += oDetails1.getPrice()*oDetails.getQuantity();

        order.setTotalPrice(totalPrice);
        OrderDto orderDTO = modelMapper.map(order, OrderDto.class);
        Assertions.assertEquals(orderSevice.checkout(orderDetailDTO),orderDTO);
    }
    @Test
    void confirmOrder_WithValidData_shouldReturnOrderDTO() {
        Optional<Orders> orders = Optional.of(Mockito.mock(Orders.class));
        Optional<OrderDto> orderDto = Optional.of(Mockito.mock(OrderDto.class));
        Mockito.when(orderRepository.findById(orders.get().getId())).thenReturn(orders);
        Mockito.when(orderRepository.save(orders.get())).thenReturn(orders.get());
        Mockito.when(modelMapper.map(orders.get(),OrderDto.class)).thenReturn(orderDto.get());
        Assertions.assertEquals(orderSevice.confirmOrder(orders.get().getId()), orderDto.get());
        Assertions.assertEquals(orderSevice.confirmOrder(orders.get().getId()).isConfirmed(), orderDto.get().isConfirmed());
    }

    @Test
    void getAllOrders_WithValidData_shouldReturnOrderDTOPage() {
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
        Page<OrderDto> orderDTOPage = ordersPage.map(order -> modelMapper.map(order, OrderDto.class));

        Assertions.assertEquals(orderSevice.getAllOrders(pageable.getPageNumber(), pageable.getPageSize()), orderDTOPage);
        Assertions.assertEquals(orderSevice.getAllOrders(pageable.getPageNumber(), pageable.getPageSize()).getTotalPages(), orderDTOPage.getTotalPages());
        Assertions.assertEquals(orderSevice.getAllOrders(pageable.getPageNumber(), pageable.getPageSize()).getSize(), orderDTOPage.getSize());

    }

    @Test
    void getOrderByUserId_WithValidData_shouldReturnOrderDTOPage() {
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
        Page<OrderDto> orderDTOPage = ordersPage.map(orders -> modelMapper.map(orders, OrderDto.class));

        Assertions.assertEquals(orderSevice.getOrderByUserId(pageable.getPageNumber(), pageable.getPageSize(), user.getId()), orderDTOPage);
        Assertions.assertEquals(orderSevice.getOrderByUserId(pageable.getPageNumber(), pageable.getPageSize(), user.getId()).getTotalPages(), orderDTOPage.getTotalPages());
        Assertions.assertEquals(orderSevice.getOrderByUserId(pageable.getPageNumber(), pageable.getPageSize(), user.getId()).getSize(), orderDTOPage.getSize());

    }

    @Test
    void getOrdersByEmail_WithValidData_shouldReturnOrderDTOPage() {
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
        Page<OrderDto> orderDTOPage = ordersPage.map(orders -> modelMapper.map(orders, OrderDto.class));
        Assertions.assertEquals(orderSevice.getOrdersByEmail(pageable.getPageNumber(), pageable.getPageSize(), user.getEmail()), orderDTOPage);
        Assertions.assertEquals(orderSevice.getOrdersByEmail(pageable.getPageNumber(), pageable.getPageSize(), user.getEmail()).getTotalPages(), orderDTOPage.getTotalPages());
        Assertions.assertEquals(orderSevice.getOrdersByEmail(pageable.getPageNumber(), pageable.getPageSize(), user.getEmail()).getSize(), orderDTOPage.getSize());
    }
}