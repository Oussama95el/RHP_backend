package com.simplon.rhp.services;

import com.simplon.rhp.auth.RegisterRequest;
import com.simplon.rhp.entities.AgentRh;
import com.simplon.rhp.entities.LeaveRequest;
import com.simplon.rhp.enums.Status;
import com.simplon.rhp.repositories.AgentRhRepository;
import com.simplon.rhp.repositories.LeaveRequestRepository;
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
public class ManagerService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AgentRhRepository agentRhRepository;

    private final LeaveRequestRepository leaveRequestRepository;

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

    public Map<?,?> getLeaveRequests() {
        try {
            List<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();
            return Map.of("leaveRequests", leaveRequests);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<?,?> editLeaveRequest(Long id, String status) {
        try {
            LeaveRequest leaveRequest = leaveRequestRepository.findById(id).orElse(null);
            assert leaveRequest != null;
            if (status.equals("APPROVED")) {
                leaveRequest.setStatus(Status.APPROVED);
                leaveRequestRepository.save(leaveRequest);
//                emailSender.sendEmailLeaveRequestApproved(leaveRequest.getUser().getEmail(), leaveRequest.getUser().getFirstname(), leaveRequest.getStartDate(), leaveRequest.getEndDate());
            } else if (status.equals("REJECTED")) {
                leaveRequest.setStatus(Status.REJECTED);
                leaveRequestRepository.save(leaveRequest);
//                emailSender.sendEmailLeaveRequestRejected(leaveRequest.getUser().getEmail(), leaveRequest.getUser().getFirstname(), leaveRequest.getStartDate(), leaveRequest.getEndDate());
            }
           return getLeaveRequests();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
