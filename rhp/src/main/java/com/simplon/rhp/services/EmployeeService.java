package com.simplon.rhp.services;

import com.simplon.rhp.dto.LeaveRequestResponse;
import com.simplon.rhp.dto.RegisterLeaveRequest;
import com.simplon.rhp.entities.Employee;
import com.simplon.rhp.entities.LeaveRequest;
import com.simplon.rhp.entities.Profile;
import com.simplon.rhp.enums.Status;
import com.simplon.rhp.repositories.EmployeeRepository;
import com.simplon.rhp.repositories.LeaveRequestRepository;
import com.simplon.rhp.repositories.ProfileRepository;
import com.simplon.rhp.user.User;
import com.simplon.rhp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeService {


    private final EmployeeRepository employeeRepository;

    private final LeaveRequestRepository leaveRequestRepository;

    private final ProfileRepository profileRepository;

    private final UserRepository userRepository;



    public Map<String,LeaveRequestResponse> createLeaveRequest(RegisterLeaveRequest leaveRequest){
        try {
            User user = userRepository.findByEmail(leaveRequest.getUserEmail()).orElse(null);
            assert user != null;

            Employee employee = employeeRepository.findEmployeeByUser(user);
            LeaveRequest leave = LeaveRequest.builder()
                    .startDate(leaveRequest.getStartDate())
                    .endDate(leaveRequest.getEndDate())
                    .type(leaveRequest.getType())
                    .status(Status.PENDING)
                    .comment(leaveRequest.getComment())
                    .employee(employee)
                    .build();

            LeaveRequest res = leaveRequestRepository.save(leave);

            // dto response
            LeaveRequestResponse leaveRequestResponse = LeaveRequestResponse.builder()
                    .id(res.getId())
                    .startDate(res.getStartDate())
                    .endDate(res.getEndDate())
                    .status(res.getStatus().toString())
                    .type(res.getType())
                    .comment(res.getComment())
                    .employeeId(res.getEmployee().getId())
                    .build();


            return Map.of("data", leaveRequestResponse);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Map<?,?> updateProfile(Profile profile) {
        try {
            Profile prof =  profileRepository.findProfileByRegistrationNumber(profile.getRegistrationNumber());
            assert prof != null;

                prof.setKids(profile.getKids());
                prof.setBenefits(profile.getBenefits());
                prof.setJoint(profile.getJoint());
                prof.setTaxRate(profile.getTaxRate());
                prof.setDescription(profile.getDescription());
                prof.setNetSalary(profile.getNetSalary());
                prof.setBankAccount(profile.getBankAccount());
                prof.setJobTitle(profile.getJobTitle());

                profileRepository.save(prof);
            return Map.of("profile", prof);
        }catch (Exception e){
            e.printStackTrace();
            return Map.of("data", "Profile update failed");
        }
    }


    public Map<?,?> getProfile(String email){
        try {
            Map<String, Object> data = new java.util.HashMap<>();
            User user = userRepository.findByEmail(email).orElse(null);
            assert user != null;
            data.put("user", user);
            Employee employee = employeeRepository.findEmployeeByUser(user);
            Profile profile = employee.getProfile();
            data.put("profile", profile);
            return data;
        }catch (Exception e){
            e.printStackTrace();
            return Map.of("data", "Profile update failed");
        }
    }
}
