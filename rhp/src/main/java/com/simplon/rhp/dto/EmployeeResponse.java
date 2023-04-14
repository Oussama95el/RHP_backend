package com.simplon.rhp.dto;

import com.simplon.rhp.entities.Profile;
import com.simplon.rhp.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

    private String country;
    private String department;
    private String address;
    private String city;
    private String phone;
    private User user;
    private Profile profile;

}
