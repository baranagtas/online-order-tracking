package com.example.onlineordertrackingsystem.controller;

import com.example.onlineordertrackingsystem.dto.UserDto;
import com.example.onlineordertrackingsystem.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(@Lazy UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDto> getUserByUuid(@PathVariable String uuid) {
        UserDto user = userService.getUserByUuid(uuid);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateUser(@PathVariable String uuid, @RequestBody UserDto updatedUserDto) {
        userService.updateUser(uuid, updatedUserDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable String uuid) {
        userService.deleteUser(uuid);
        return ResponseEntity.ok().build();
    }
}


