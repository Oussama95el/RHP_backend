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
public class AgentRh {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "agentRh_id_seq", sequenceName = "agentRh_id_seq", allocationSize = 1)
    private Long id;


    private String matricule;

    @OneToOne
    private Profile profile;

    @OneToOne
    private User user;
}
