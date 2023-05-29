package com.dental.controller;


import com.dental.entity.User;
import com.dental.entity.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/redirect")
    public String redirect(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        System.out.println(new BCryptPasswordEncoder().matches("a8888888", "$2a$12$6MkbL7umkJ3s4zcR8flDA.nca35mKYWf02d3/vMOW0w89x7EwwTAW"));
        User userEnity = userDetails.getUserEntity();
        if (userEnity!= null){
            if (userEnity.getRole().equals("Admin")){
                return "redirect:/admin/blog";
            } else {
                return "redirect:/user/homeLanding";
            }
        }
        return "landing/auth/login"; // Redirect to login page if role not found
    }

    @GetMapping("/access-denied")
    public String deniedAccess(){
        return "landing/error";
    }

}
