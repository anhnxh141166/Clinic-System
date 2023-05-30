package com.dental.controller;

import com.dental.entity.*;
import com.dental.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping()
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/comment/save")
    public String createComment(@Valid CommentBlog comment, BindingResult result, Model model, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("description", "Comment is mandatory");
            return "redirect:/blog/" + comment.getBlog().getBlogId();
        }

        try {
            commentService.save(comment);
            return "redirect:/blog/" + comment.getBlog().getBlogId();
        } catch (Error e) {
            redirectAttrs.addFlashAttribute("description", e);
            return "redirect:/blog/" + comment.getBlog().getBlogId();

        }
    }

//    @PostMapping("admin/blog/delete/{blogId}")
//    public String deleteUser(@PathVariable("blogId") int blogId, Model model) throws IllegalAccessException {
//        try {
//            blogService.get(blogId);
//            blogService.delete(blogId);
//        } catch (Error e) {
//            throw new IllegalAccessException("Failed to delete!");
//        }
//        return "redirect:/admin/blog";
//    }
}
