package com.dental.controller;

import com.dental.entity.User;
import com.dental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
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

    @GetMapping()
    public String viewListPlan(Model theModel){
        // get employees from db
        List<User> user = userService.getAllUser();

        // add to the spring model
        theModel.addAttribute("user", user);

        return "/user/list-user";
    }
    @PostMapping()
    public void registerUser(@ModelAttribute("") User user) {
        userService.addUser(user);
    }

}
