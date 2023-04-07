package com.simplon.rhp.controllers;

import com.simplon.rhp.auth.RegisterRequest;
import com.simplon.rhp.pojo.Response;
import com.simplon.rhp.repositories.ManagerRhRepository;
import com.simplon.rhp.services.AdminService;
import com.simplon.rhp.user.Role;
import com.simplon.rhp.user.User;
import com.simplon.rhp.user.UserDto;
import com.simplon.rhp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class AdminController {


    private final AdminService adminService;
    private final UserRepository userRepository;

    @PostMapping("register/manager")
    public Response registerManager(@RequestBody RegisterRequest newUser) {
        return Response.builder()
                .timestamp(java.time.LocalDateTime.now())
                .statusCode(200)
                .status(org.springframework.http.HttpStatus.OK)
                .message("User registered successfully")
                .reason("User registered successfully")
                .developerMessage("User registered successfully")
                .data(adminService.registerManager(newUser))
                .build();
    }


    @GetMapping("statistics")
    public Response getStatistics() {
        return Response.builder()
                .timestamp(java.time.LocalDateTime.now())
                .statusCode(200)
                .status(org.springframework.http.HttpStatus.OK)
                .message("Statistics retrieved successfully")
                .reason("Statistics retrieved successfully")
                .developerMessage("Data fetched successfully")
                .data(adminService.getStatistics())
                .build();
    }

    @GetMapping("users")
    public ResponseEntity<List<UserDto>> getUsers() {
        Map<Integer, UserDto> data = new HashMap<>();
        List<User> users = userRepository.findAll().stream().filter(user -> user.getRole() != Role.ADMIN).toList();
        for (User user : users) {
            data.put(user.getId(), UserDto.builder()
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build());
        }
        return ResponseEntity.ok(data.values().stream().toList());
    }



}
