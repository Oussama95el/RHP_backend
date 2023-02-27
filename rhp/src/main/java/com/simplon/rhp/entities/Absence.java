package com.simplon.rhp.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Absence {

        @Id
        private Long id;
        private String type;
        private String startDate;
        private String endDate;
        private String comment;
        private String status;

        @ManyToOne
        private Employee employee;
}
