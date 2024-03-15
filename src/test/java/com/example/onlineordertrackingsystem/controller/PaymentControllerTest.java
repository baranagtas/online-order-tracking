package com.example.onlineordertrackingsystem.controller;

import com.example.onlineordertrackingsystem.dto.PaymentDto;
import com.example.onlineordertrackingsystem.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    void testDoPaymentAndSaveCustomerDetails() throws Exception {
        PaymentDto paymentDto = new PaymentDto();

        when(paymentService.doPaymentAndSaveCustomerDetails(any(PaymentDto.class))).thenReturn(paymentDto);

        mockMvc.perform(post("/make-payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(paymentDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$....").value("...")); // Dönüş değerine göre jsonPath ekleyin
    }

    @Test
    void testGetUserCartDetailsWithUserUuid() throws Exception {
        String userUuid = "exampleUserUuid";
        PaymentDto paymentDto = new PaymentDto();

        when(paymentService.getUserCartDetailsWithUserUuid(userUuid)).thenReturn(paymentDto);

        mockMvc.perform(get("/" + userUuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$....").value("...")); // Dönüş değerine göre jsonPath ekleyin
    }

    @Test
    void testUpdateUserCartDetails() throws Exception {
        String userUuid = "exampleUserUuid";
        PaymentDto updatedPaymentDto = new PaymentDto();

        doNothing().when(paymentService).updateUserCartDetails(userUuid, updatedPaymentDto);

        mockMvc.perform(put("/" + userUuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedPaymentDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeletePaymentByUserUuid() throws Exception {
        String userUuid = "exampleUserUuid";

        doNothing().when(paymentService).deletePaymentByUserUuid(userUuid);

        mockMvc.perform(delete("/" + userUuid))
                .andExpect(status().isNoContent());
    }

    // Nesneleri JSON formatına dönüştürmek için yardımcı metod
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
