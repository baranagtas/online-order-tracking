package com.example.onlineordertrackingsystem.dto;


import com.example.onlineordertrackingsystem.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String orderNumber;
    private LocalDate orderDate;
    private Double totalAmount;
    private OrderStatus status;
    private String userUuid;
    private List<OrderItemDto> orderItems;
    private PaymentDto paymentDto;
}
