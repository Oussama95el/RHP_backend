package com.simplon.rhp.entities;

import com.simplon.rhp.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "profile_id_seq", sequenceName = "profile_id_seq", allocationSize = 1)
    private Long registrationNumber;
    private String matricule;
    private String jobTitle;
    private String description;
    private Boolean joint;
    private Integer kids = 0;
    private String taxID;
    private String bankAccount;
    private Double taxRate = 0.0;

    @Column(name = "salary_net")
    private Double netSalary = 0.0;
    private Double benefits = 0.0;


}
