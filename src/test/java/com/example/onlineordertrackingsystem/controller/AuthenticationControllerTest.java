package com.example.onlineordertrackingsystem.controller;

import com.example.onlineordertrackingsystem.model.AuthenticationResponse;
import com.example.onlineordertrackingsystem.dto.UserDto;
import com.example.onlineordertrackingsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AuthenticationControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegistration() {
        // Verilen kullanıcı isteği
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("testPassword");

        // Kullanıcı servisinin beklenen sonucu
        AuthenticationResponse expectedResponse = new AuthenticationResponse("Test Token");

        // Kullanıcı servisinden döndürülen sonuç
        when(userService.saveUser(userDto)).thenReturn(expectedResponse);

        // Kontrolcü metodunu çağırma
        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.registration(userDto);

        // Sonucun doğru olup olmadığını kontrol etme
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    public void testAuthentication() {
        // Verilen kullanıcı isteği
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("testPassword");

        // Kullanıcı servisinin beklenen sonucu
        AuthenticationResponse expectedResponse = new AuthenticationResponse("Test Token");

        // Kullanıcı servisinden döndürülen sonuç
        when(userService.authenticate("test@example.com", "testPassword")).thenReturn(expectedResponse);

        // Kontrolcü metodunu çağırma
        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.authenticate(userDto);

        // Sonucun doğru olup olmadığını kontrol etme
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}