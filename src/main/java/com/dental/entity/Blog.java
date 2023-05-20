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

    public Blog(String title, String thumbnail, String summary, String content, boolean status) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.summary = summary;
        this.content = content;
        this.status = status;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CommentBlog> getCommentBlog() {
        return commentBlog;
    }

    public void setCommentBlog(List<CommentBlog> commentBlog) {
        this.commentBlog = commentBlog;
    }
}
