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
public class ManagerRh {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String matricule;

    @OneToOne
    private User user;
}
