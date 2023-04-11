package com.simplon.rhp.entities;

import com.simplon.rhp.user.User;
import com.simplon.rhp.user.UserDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "employee")
    @Fetch(FetchMode.JOIN)
    @ToString.Exclude
    private Collection<Absence> absences;

    @OneToMany(mappedBy = "employee")
    @Fetch(FetchMode.JOIN)
    @ToString.Exclude
    private Collection<LeaveRequest> leaveRequests;

    @OneToMany(mappedBy = "employee")
    @Fetch(FetchMode.JOIN)
    @ToString.Exclude
    private Collection<PaySlip> paySlips;



}
