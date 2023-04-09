package com.simplon.rhp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "profile_id_seq", sequenceName = "profile_id_seq", allocationSize = 1)
    private Long registrationNumber;

    private String matricule;
    private String jobTitle;
    private String description;
    private Boolean joint;
    private int kids;
    private String taxID;
    private String bankAccount;
    private float taxRate;
    private float GrossSalary;
    private float benefits;



}
