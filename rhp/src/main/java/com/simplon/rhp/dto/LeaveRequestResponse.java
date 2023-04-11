package com.simplon.rhp.dto;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LeaveRequestResponse {

    private Long id;
    private String startDate;
    private String endDate;
    private String status;
    private String type;
    private String comment;
    private Long employeeId;

}
