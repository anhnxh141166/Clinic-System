package com.dental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, message = "Gender must be mandatory")
    private String gender;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false, columnDefinition = "nvarchar(100)")
    @Size(min = 1, message = "Address must be mandatory")
    private String address;

    @Column(nullable = true, length = 12)
    @Size(min = 1, message = "Phone must be mandatory")
    private String phoneNumber;

    @Column(nullable = false, columnDefinition = "text")
    private String avatar;

    @Column(nullable = false, columnDefinition = "text")
    @Size(min = 1, message = "Bio must be mandatory")
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

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId=" + doctorId +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", avatar='" + avatar + '\'' +
                ", description='" + description + '\'' +
                ", medicalInformation=" + medicalInformation.size() +
                ", service=" + service.size() +
                ", user=" + user.getUserId() +
                '}';
    }
}
