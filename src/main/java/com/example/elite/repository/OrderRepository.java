package com.example.elite.repository;

import com.example.elite.entities.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
    public Page<Orders> getOrdersByUserId(Pageable pageable, int userId);
    public Page<Orders> getOrdersByUserEmail(Pageable pageable,String email);
}
