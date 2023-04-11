package com.simplon.rhp.services;

import com.simplon.rhp.auth.RegisterRequest;
import com.simplon.rhp.entities.AgentRh;
import com.simplon.rhp.repositories.AgentRhRepository;
import com.simplon.rhp.services.mail.EmailSender;
import com.simplon.rhp.user.Role;
import com.simplon.rhp.user.User;
import com.simplon.rhp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ManagerService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AgentRhRepository agentRhRepository;

    private final EmailSender emailSender;


    public Map<String,?> createAgent(RegisterRequest newUser) {
        // create user and save it
        var user = User.builder()
                .firstname(newUser.getFirstname())
                .lastname(newUser.getLastname())
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .role(Role.RH_AGENT)
                .build();
        var savedUser = userRepository.save(user);
        // create agent and save it
        var agent = com.simplon.rhp.entities.AgentRh.builder()
                .matricule(newUser.getFirstname().substring(0,1).toUpperCase()+newUser.getLastname().substring(0,1).toUpperCase()+savedUser.getId())
                .user(savedUser)
                .build();
        var saved = agentRhRepository.save(agent);
        // send email
        emailSender.sendEmailUserCreated(newUser.getEmail(),newUser.getFirstname(), newUser.getEmail(), newUser.getPassword());
        Map<String, AgentRh> data = new HashMap<>();
        data.put("Data", saved);
        return data;
    }
}
