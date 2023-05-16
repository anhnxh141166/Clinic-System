package com.dental.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.Set;

@Entity
@Data
public class Appointment {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointmentId;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private Date time;
    @Column(nullable = false,length = 1, columnDefinition = "bit default 1")
    private boolean status;
    @Column(nullable = true)
    private String note;

    @ManyToMany(mappedBy = "appointment")
    private Set<Service> service;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
}
