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
    private Long registrationNumber;
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
