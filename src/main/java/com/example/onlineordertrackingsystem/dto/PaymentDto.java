package com.example.onlineordertrackingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private String uuid;
    private String name;
    private String cardNumber;
    private String expirationDate;
    private String cvc;
    private Double amount;
    private String userUuid;
    private String productUuids;
}
