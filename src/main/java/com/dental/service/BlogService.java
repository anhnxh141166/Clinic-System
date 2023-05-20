package com.dental.service;

import com.dental.entity.Blog;
import com.dental.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    BlogRepository blogRepository;

    public List<Blog> getAll(){
        return blogRepository.findAll();
    }

    public void createBlog(Blog blog) {
        blogRepository.save(blog);
    }
}
