package com.simplon.rhp.entities;

import com.simplon.rhp.user.User;
import jakarta.persistence.*;
import lombok.*;


import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "employee_id_seq", sequenceName = "employee_id_seq", allocationSize = 1)
    private Long id;
    private String country;
    private String department;
    private String address;
    private String city;
    private String phone;

    @OneToOne
    private Profile profile;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "employee")
    private Collection<Absence> absences;

    @OneToMany(mappedBy = "employee")
    private Collection<LeaveRequest> leaveRequests;

    @OneToMany(mappedBy = "employee")
    private Collection<PaySlip> paySlips;

}
