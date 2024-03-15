package com.example.onlineordertrackingsystem.repository;

import com.example.onlineordertrackingsystem.model.Jwt;
import com.example.onlineordertrackingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRepository extends JpaRepository<Jwt, Long> {
    Jwt findByUser(User user);
}
