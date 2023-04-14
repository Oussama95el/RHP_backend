package com.simplon.rhp.controllers;


import com.simplon.rhp.dto.PaySlipResponse;
import com.simplon.rhp.dto.RegisterLeaveRequest;
import com.simplon.rhp.entities.LeaveRequest;
import com.simplon.rhp.entities.PaySlip;
import com.simplon.rhp.entities.Profile;
import com.simplon.rhp.pojo.Response;
import com.simplon.rhp.repositories.EmployeeRepository;
import com.simplon.rhp.services.EmployeeService;
import com.simplon.rhp.services.PaySlipService;
import com.simplon.rhp.services.pdf.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee/")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final PaySlipService paySlipService;

    private final PdfService pdfService;



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

    @GetMapping("payslip/all")
    public List<PaySlipResponse> getAllPaySlips(@RequestParam Long id){
       return paySlipService.getAllPaySlipsByEmployeeId(id);
    }

    @GetMapping("pdf/download")
    public void generatePaySlip(HttpServletResponse response, @RequestParam Long id) throws Exception {
        response.setContentType("application/pdf");
        // format date
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.LONG);
        String currentDateTime = dateFormatter.format(new java.util.Date());
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=payslip_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        pdfService.generatePaySlipPdf(response,id);
        // download the PDF file
        response.getOutputStream().flush();
    }
}
