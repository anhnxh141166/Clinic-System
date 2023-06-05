package com.dental.controller;

import com.dental.entity.User;
import com.dental.entity.UserDetailsImpl;
import com.dental.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/homeLanding")
    public String viewHomeLandingPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        User userEnity = null;
        if (userDetails != null) {
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


    @GetMapping("/error")
    public String viewTest(Model model) {
        User user = new User();
        user.setFullName("Nguyen Van B");
//        model.addAttribute("listUser",user);
        return "landing/error";

    }
// test link: http://localhost:8888/user/homeAdmin

    @GetMapping()
    public String viewListPlan(Model theModel) {
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

    @GetMapping("/register")
    public String showRegister(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "landing/auth/signup";
    }

    //    @PostMapping("/register/save")
//    public String registration(@ModelAttribute("user") User user,
//                               BindingResult result,
//                               Model model) {
//        System.out.println(user);
//        if(user==null){
//            return "landing/auth/signup";
//        }else {
//            userService.registerUser(user);
//        }
//        return "redirect:/login";
//    }
    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "landing/auth/signup";
        }

        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null) {
            model.addAttribute("emailError", "Email đã được sử dụng.");
            return "landing/auth/signup";
//            return "redirect:/register";
        }else {
            userService.registerUser(user);
            return "redirect:/login";
        }

    }

    @RequestMapping(value = "/checkEmailExists", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> checkEmailExists(@RequestParam String email) {
        Map<String, Object> response = new HashMap<>();
        boolean exists = userService.checkEmail(email);
        response.put("exists", exists);
        return response;
    }

}
