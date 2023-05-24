package com.dental.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(length = 254, nullable = false, unique = true)
    private String email;

    @Column(length = 254, nullable = false)
    private String password;

    @Column(columnDefinition = "nvarchar(50)" , nullable = false)
    private String fullName;

    @Column(length = 1, nullable = false, columnDefinition = "bit default 1")
    private boolean status;

    @Column(length = 20, nullable = false, columnDefinition = "nvarchar(20) default 'Patient'")
    private String role;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(length = 6, nullable = true)
    private String captcha;

    @Column(nullable = true)
    private Date captchaExpire;

    @OneToMany(mappedBy = "user")
    private List<Blog> blog;

    @OneToMany(mappedBy = "user")
    private List<CommentBlog> commentBlog;

    @OneToMany(mappedBy = "user")
    private List<RateStar> rateStar;

    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Doctor doctor;

    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Patient patient;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
