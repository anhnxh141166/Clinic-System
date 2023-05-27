package com.dental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;
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
    @Size(min = 1, message = "Title must be mandatory")
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    @Size(min = 1, message = "Description must be mandatory")
    private String description;

    @Column(nullable = false, columnDefinition = "text")
    @Size(min = 1, message = "Content must be mandatory")
    private String content;

    @Column(nullable = false, columnDefinition = "text")
    private String thumbnail;

    @Column(nullable = false, length = 1, columnDefinition = "BIT(1) default 1")
    @NotNull(message = "Status must be mandatory")
    private boolean status;

    @Column(nullable = false)
    @NotNull(message = "Price must be mandatory")
    @Min(value = 1, message = "Price must be a number")
    private Double price;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

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

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Doctor> getDoctor() {
        return doctor;
    }

    public void setDoctor(Set<Doctor> doctor) {
        this.doctor = doctor;
    }

    public List<MedicalInformation> getMedicalInformation() {
        return medicalInformation;
    }

    public void setMedicalInformation(List<MedicalInformation> medicalInformation) {
        this.medicalInformation = medicalInformation;
    }

    public List<RateStar> getRateStar() {
        return rateStar;
    }

    public void setRateStar(List<RateStar> rateStar) {
        this.rateStar = rateStar;
    }

    public Set<Appointment> getAppointment() {
        return appointment;
    }

    public void setAppointment(Set<Appointment> appointment) {
        this.appointment = appointment;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceId=" + serviceId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", status=" + status +
                ", price=" + price +
                ", doctor=" + doctor.size() +
                ", medicalInformation=" + medicalInformation.size() +
                ", rateStar=" + rateStar.size() +
                ", appointment=" + appointment.size() +
                '}';
    }
}
