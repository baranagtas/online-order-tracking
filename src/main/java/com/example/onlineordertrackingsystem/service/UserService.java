package com.example.onlineordertrackingsystem.service;

import com.example.onlineordertrackingsystem.beans.AuthenticationResponse;
import com.example.onlineordertrackingsystem.dto.UserDto;
import com.example.onlineordertrackingsystem.model.User;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserByUuid(String uuid);

    AuthenticationResponse saveUser(UserDto userDto);

    void updateUser(String uuid, UserDto updatedUserDto);

    void deleteUser(String uuid);

    boolean isUsernameTaken(String username);

    AuthenticationResponse authenticate(String username, String password);
}
