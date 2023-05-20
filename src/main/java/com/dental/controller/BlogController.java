package com.dental.controller;

import com.dental.entity.Blog;
import com.dental.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping()
    public String getAll(Model model) {
        List<Blog> blogs = blogService.getAll();
//        model.addAttribute("blogs", blogs);
        model.addAttribute("blogs", "abcd");
        return "admin/blog/blogs";
    }


    @PostMapping()
    public String createBlog(@ModelAttribute("blog") Blog blog, BindingResult result, Model model) {
        System.out.println("result here");
        System.out.println(result);
        System.out.println("model here");
        System.out.println(model);
        if (result.hasErrors()) {
            return "admin/blog/blogs";
        }
        blogService.createBlog(blog);
        return "redirect:admin/blog/blogs";
    }

    public String editBlog(@PathVariable int id, Model m) {
        return "admin/blog/blogs";
    }
}
