package com.simplon.rhp.controllers;

import com.simplon.rhp.auth.RegisterRequest;
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


    @GetMapping("pdf/payslip")
    public void generatePaySlip(HttpServletResponse response, @PathVariable Long ref) throws Exception {
        response.setContentType("application/pdf");
        // format date
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.LONG);
        String currentDateTime = dateFormatter.format(new java.util.Date());
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=payslip_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        pdfService.generatePaySlipPdf(response,ref);
        // download the PDF file
        response.getOutputStream().flush();
    }
}
