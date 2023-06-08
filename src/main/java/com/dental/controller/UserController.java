package com.dental.controller;

import com.dental.entity.Blog;
import com.dental.entity.Doctor;
import com.dental.entity.Patient;
import com.dental.entity.RateStar;
import com.dental.entity.Service;
import com.dental.entity.User;
import com.dental.entity.UserDetailsImpl;
import com.dental.service.BlogService;
import com.dental.service.DoctorService;
import com.dental.service.RateStarService;
import com.dental.service.SService;
import com.dental.service.UserService;
import com.dental.util.UploadFile;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping()
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    BlogService blogService;

    @Autowired
    SService serviceService;

    @Autowired
    RateStarService rateStarService;

    @GetMapping("/")
    public String viewHomeLandingPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        User userEnity = null;
        if (userDetails != null){
            userEnity = userDetails.getUserEntity();
        }
        List<Service> services = serviceService.getAll();
        List<Doctor> doctors = doctorService.getAll();
        List<Blog> blogs = blogService.getAll();

        List<RateStar> rateStars = rateStarService.getAll();
        List<RateStar> rateStarsTop5 = rateStarService.findTop5ByStarGreaterThanOrderByStarDesc(3);
        List<Object[]> servicesWithAVG = rateStarService.findAllWithAvg();


        model.addAttribute("user", userEnity);
        model.addAttribute("services", services);
        model.addAttribute("doctors", doctors);
        model.addAttribute("blogs", blogs);
        model.addAttribute("rateStars", rateStars);
        model.addAttribute("rateStarsTop5", rateStarsTop5);
        model.addAttribute("servicesWithAVG", servicesWithAVG);
        return "landing/index";
//        return "landing/index-two";
// test link: http://localhost:8888/user/homeLanding

    }






    @GetMapping("/admin")
    public String viewHomeAdminPage(Model model) {
        User user = new User();
        user.setFullName("Nguyen Van B");
        return "admin/index";
    }

    @GetMapping("/error")
    public String viewTest(Model model) {
        User user = new User();
        user.setFullName("Nguyen Van B");
        return "landing/error";
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

    @PostMapping("/register/save")
    public String registration(@ModelAttribute("user") User user,
                               BindingResult result,
                               Model model) {
        System.out.println(user);
        if(user==null){
            return "landing/auth/signup";
        }else {
            userService.registerUser(user);
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/checkEmailExists", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> checkEmailExists(@RequestParam String email) {
        Map<String, Object> response = new HashMap<>();
        boolean exists = userService.checkEmail(email);
        response.put("exists", exists);
        return response;
    }

    @GetMapping("/profile")
    public String profile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Model model
    ) {
        String role = userDetails.getUserEntity().getRole();
        if (role.equals("Doctor")) {
            model.addAttribute("doctor", doctorService.get(userDetails.getUserEntity().getUserId()));
        }

        model.addAttribute("user", userService.get(userDetails.getUserEntity().getUserId()));
        return "landing/user/profile";
    }

    @PostMapping("/profile/change-password")
    public String changePassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model)
    {
        String url = "landing/user/profile";
        User userEntity = userDetails.getUserEntity();
        String message = "";

        //login success
        if (userEntity != null){
            // password correct
            if (passwordEncoder.matches(currentPassword,userEntity.getPassword())){
                //current Password not same new password
                if (!currentPassword.equals(newPassword)){
                    //confirm password
                    if (newPassword.equals(confirmPassword)){
                        userEntity = userService.update(userEntity, newPassword);
                        userDetails.setUserEntity(userEntity);
                    }else{
                        message = "Confirm password not match!!!";
                    }
                }else {
                    message = "Please enter new password different old password!!!";
                }
            }else{
                message = "Wrong password !";
            }
        }else{
            message = "Please login !!!";
        }

        model.addAttribute("message", message);
        model.addAttribute("user",userEntity);
        return url;
    }

    @PostMapping("profile/update")
    public String updateDoctor(
            @Valid Doctor doctor, BindingResult doctorBindingResult,
            @Valid User user, BindingResult userBindingResult,
            Model model, @RequestParam(value = "image", required = false) MultipartFile multipartFile
    ) {
        int doctorId = user.getUserId();

        User u = userService.get(doctorId);
        user.setDoctor(u.getDoctor());

        user.setEmail(u.getEmail());
        user.setPassword(u.getPassword());
        user.setRole(u.getRole());
        user.setCreatedAt(u.getCreatedAt());

        if (user.getDateOfBirth() == null) {
            model.addAttribute("dateOfBirth", "Date of birth must be mandatory");
        }

        if (user.getGender() == null) {
            model.addAttribute("gender", "Gender must be mandatory");
        }

        if (multipartFile != null) {
            String fileName = UploadFile.getFileName(multipartFile);

            if (!fileName.isEmpty()) {
                user.setAvatar(fileName);
                try {
                    UploadFile.saveFile(fileName, multipartFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            user.setAvatar(u.getAvatar());
        }

        if (doctorBindingResult.hasErrors() || userBindingResult.hasErrors()) {
            return "landing/user/profile";
        }

        try {
            if (user.getRole().equals("Doctor")) {
                doctorService.updateDoctor(doctor.getDescription(), doctorId);
            }

            userService.save(user);
            return "redirect:/profile";
        } catch (Error e) {
            System.out.println(e);
            return "landing/user/profile";
        }
    }
}
