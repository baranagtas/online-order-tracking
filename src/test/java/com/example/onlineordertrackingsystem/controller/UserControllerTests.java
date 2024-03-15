package com.example.onlineordertrackingsystem.controller;

import com.example.onlineordertrackingsystem.dto.UserDto;
import com.example.onlineordertrackingsystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetAllUsers() throws Exception {
        // Mock users
        UserDto user1 = new UserDto("1", "John Doe", "john@example.com", "password1", "123456789", "Address 1", LocalDate.now(), LocalDate.now());
        UserDto user2 = new UserDto("2", "Jane Doe", "jane@example.com", "password2", "987654321", "Address 2", LocalDate.now(), LocalDate.now());
        List<UserDto> userList = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(userList.size()))
                .andExpect(jsonPath("$[0].uuid").value(user1.getUuid()))
                .andExpect(jsonPath("$[0].userName").value(user1.getUserName()))
                .andExpect(jsonPath("$[0].email").value(user1.getEmail()))
                .andExpect(jsonPath("$[0].password").value(user1.getPassword()))
                .andExpect(jsonPath("$[0].telephone").value(user1.getTelephone()))
                .andExpect(jsonPath("$[0].address").value(user1.getAddress()))
                .andExpect(jsonPath("$[0].createdAt").value(user1.getCreatedAt().toString()))
                .andExpect(jsonPath("$[0].updatedAt").value(user1.getUpdatedAt().toString()))
                .andExpect(jsonPath("$[1].uuid").value(user2.getUuid()))
                .andExpect(jsonPath("$[1].userName").value(user2.getUserName()))
                .andExpect(jsonPath("$[1].email").value(user2.getEmail()))
                .andExpect(jsonPath("$[1].password").value(user2.getPassword()))
                .andExpect(jsonPath("$[1].telephone").value(user2.getTelephone()))
                .andExpect(jsonPath("$[1].address").value(user2.getAddress()))
                .andExpect(jsonPath("$[1].createdAt").value(user2.getCreatedAt().toString()))
                .andExpect(jsonPath("$[1].updatedAt").value(user2.getUpdatedAt().toString()));
    }

    @Test
    void testGetUserByUuid() throws Exception {
        String uuid = "1";
        UserDto user = new UserDto(uuid, "John Doe", "john@example.com", "password", "123456789", "Address", LocalDate.now(), LocalDate.now());

        when(userService.getUserByUuid(uuid)).thenReturn(user);

        mockMvc.perform(get("/{uuid}", uuid))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid").value(user.getUuid()))
                .andExpect(jsonPath("$.userName").value(user.getUserName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.password").value(user.getPassword()))
                .andExpect(jsonPath("$.telephone").value(user.getTelephone()))
                .andExpect(jsonPath("$.address").value(user.getAddress()))
                .andExpect(jsonPath("$.createdAt").value(user.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(user.getUpdatedAt().toString()));
    }

    @Test
    void testUpdateUser() throws Exception {
        String uuid = "1";
        UserDto updatedUser = new UserDto(uuid, "Updated Name", "updated@example.com", "updatedpassword", "987654321", "Updated Address", LocalDate.now(), LocalDate.now());

        mockMvc.perform(put("/update/{uuid}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedUser)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser() throws Exception {
        String uuid = "1";

        mockMvc.perform(delete("/delete/{uuid}", uuid))
                .andExpect(status().isOk());
    }

    // UserDto objesini JSON formatına dönüştürmek için yardımcı metot
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
