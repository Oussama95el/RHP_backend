package com.simplon.rhp.models;

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
public class LeaveRequest {

    @Id
    private Long id;

    private String type;

    private String status;

    private String startDate;

    private String endDate;

    private String comment;

    @ManyToOne
    private Employee employee;


}
