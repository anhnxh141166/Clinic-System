package com.dental.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Blog {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer blogId;

    @Column(length = 254, nullable = false, columnDefinition = "nvarchar(254)")
    private String title;

    @Column(length = 254, nullable = true)
    private String thumbnail;

    @Column(columnDefinition = "nvarchar(255)" , nullable = false)
    private String summary;

    @Column(nullable = true, columnDefinition = "text")
    private String content;

    @Column(length = 1, nullable = false, columnDefinition = "bit default 1")
    private boolean status;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
    private List<CommentBlog> commentBlog;
}
