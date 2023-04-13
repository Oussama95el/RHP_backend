package com.simplon.rhp.entities;

import jakarta.persistence.*;
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
    @GeneratedValue( strategy = GenerationType.SEQUENCE)
    @SequenceGenerator( name = "payslip_id_sequence" , sequenceName = "payslip_id_sequence" , allocationSize = 1)
    private Long id;

    private Date month;

    private float grossSalary;

    private float benefits;

    private float taxRate;

    private float taxAmount;

    private float deductions;

    private float overtimeHours;

    @ManyToOne
    private Employee employee;

}
