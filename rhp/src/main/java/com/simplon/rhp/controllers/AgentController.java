package com.simplon.rhp.controllers;

import com.simplon.rhp.auth.RegisterRequest;
import com.simplon.rhp.entities.Employee;
import com.simplon.rhp.pojo.Response;
import com.simplon.rhp.repositories.AgentRhRepository;
import com.simplon.rhp.services.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agent/")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;
    private final AgentRhRepository agentRhRepository;


    @PostMapping("register/employee")
    public Response registerEmployee(@RequestBody RegisterRequest employee){
        System.out.println(employee.toString());

        return Response.builder()
                .timestamp(java.time.LocalDateTime.now())
                .statusCode(200)
                .status(org.springframework.http.HttpStatus.OK)
                .message("User registered successfully")
                .reason("User registered successfully")
                .developerMessage("User registered successfully")
                .data(agentService.registerEmployee(employee))
                .build();
    }


    @GetMapping("employees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return ResponseEntity.ok(agentService.getAllEmployees());
    }

}
