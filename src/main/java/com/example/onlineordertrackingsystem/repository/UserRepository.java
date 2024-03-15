package com.example.onlineordertrackingsystem.repository;

import com.example.onlineordertrackingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByUuid(String uuid);
    Optional<User> findByUuid(String uuid);
    User findByUserName(String userName);

    User findByEmail(String email);

}
