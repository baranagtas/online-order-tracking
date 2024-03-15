package com.example.onlineordertrackingsystem.controller;

import com.example.onlineordertrackingsystem.dto.PaymentDto;
import com.example.onlineordertrackingsystem.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/make-payment")
    public ResponseEntity<PaymentDto> doPaymentAndSaveCustomerDetails(@RequestBody PaymentDto paymentDto) {
        PaymentDto savedPayment = paymentService.doPaymentAndSaveCustomerDetails(paymentDto);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }

    @GetMapping("/{userUuid}")
    public ResponseEntity<PaymentDto> getUserCartDetailsWithUserUuid(@PathVariable String userUuid) {
        PaymentDto paymentDto = paymentService.getUserCartDetailsWithUserUuid(userUuid);
        return ResponseEntity.ok(paymentDto);
    }

    @PutMapping("/{userUuid}")
    public ResponseEntity<Void> updateUserCartDetails(@PathVariable String userUuid, @RequestBody PaymentDto updatedPaymentDto) {
        paymentService.updateUserCartDetails(userUuid, updatedPaymentDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userUuid}")
    public ResponseEntity<Void> deletePaymentByUserUuid(@PathVariable String userUuid) {
        paymentService.deletePaymentByUserUuid(userUuid);
        return ResponseEntity.noContent().build();
    }
}