package com.simplon.rhp.controllers;


import com.simplon.rhp.dto.RegisterLeaveRequest;
import com.simplon.rhp.entities.LeaveRequest;
import com.simplon.rhp.entities.Profile;
import com.simplon.rhp.pojo.Response;
import com.simplon.rhp.repositories.EmployeeRepository;
import com.simplon.rhp.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee/")
public class EmployeeController {

    private final EmployeeService employeeService;



    @PostMapping("leave-request")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    public Response createLeaveRequest(@RequestBody RegisterLeaveRequest leaveRequest){
        var res =  employeeService.createLeaveRequest(leaveRequest);
        if (res == null){
            return Response.builder()
                    .data(null)
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Leave request creation failed")
                    .build();
        }
        return Response.builder()
                .data(res)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .message("Leave request created successfully")
                .build();
    }


    @PostMapping("profile/update")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    public Response updateProfile(@RequestBody Profile profile){
        System.out.println("\n\nprofile : \n" + profile.toString());
        return  Response.builder()
                .data(employeeService.updateProfile(profile))
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .message("Profile updated successfully")
                .build();
    }

    @GetMapping("profile")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    public Response getProfile(@RequestParam String email){
        return Response.builder()
                .data(employeeService.getProfile(email))
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .message("Profile retrieved successfully")
                .build();
    }
}
