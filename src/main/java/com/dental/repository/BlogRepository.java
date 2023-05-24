package com.dental.repository;

import com.dental.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
    List<Blog> findByOrderByCreatedAtDesc();
    Page<Blog> findAll(Pageable pageable);

    Page<Blog> findAllByStatusAndTitleContaining(boolean status, String title, Pageable pageable);

    Page<Blog> findAllByTitleContaining(String title, Pageable pageable);

    Page<Blog> findAllByUser(Integer userId, Pageable pageable);

    Page<Blog> findAllByStatus(boolean status, Pageable pageable);
}
