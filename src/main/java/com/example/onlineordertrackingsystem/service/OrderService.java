package com.example.onlineordertrackingsystem.service;

import com.example.onlineordertrackingsystem.dto.OrderDto;
import com.example.onlineordertrackingsystem.enums.OrderStatus;
import com.example.onlineordertrackingsystem.model.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderDto orderDto);
    Order getOrderById(Long orderId);
    List<Order> getOrdersByUserUuid(String userUuid);
    void updateOrderStatus(Long orderId, OrderStatus newStatus);
    List<Order> getAllOrders();
    void cancelOrder(Long orderId);
    Order getOrderDetails(Long orderId);
}
