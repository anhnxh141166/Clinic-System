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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private HttpSession session;

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
        if (userDetails != null) {
            userEnity = userDetails.getUserEntity();
        }
        List<Service> top4Service = getTop4Service();
        List<Doctor> doctors = doctorService.getAll();
        List<Blog> blogs = blogService.getAll();

        List<RateStar> rateStars = rateStarService.getAll();
        List<RateStar> rateStarsTop5 = rateStarService.findTop5ByStarGreaterThanOrderByStarDesc(3);
        List<Object[]> servicesWithAVG = rateStarService.findTop4WithAvg();


        model.addAttribute("user", userEnity);
        model.addAttribute("top4Service", top4Service);
        model.addAttribute("doctors", doctors);
        model.addAttribute("blogs", blogs);
        model.addAttribute("rateStars", rateStars);
        model.addAttribute("rateStarsTop5", rateStarsTop5);
        model.addAttribute("servicesWithAVG", servicesWithAVG);
        return "landing/index";
//        return "landing/index-two";
// test link: http://localhost:8888/user/homeLanding

    }

    public List<Service> getTop4Service() {
        List<Service> top4Service = new ArrayList<>();
        int count = 0;
        List<Object[]> servicesWithAVG = rateStarService.findTop4WithAvg();
        for (Object[] serviceWithAVG : servicesWithAVG) {
            if (count < 4) {
                int id = (Integer) serviceWithAVG[0];
                Service service = serviceService.get(id);
                top4Service.add(service);
            }
            count++;
        }
        return top4Service;
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
        String message = "";
        if (user == null) {
            message = "Register fail";
        } else {
            userService.registerUser(user);
            message = "Register successful";
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

    @PostMapping("/change-password")
    public String changePassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model,
            RedirectAttributes redirectAttributes) {
        User userEntity = userDetails.getUserEntity();
        String message = "";

        // Check if user is logged in
        if (userEntity != null) {
            // Check if current password is correct
            if (passwordEncoder.matches(currentPassword, userEntity.getPassword())) {
                // Check if new password is different from the current password
                if (!currentPassword.equals(newPassword)) {
                    // Check if new password matches the confirm password
                    if (newPassword.equals(confirmPassword)) {
                        // Update the user's password
                        userEntity = userService.update(userEntity, newPassword);
                        userDetails.setUserEntity(userEntity);
//                        redirectAttributes.addFlashAttribute("passwordMessage", "Change password success");
                        model.addAttribute("passwordMessage", "Change password success");
                        return "redirect:/profile";
                    } else {
                        message = "Confirm password does not match!";
                    }
                } else {
                    message = "Please enter a new password different from the old password!";
                }
            } else {
                message = "Wrong password!";
            }
        } else {
            message = "Please login!";
        }

        redirectAttributes.addFlashAttribute("message", message);
        model.addAttribute("message", message);
        return "redirect:/profile";
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
            session.setAttribute("user", userService.get(user.getUserId()));
            return "redirect:/profile";
        } catch (Error e) {
            System.out.println(e);
            return "landing/user/profile";
        }
    }
}
