package com.dental.controller;

import com.dental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

//    @GetMapping("/list")
//    public String viewListPlan(Authentication authentication, Model model){
//        String username= authentication.getName();
//        Users users=userService.getUserByUsername(username);
//        model.addAttribute("users", users);
//        model.addAttribute("listPlans", planService.getAllPlan());
//        return "admin/listPlans";
//    }
}
