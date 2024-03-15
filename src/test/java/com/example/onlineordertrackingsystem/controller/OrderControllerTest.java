package com.example.onlineordertrackingsystem.controller;

import com.example.onlineordertrackingsystem.dto.OrderDto;
import com.example.onlineordertrackingsystem.enums.OrderStatus;
import com.example.onlineordertrackingsystem.model.Order;
import com.example.onlineordertrackingsystem.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrder() {
        OrderDto orderDto = new OrderDto("123", LocalDate.now(), 100.0, OrderStatus.NEW, "user123", new ArrayList<>(), null);
        Order createdOrder = new Order(); // Assuming you have Order class defined
        when(orderService.createOrder(orderDto)).thenReturn(createdOrder);

        ResponseEntity<Order> response = orderController.createOrder(orderDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdOrder, response.getBody());
    }

    @Test
    public void testGetOrderById() {
        Long orderId = 123L;
        Order order = new Order(); // Assuming you have Order class defined
        when(orderService.getOrderById(orderId)).thenReturn(order);

        ResponseEntity<Order> response = orderController.getOrderById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    public void testUpdateOrderStatus() {
        Long orderId = 123L;
        OrderStatus status = OrderStatus.PROCESSING;

        ResponseEntity<String> response = orderController.updateOrderStatus(orderId, status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order status updated successfully", response.getBody());
        verify(orderService, times(1)).updateOrderStatus(orderId, status);
    }

    @Test
    public void testGetOrdersByUserUuid() {
        String userUuid = "user123";
        List<Order> orders = new ArrayList<>(); // Assuming you have Order class defined
        when(orderService.getOrdersByUserUuid(userUuid)).thenReturn(orders);

        ResponseEntity<List<Order>> response = orderController.getOrdersByUserUuid(userUuid);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    public void testGetAllOrders() {
        List<Order> orders = new ArrayList<>(); // Assuming you have Order class defined
        when(orderService.getAllOrders()).thenReturn(orders);

        ResponseEntity<List<Order>> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    public void testCancelOrder() {
        Long orderId = 123L;

        ResponseEntity<String> response = orderController.cancelOrder(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order cancelled successfully", response.getBody());
        verify(orderService, times(1)).cancelOrder(orderId);
    }

    @Test
    public void testGetOrderDetails() {
        Long orderId = 123L;
        Order order = new Order(); // Assuming you have Order class defined
        when(orderService.getOrderDetails(orderId)).thenReturn(order);

        ResponseEntity<Order> response = orderController.getOrderDetails(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }
}
