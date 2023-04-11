package com.simplon.rhp.services;

import com.simplon.rhp.auth.RegisterRequest;
import com.simplon.rhp.entities.ManagerRh;
import com.simplon.rhp.repositories.ManagerRhRepository;
import com.simplon.rhp.services.mail.EmailSender;
import com.simplon.rhp.user.Role;
import com.simplon.rhp.user.User;
import com.simplon.rhp.user.UserDto;
import com.simplon.rhp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {


    private final ManagerRhRepository managerRhRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final EmailSender emailSender;

    public Map<String,?> registerManager(RegisterRequest newUser) {
        // create user and save it
        var user = User.builder()
                .firstname(newUser.getFirstname())
                .lastname(newUser.getLastname())
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .role(Role.RH_MANAGER)
                .build();
        var savedUser = userRepository.save(user);
        // create manager and save it
        var manager = com.simplon.rhp.entities.ManagerRh.builder()
                .matricule(newUser.getFirstname().substring(0,1).toUpperCase()+newUser.getLastname().substring(0,1).toUpperCase()+savedUser.getId())
                .user(savedUser)
                .build();
        var saved = managerRhRepository.save(manager);
        // send email
        emailSender.sendEmailUserCreated(newUser.getEmail(),newUser.getFirstname(), newUser.getEmail(), newUser.getPassword());
        Map<String, ManagerRh> data = new HashMap<>();
        data.put("Data", saved);
        return data;
    }

    public Map<String,?> getStatistics() {
        Map<String, Object> data = new HashMap<>();
        List<User> users = userRepository.findAll();

        data.put("managers", users.stream().filter(user -> user.getRole().equals(Role.RH_MANAGER)).count());
        data.put("employees", users.stream().filter(user -> user.getRole().equals(Role.EMPLOYEE)).count());
        data.put("agents", users.stream().filter(user -> user.getRole().equals(Role.RH_AGENT)).count());
        data.put("users", users.size());
        return data;
    }
}
