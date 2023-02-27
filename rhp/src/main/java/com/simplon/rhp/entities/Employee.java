package com.simplon.rhp.entities;

import jakarta.persistence.*;
import lombok.*;


import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee extends User  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "employee_id_seq", sequenceName = "employee_id_seq", allocationSize = 1)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private String country;
    private String department;
    private String address;
    private String city;
    private String phone;

    @OneToOne
    private Profile profile;

    @OneToMany(mappedBy = "employee")
    private Collection<Absence> absences;

    @OneToMany(mappedBy = "employee")
    private Collection<LeaveRequest> leaveRequests;

    @OneToMany(mappedBy = "employee")
    private Collection<PaySlip> paySlips;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
}
