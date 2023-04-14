package com.simplon.rhp.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditLeaveRequest {

    private Long id;

    private String status;
}
