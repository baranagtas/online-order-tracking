package com.example.onlineordertrackingsystem.repository;

import com.example.onlineordertrackingsystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_Id(Long userId);

    List<Order> findByOrderNumber(String orderNumber);

    List<Order> findByUserUuid(String userUuid);
}
