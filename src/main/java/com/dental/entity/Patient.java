package com.dental.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Entity
@Data
public class Patient {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patientId;
    @Column(nullable = false, columnDefinition = "nvarchar(100)")
    private String address;
    @Column(nullable = false)
    private Date dateOfBirth;
    @Column(nullable = false, columnDefinition = "nvarchar(8)")
    private String gender;
    @Column(nullable = false, length = 12)
    private String phoneNumber;
    @Column(nullable = false, columnDefinition = "text")
    private String avatar;
    @Column(nullable = false, columnDefinition = "varchar(5)")
    private String bloodGroup;

    @OneToMany(mappedBy = "patient")
    private List<MedicalInformation> medicalInformation;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointment;

    @OneToMany(mappedBy = "patient")
    private List<PatientHistory> patientHistory;

    @OneToOne
    @MapsId
    @JoinColumn(name = "patient_id")
    private User user;

}
