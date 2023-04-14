package com.simplon.rhp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaySlipResponse {

    private Long id;

    private Date date;

    private String month;

    private float grossSalary;

    private float benefits;

    private float taxRate;

    private float taxAmount;

    private float deductions;

    private int overtimeHours;

    private String userEmail;
}
