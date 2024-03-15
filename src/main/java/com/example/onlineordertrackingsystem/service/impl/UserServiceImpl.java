package com.example.onlineordertrackingsystem.service.impl;

import com.example.onlineordertrackingsystem.beans.AuthenticationResponse;
import com.example.onlineordertrackingsystem.dto.UserDto;
import com.example.onlineordertrackingsystem.enums.UserRole;
import com.example.onlineordertrackingsystem.model.User;
import com.example.onlineordertrackingsystem.repository.UserRepository;
import com.example.onlineordertrackingsystem.security.JwtService;
import com.example.onlineordertrackingsystem.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService,@Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByUuid(String uuid) {
        Optional<User> userOptional = userRepository.findByUuid(uuid);
        if (userOptional.isPresent()) {
            return convertToDto(userOptional.get());
        } else {
            // Burada isteğe bağlı olarak bir hata işleme mekanizması eklenebilir.
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public AuthenticationResponse saveUser(UserDto userDto) {
        User user = convertToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public void updateUser(String uuid, UserDto updatedUserDto) {
        Optional<User> userOptional = userRepository.findByUuid(uuid);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Güncelleme yapılacak alanları burada güncelleyin
            user.setUserName(updatedUserDto.getUserName());
            user.setEmail(updatedUserDto.getEmail());
            user.setTelephone(updatedUserDto.getTelephone());
            user.setAddress(updatedUserDto.getAddress());
            user.setUpdatedAt(LocalDate.now());
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void deleteUser(String uuid) {
        Optional<User> userOptional = userRepository.findByUuid(uuid);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public final User getAuthenticatedUser() {
        String authUserEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return getUserByEmail(authUserEmail);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean isUsernameTaken(String username) {
        User existingUser = userRepository.findByUserName(username);
        return existingUser != null;
    }

    @Override
    public AuthenticationResponse authenticate(String username, String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        var user = userRepository.findByEmail(username);
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }


    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUuid(user.getUuid());
        dto.setUserName(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setTelephone(user.getTelephone());
        dto.setAddress(user.getAddress());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setUuid(userDto.getUuid());
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setTelephone(userDto.getTelephone());
        user.setAddress(userDto.getAddress());
        user.setCreatedAt(userDto.getCreatedAt());
        user.setUpdatedAt(userDto.getUpdatedAt());
        return user;
    }
}