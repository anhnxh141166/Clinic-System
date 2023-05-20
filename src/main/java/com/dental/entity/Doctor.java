package com.dental.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Doctor {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctorId;

    @Column(nullable = false, columnDefinition = "nvarchar(8)")
    private String gender;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false, columnDefinition = "nvarchar(100)")
    private String address;

    @Column(nullable = true, length = 12)
    private String phoneNumber;

    @Column(nullable = false, columnDefinition = "text")
    private String avatar;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "doctor")
    private List<MedicalInformation> medicalInformation;

    @ManyToMany
    @JoinTable(
            name = "doctor_service",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    Set<Service> service;

    @OneToOne
    @MapsId
    @JoinColumn(name = "doctor_id")
    private User user;
}
