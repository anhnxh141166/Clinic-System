package com.dental.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Service {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceId;

    @Column(nullable = false, columnDefinition = "nvarchar(100)")
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(nullable = false, columnDefinition = "text")
    private String thumbnail;

    @Column(nullable = false, length = 1, columnDefinition = "BIT(1) default 1")
    private boolean status;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Date createdAt;

    @ManyToMany(mappedBy = "service")
    private Set<Doctor> doctor;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<MedicalInformation> medicalInformation;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<RateStar> rateStar;

    @ManyToMany
    @JoinTable(
            name = "appointment_detail",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_id"))
    Set<Appointment> appointment;


}
