package com.simplon.rhp.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterLeaveRequest {

    private String startDate;
    private String endDate;
    private String type;
    private String status;
    private String comment;
    private String userEmail;
}
