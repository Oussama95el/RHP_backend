package com.simplon.rhp.services;

import com.simplon.rhp.auth.RegisterRequest;
import com.simplon.rhp.dto.EmployeeResponse;
import com.simplon.rhp.dto.RegisterPaySlip;
import com.simplon.rhp.entities.Employee;
import com.simplon.rhp.entities.PaySlip;
import com.simplon.rhp.entities.Profile;
import com.simplon.rhp.repositories.EmployeeRepository;
import com.simplon.rhp.repositories.PaySlipRepository;
import com.simplon.rhp.repositories.ProfileRepository;
import com.simplon.rhp.services.mail.EmailSender;
import com.simplon.rhp.user.Role;
import com.simplon.rhp.user.User;
import com.simplon.rhp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    private final ProfileRepository profileRepository;

    private final PaySlipRepository paySlipRepository;
    private  final UserRepository userRepository;

    private final EmailSender emailSender;


    public Map<?,?> registerEmployee(RegisterRequest employee) {
        try {
        // create user and save it
        var user = User.builder()
                .firstname(employee.getFirstname())
                .lastname(employee.getLastname())
                .email(employee.getEmail())
                .password(passwordEncoder.encode(employee.getPassword()))
                .role(Role.EMPLOYEE)
                .build();
        var savedUser = userRepository.save(user);
        // create profile and save it
        var profile = Profile.builder()
                .matricule(employee.getFirstname().substring(0,1).toUpperCase()+employee.getLastname().substring(0,1).toUpperCase()+savedUser.getId())
                .build();
        var savedProfile = profileRepository.save(profile);
        // create employee and save it
        var emp = Employee.builder()
                .profile(profile)
                .user(savedUser)
                .build();
        var saved = employeeRepository.save(emp);
        // send email
        emailSender.sendEmailUserCreated(employee.getEmail(),employee.getFirstname(), employee.getEmail(), employee.getPassword());
        Map<String, Employee> data = new HashMap<>();
        data.put("Data", saved);
        return data;
        } catch (Exception e) {
            Map<String, String> data = new HashMap<>();
            data.put("Error", e.getMessage());
            return data;
        }
    }

    public List<EmployeeResponse> getAllEmployees() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            List<EmployeeResponse> emp = new ArrayList<>();
            employees.forEach(employee -> {
                EmployeeResponse employeeResponse = EmployeeResponse.builder()
                        .country(employee.getCountry())
                        .department(employee.getDepartment())
                        .address(employee.getAddress())
                        .city(employee.getCity())
                        .phone(employee.getPhone())
                        .user(employee.getUser())
                        .profile(employee.getProfile())
                        .build();
                emp.add(employeeResponse);
            });
            return emp;
        } catch (Exception e) {
            return null;
        }
    }

    public Profile getEmployeeById(Long id) {
        try {
            Employee employee = employeeRepository.findEmployeeByUserId(id).orElse(null);
            if (employee != null) {
                System.out.println(employee.toString());
                return employee.getProfile();
            } else {
                System.out.println("Employee not found");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<?,?> registerPaySlip(RegisterPaySlip payslip){
        try {
            User user = userRepository.findByEmail(payslip.getUserEmail()).orElse(null);
            assert user != null;

            Employee employee = employeeRepository.findEmployeeByUser(user);
            assert employee != null;

            // create payslip from DTO
            PaySlip paySlip = PaySlip.builder()
                    .date(payslip.getDate())
                    .month(payslip.getMonth())
                    .grossSalary(payslip.getGrossSalary())
                    .benefits(payslip.getBenefits())
                    .taxRate(payslip.getTaxRate())
                    .taxAmount(payslip.getTaxAmount())
                    .deductions(payslip.getDeductions())
                    .overtimeHours(payslip.getOvertimeHours())
                    .employee(employee)
                    .build();
            // save payslip
            var result = paySlipRepository.save(paySlip);

            // set payslip to employee and save employee
            employee.getPaySlips().add(result);
            employeeRepository.save(employee);
            Map<String, RegisterPaySlip> data = new HashMap<>();

            // response
            RegisterPaySlip response = RegisterPaySlip.builder()
                    .date(result.getDate())
                    .month(result.getMonth())
                    .grossSalary(result.getGrossSalary())
                    .benefits(result.getBenefits())
                    .taxRate(result.getTaxRate())
                    .taxAmount(result.getTaxAmount())
                    .deductions(result.getDeductions())
                    .overtimeHours(result.getOvertimeHours())
                    .userEmail(result.getEmployee().getUser().getEmail())
                    .build();

            data.put("response", response);
            return data;
        } catch (Exception e) {
            Map<String, String> data = new HashMap<>();
            data.put("Error", e.getMessage());
            return data;
        }
    }
}
