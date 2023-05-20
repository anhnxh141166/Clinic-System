package com.dental.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
public class MedicalInformation {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer medicalInformationId;

    @Column(nullable = false, columnDefinition = "nvarchar(254)")
    private String result;

    @Column(nullable = false, columnDefinition = "nvarchar(254)")
    private String reason;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Double unitPrice;

    @Column(nullable = false)
    private Date date;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(name = "patient_history_id", nullable = false)
    private PatientHistory patientHistory;
}
