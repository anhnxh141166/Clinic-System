package com.dental.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class CommentBlog {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    @Column(nullable = false, columnDefinition = "nvarchar(254)")
    private String description;
    @Column(nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
