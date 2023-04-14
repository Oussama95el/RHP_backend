package com.simplon.rhp.controllers;

import com.simplon.rhp.auth.RegisterRequest;
import com.simplon.rhp.dto.EditLeaveRequest;
import com.simplon.rhp.pojo.Response;
import com.simplon.rhp.repositories.AgentRhRepository;
import com.simplon.rhp.services.ManagerService;
import com.simplon.rhp.services.pdf.PdfService;
import com.simplon.rhp.user.User;
import com.simplon.rhp.user.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/manager/")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    private final PdfService pdfService;

    @PostMapping("register/agent")
    public Response registerAgent(@RequestBody RegisterRequest newUser) {
        return Response.builder()
                .timestamp(java.time.LocalDateTime.now())
                .statusCode(200)
                .status(org.springframework.http.HttpStatus.OK)
                .message("User registered successfully")
                .reason("User registered successfully")
                .developerMessage("User registered successfully")
                .data(managerService.createAgent(newUser))
                .build();
    }



       @GetMapping("leave-requests")
        public Response getLeaveRequests() {
           return Response.builder()
                   .timestamp(java.time.LocalDateTime.now())
                   .statusCode(200)
                   .status(org.springframework.http.HttpStatus.OK)
                   .message("Leave requests retrieved successfully")
                   .reason("Leave requests retrieved successfully")
                   .developerMessage("Leave requests retrieved successfully")
                   .data(managerService.getLeaveRequests())
                   .build();
       }

       @PostMapping("leave-requests/edit")
         public Response editLeaveRequest(@RequestBody EditLeaveRequest leaveRequest) {

           System.out.println(leaveRequest.toString() + "\n \n " );


              return Response.builder()
                     .timestamp(java.time.LocalDateTime.now())
                     .statusCode(200)
                     .status(org.springframework.http.HttpStatus.OK)
                     .message("Leave request edited successfully")
                     .reason("Leave request edited successfully")
                     .developerMessage("Leave request edited successfully")
                     .data(managerService.editLeaveRequest(leaveRequest.getId(), leaveRequest.getStatus()))
                     .build();
         }

}
