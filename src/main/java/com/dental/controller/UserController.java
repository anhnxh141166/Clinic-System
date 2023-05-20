package com.dental.controller;

import com.dental.entity.User;
import com.dental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/homeLanding")
    public String viewHomeLandingPage(Model model) {
        User user = new User();
        user.setFullName("Nguyen Van A");
//        model.addAttribute("listUser",user);
        return "landing/index";
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


}
