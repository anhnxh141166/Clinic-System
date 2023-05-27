package com.dental.controller;

import com.dental.entity.UserDetailsImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
//@RequestMapping("/login")
public class LoginController {

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {
        // lỗi many request khi dùng @RequestParam, @RequestVariable
        if (userDetails != null){
            return "redirect:/user/homeLanding";
        }else {
            model.addAttribute("success", request.getParameter("success"));
        }
            return "landing/auth/login";
    }



}
