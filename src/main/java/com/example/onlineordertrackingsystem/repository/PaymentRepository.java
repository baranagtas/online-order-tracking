package com.example.onlineordertrackingsystem.repository;

import com.example.onlineordertrackingsystem.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByUuid(String uuid);
    Payment findByUserUuid(String userUuid);
   // Payment findByProductUuid(String productUuid);
   List<Payment> findByProducts_Uuid(String productUuid); // Burada "_Uuid" ifadesi Product sınıfındaki uuid alanını ifade eder.

}
