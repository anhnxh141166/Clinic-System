package com.dental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
   @Size(min = 4, message = "Email must be mandatory")
    @Email(message = "This field must be an email")
//    @NotBlank(message = "Email không được để trống")
//    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email không đúng định dạng")
    private String email;

    @Column(length = 254, nullable = false)
//    @Size(min = 6, max=20, message = "Password must be mandatory")
//    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @Column(columnDefinition = "nvarchar(50)" , nullable = false)
    @Size(min = 3, max = 30, message = "Full name must be mandatory")
//    @NotBlank(message = "Tên không được để trống")
//    @Pattern(regexp = "^[a-zA-Z\\s]{4,30}$\n", message = "Tên chỉ được nhập kí tự không được nhập số và kí tự đặc biệt")
    private String fullName;

    @Column(length = 1, nullable = false, columnDefinition = "bit default 1")
    private boolean status;

    @Column(length = 20, nullable = false, columnDefinition = "nvarchar(20) default 'Patient'")
    @Pattern(regexp = "Admin|Patient|Doctor|Staff", message = "Role undefined")
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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", status=" + status +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                ", captcha='" + captcha + '\'' +
                ", captchaExpire=" + captchaExpire +
                '}';
    }
}
