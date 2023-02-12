package com.simplon.rhp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaySlip {

    @Id
    private Long id;

    private Date month;

    private float grossSalary;

    private float netSalary;

    private float benefits;

    private float taxRate;

    private float taxAmount;

    private float deductions;

    private float overtimeHours;

    @ManyToOne
    private Employee employee;

}
