
package com.simplon.rhp.controllers;
import com.simplon.rhp.auth.RegisterRequest;
import com.simplon.rhp.pojo.Response;
import com.simplon.rhp.services.AdminService;
import com.simplon.rhp.user.Role;
import com.simplon.rhp.user.User;
import com.simplon.rhp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class AdminController {


    private final AdminService adminService;
    private final UserRepository userRepository;

    @PostMapping("register/manager")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize(value = "hasRole('ADMIN', 'RH_MANAGER', 'EMPLOYEE','RH_AGENT')")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll().stream().filter(user -> user.getRole() != Role.ADMIN).limit(5).toList();
        return ResponseEntity.ok(users);
    }



}
