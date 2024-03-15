package com.example.onlineordertrackingsystem.service;


import com.example.onlineordertrackingsystem.dto.PaymentDto;
import com.example.onlineordertrackingsystem.model.Payment;

public interface PaymentService {
    PaymentDto doPaymentAndSaveCustomerDetails(PaymentDto paymentDto);

    void updateUserCartDetails(String userUuid, PaymentDto updatedPaymentDto);

    void deletePaymentByUserUuid(String userUuid);

    PaymentDto getUserCartDetailsWithUserUuid(String userUuid);
}
