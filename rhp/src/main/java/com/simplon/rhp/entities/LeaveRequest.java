package com.simplon.rhp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simplon.rhp.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "leave_request_id_seq", sequenceName = "leave_request_id_seq", allocationSize = 1)
    private Long id;

    private String type;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String startDate;

    private String endDate;

    private String comment;

    @ManyToOne
    @JsonIgnore
    private Employee employee;


}
