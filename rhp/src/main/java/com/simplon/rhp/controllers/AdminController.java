package com.simplon.rhp.controllers;

import com.simplon.rhp.auth.RegisterRequest;
import com.simplon.rhp.pojo.Response;
import com.simplon.rhp.repositories.ManagerRhRepository;
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


    private final ManagerRhRepository managerRhRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("register/manager")
    public Response registerManager(@RequestBody RegisterRequest newUser) {
        var user = User.builder()
                .firstname(newUser.getFirstname())
                .lastname(newUser.getLastname())
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .role(Role.RH_MANAGER)
                .build();
        var saved = userRepository.save(user);
        Map<String, User> data = new HashMap<>();
        data.put("Data", saved);
        return Response.builder()
                .timestamp(java.time.LocalDateTime.now())
                .statusCode(200)
                .status(org.springframework.http.HttpStatus.OK)
                .message("User registered successfully")
                .reason("User registered successfully")
                .developerMessage("User registered successfully")
                .data(data)
                .build();
    }


    @GetMapping("statistics")
    public Response getStatistics() {
        Map<String, Object> data = new HashMap<>();
        data.put("managers", userRepository.findByRole(Role.RH_MANAGER).size());
        data.put("employees", userRepository.findByRole(Role.EMPLOYEE).size());
        data.put("agents", userRepository.findByRole(Role.RH_AGENT).size());
        data.put("users", userRepository.findAll().size());
        return Response.builder()
                .timestamp(java.time.LocalDateTime.now())
                .statusCode(200)
                .status(org.springframework.http.HttpStatus.OK)
                .message("Statistics retrieved successfully")
                .reason("Statistics retrieved successfully")
                .developerMessage("Data fetched successfully")
                .data(data)
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
