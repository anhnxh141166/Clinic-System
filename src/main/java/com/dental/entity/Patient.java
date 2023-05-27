package com.dental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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

    @Column(nullable = true, columnDefinition = "nvarchar(100)")
    private String address;

    @Column(nullable = true)
    private Date dateOfBirth;

    @Column(nullable = true, columnDefinition = "nvarchar(8)")
    private String gender;

    @Column(nullable = false, length = 12)
    @Pattern(regexp = "^(?:\\+)?[0-9]*$", message = "Wrong type of phone number")
    private String phoneNumber;

    @Column(nullable = true, columnDefinition = "text")
    private String avatar;

    @Column(nullable = true, columnDefinition = "varchar(5)")
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
