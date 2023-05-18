package com.dental.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Entity
@Data
public class PatientHistory {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patientHistoryId;

    @Column(nullable = false)
    private Float temperature;

    @Column(nullable = false)
    private Float bloodPressure;

    @Column(nullable = false, columnDefinition = "nvarchar(254)")
    private String pastMedicalHistory;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean liver;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean diabetes;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean rheumatism;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean nerve;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean allergy;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean digest;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean respiratory;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean cardiovascular;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean kidney;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean other1;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean temporomandibularJoint;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean toothExtraction;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean orthodonticTreatment;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean dentalBraces;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean other2;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = true, columnDefinition = "text")
    private String note;

    @OneToMany(mappedBy = "patientHistory")
    private List<MedicalInformation> medicalInformation;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

}
