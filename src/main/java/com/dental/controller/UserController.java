package com.dental.controller;

import com.dental.entity.User;
import com.dental.entity.UserDetailsImpl;
import com.dental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/homeLanding")
    public String viewHomeLandingPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        User userEnity = null;
        if (userDetails != null){
            userEnity = userDetails.getUserEntity();
        }

        model.addAttribute("user", userEnity);
        return "landing/index";
//        return "landing/index-two";
// test link: http://localhost:8888/user/homeLanding

    }


    @GetMapping("/homeAdmin")
    public String viewHomeAdminPage(Model model) {
        User user = new User();
        user.setFullName("Nguyen Van B");
//        model.addAttribute("listUser",user);
        return "admin/index";

// test link: http://localhost:8888/user/homeAdmin
    }


//    @GetMapping("/list")
//    public String viewListPlan(Authentication authentication, Model model){
//        String username= authentication.getName();
//        Users users=userService.getUserByUsername(username);
//        model.addAttribute("users", users);
//        model.addAttribute("listPlans", planService.getAllPlan());
//        return "admin/listPlans";
//    }

    @GetMapping("/error")
    public String viewTest(Model model) {
        User user = new User();
        user.setFullName("Nguyen Van B");
//        model.addAttribute("listUser",user);
        return "landing/error";

    }
// test link: http://localhost:8888/user/homeAdmin

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
