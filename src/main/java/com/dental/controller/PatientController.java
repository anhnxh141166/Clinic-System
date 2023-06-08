package com.dental.controller;

import com.dental.entity.User;
import com.dental.entity.UserDetailsImpl;
import com.dental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("patient")
public class PatientController {

    @Autowired
    UserService userService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String viewProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("user", userDetails.getUserEntity());
        return "landing/patient/patient-profile";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {
        String url = "landing/patient/patient-profile";
        User userEntity = userDetails.getUserEntity();
        String message = "";

        //login success
        if (userEntity != null) {
            // password correct
            if (passwordEncoder.matches(currentPassword, userEntity.getPassword())) {
                //current Password not same new password
                if (!currentPassword.equals(newPassword)) {
                    //confirm password
                    if (newPassword.equals(confirmPassword)) {
                        userEntity = userService.update(userEntity, newPassword);
                        userDetails.setUserEntity(userEntity);
                    } else {
                        message = "Confirm password not same !!!";
                    }
                } else {
                    message = "Please enter new password different old password!!!";
                }
            } else {
                message = "Wrong password !";
            }
        } else {
            message = "Please login !!!";
        }

        model.addAttribute("message", message);
        model.addAttribute("user", userEntity);
        return url;
    }

//    @GetMapping("")
//    public String getAll() {
//        return "";
//    }
//
//    @GetMapping("{}")
//    public String getOne() {
//        return "";
//    }
//
//    @GetMapping("{}")
//    public String detailPatient() {
//        return "";
//    }
//
//    public String updatePatient(){
//        return "";
//    }

}
