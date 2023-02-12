package com.simplon.rhp.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Statistics {

    @Id
    private Long id;

    private Date month;

    private int daysOfAbsence;

    private int leaveRequest;

    private int pendingLeaveRequest;

    private int approvedLeaveRequest;

    private int rejectedLeaveRequest;

    private int overtimeHours;

    private int sickDays;


    @OneToOne
    private Employee employee;






}
