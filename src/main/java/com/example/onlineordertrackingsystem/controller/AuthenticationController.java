package com.example.onlineordertrackingsystem.controller;

import com.example.onlineordertrackingsystem.model.AuthenticationResponse;
import com.example.onlineordertrackingsystem.dto.UserDto;
import com.example.onlineordertrackingsystem.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<AuthenticationResponse> registration(@RequestBody UserDto request){
        return new ResponseEntity<>(userService.saveUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody UserDto request){
        return new ResponseEntity<>(userService.authenticate(request.getEmail(),request.getPassword()),HttpStatus.OK);
    }
}
