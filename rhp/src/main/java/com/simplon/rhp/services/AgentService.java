package com.simplon.rhp.services;

import com.simplon.rhp.auth.RegisterRequest;
import com.simplon.rhp.entities.Employee;
import com.simplon.rhp.entities.Profile;
import com.simplon.rhp.repositories.EmployeeRepository;
import com.simplon.rhp.repositories.ProfileRepository;
import com.simplon.rhp.services.mail.EmailSender;
import com.simplon.rhp.user.Role;
import com.simplon.rhp.user.User;
import com.simplon.rhp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    private final ProfileRepository profileRepository;

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

    public List<Employee> getAllEmployees() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            employees.forEach(employee -> {
                System.out.println(employee.toString());
            });
            return employees;
        } catch (Exception e) {
            return null;
        }
    }
}
