package com.example.onlineordertrackingsystem.service.impl;


import com.example.onlineordertrackingsystem.dto.PaymentDto;
import com.example.onlineordertrackingsystem.model.Payment;
import com.example.onlineordertrackingsystem.model.Product;
import com.example.onlineordertrackingsystem.model.User;
import com.example.onlineordertrackingsystem.repository.PaymentRepository;
import com.example.onlineordertrackingsystem.repository.ProductRepository;
import com.example.onlineordertrackingsystem.repository.UserRepository;
import com.example.onlineordertrackingsystem.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public PaymentDto doPaymentAndSaveCustomerDetails(PaymentDto paymentDto) {
        Optional<Product> product = productRepository.findByUuid(paymentDto.getProductUuids().trim());
        Optional<User> user = userRepository.findByUuid(paymentDto.getUserUuid().trim());

        if (!product.isPresent() || !user.isPresent()) {
            throw new RuntimeException("UUID not found");
        }
        Payment payment = new Payment();
        payment.setName(paymentDto.getName());
        payment.setCardNumber(paymentDto.getCardNumber());
        payment.setExpirationDate(paymentDto.getExpirationDate());
        payment.setCvc(paymentDto.getCvc());
        payment.setAmount(paymentDto.getAmount());
        payment.setUser(user.get());

        if (paymentDto.getProductUuids() != null && !paymentDto.getProductUuids().isEmpty()) {
            List<Product> products = productRepository.findByUuidIn(Collections.singletonList(paymentDto.getProductUuids()));
            payment.setProducts(products);
        }

        Payment savedPayment = paymentRepository.save(payment);

        // SavedPayment'i PaymentDto'ya dönüştür
        PaymentDto savedPaymentDto = new PaymentDto();
        savedPaymentDto.setName(savedPayment.getName());
        savedPaymentDto.setCardNumber(savedPayment.getCardNumber());
        savedPaymentDto.setExpirationDate(savedPayment.getExpirationDate());
        savedPaymentDto.setCvc(savedPayment.getCvc());
        savedPaymentDto.setAmount(savedPayment.getAmount());
        savedPaymentDto.setUserUuid(savedPayment.getUser().getUuid());
        savedPaymentDto.setProductUuids(savedPayment.getProducts().stream().map(Product::getUuid).collect(Collectors.toList()).toString());

        return savedPaymentDto;
    }

    @Override
    public PaymentDto getUserCartDetailsWithUserUuid(String userUuid) {
        Payment payment = paymentRepository.findByUserUuid(userUuid.trim());
        if (payment == null) {
            throw new RuntimeException("payment nout found");
        }

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setUuid(payment.getUuid());
        paymentDto.setName(payment.getName());
        paymentDto.setCardNumber(payment.getCardNumber());
        paymentDto.setExpirationDate(payment.getExpirationDate());
        paymentDto.setCvc(payment.getCvc());
        paymentDto.setAmount(payment.getAmount());
        paymentDto.setProductUuids(payment.getProducts().stream().map(Product::getUuid).collect(Collectors.toList()).toString());
        paymentDto.setUserUuid(payment.getUser().getUuid());

        return paymentDto;
    }

    @Override
    public void updateUserCartDetails(String userUuid, PaymentDto updatedPaymentDto) {
        Payment existingPayment = paymentRepository.findByUserUuid(userUuid.trim());
        if (existingPayment != null) {
            existingPayment.setName(updatedPaymentDto.getName());
            existingPayment.setCardNumber(updatedPaymentDto.getCardNumber());
            existingPayment.setExpirationDate(updatedPaymentDto.getExpirationDate());
            existingPayment.setCvc(updatedPaymentDto.getCvc());
            paymentRepository.save(existingPayment);
        }
    }

    @Override
    public void deletePaymentByUserUuid(String userUuid) {
        Payment existingPayment = paymentRepository.findByUserUuid(userUuid.trim());
        if (existingPayment != null) {
            paymentRepository.delete(existingPayment);
        }
    }
}
