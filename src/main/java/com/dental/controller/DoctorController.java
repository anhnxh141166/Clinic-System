package com.dental.controller;

import com.dental.entity.Doctor;
import com.dental.entity.User;
import com.dental.service.DoctorService;
import com.dental.service.UserService;
import com.dental.util.Const;
import com.dental.util.UploadFile;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("admin/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @Autowired
    UserService userService;
// RequestMapping : gửi yêu cầu lên 1 đường dẫn nào đấy
//   Autowired:để sử dụng được các hàm  sevice như kiểu khai báo class
//    GetMapping: đẩy dữ liệu lên html
    @GetMapping()
//hàm getAll ,RequestParam:lấy những giá trị ở  html về để sử lí để đẩy dữ liệu lên trang
//    lấy dữ liệu về rồi ,mình xử lí dữ liệu VD:search gender, xog vẫn vẫn đẩy lên đường dẫnđấy và hiển th thông tin bác sĩ
//    là hàm search và phân trang
// Model model là 1 cái hàm để đẩy dữ liệu lên trang
    public String getAll(
            Model model,
            @RequestParam(name = "page", required = false, defaultValue = Const.PAGE_DEFAULT_STR) Integer pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = Const.PAGE_SIZE_DEFAULT_STR) Integer pageSize,
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestParam(name = "statusSearch", required = false) String statusSearch,
            @RequestParam(name = "gender", required = false) String gender
    ) {

        if (pageNum < 1) {
            pageNum = 1;
        }

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        List<Doctor> doctors = doctorService.getAll();
        Page<Doctor> Doctor;

        boolean status = true;
        if (statusSearch != null && statusSearch.equals("0")) {
            status = false;
        }
//3 thang k ton tai
        if (fullName != null && !fullName.isEmpty() && statusSearch != null && !statusSearch.isEmpty() && gender != null && !gender.isEmpty()){
            Doctor = doctorService.findAllByUserStatusAndUserFullNameAndGender(status, fullName, gender, pageable);
        } else if (gender != null && !gender.isEmpty() && fullName != null && !fullName.isEmpty()){
            Doctor = doctorService.findAllByUserFullNameAndGender(fullName, gender, pageable);
        } else if (gender != null && !gender.isEmpty() && statusSearch != null && !statusSearch.isEmpty()){
            Doctor = doctorService.findAllByUserStatusAndGender(status, gender, pageable);
        } else if (fullName != null && !fullName.isEmpty() && statusSearch != null && !statusSearch.isEmpty()){
            Doctor = doctorService.findAllByUserStatusAndUserFullName(status, fullName, pageable);
        } else if (statusSearch != null && !statusSearch.isEmpty()) {
            Doctor = doctorService.findAllByUserStatus(status, pageable);
        } else if (fullName != null && !fullName.isEmpty()) {
            Doctor = doctorService.findAllByUserFullName(fullName, pageable);
        } else if (gender != null && !gender.isEmpty()) {
            Doctor = doctorService.findAllByGender(gender, pageable);
        } else {
            Doctor = doctorService.findAll(pageable);
        }
         // đẩy liệu lên ::vd nguyễn hữu thùy
        model.addAttribute("fullName", fullName);
        model.addAttribute("statusSearch", statusSearch);
        model.addAttribute("usesPage", Doctor);
        model.addAttribute("numberOfPage", Doctor.getTotalPages());
        model.addAttribute("doctors", doctors);

        return "admin/doctor/doctors";
    }

    @GetMapping("{doctorId}")
    //    GetMapping: đẩy dữ liệu lên html
//     đẩy dữ lệu lên trang dr-profile theo doctor_id
    public String getOne(@PathVariable("doctorId") int doctorId, Model model) {
        Doctor doctor = doctorService.get(doctorId);
        List<Doctor> doctors = doctorService.getAll();
        model.addAttribute("doctor", doctor);
        model.addAttribute("doctors", doctors);

        return "admin/doctor/dr-profile";
    }

    @GetMapping("doctor-add")
    //    GetMapping: đẩy dữ liệu lên html
//đẩy dữ liệu sang trang add-doctor
    public String addDoctorForm(Model model) {
        List<Doctor> doctors = doctorService.getAll();
        model.addAttribute("user", new User());
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("doctors", doctors);

        return "admin/doctor/add-doctor";
    }

    @PostMapping("/save")
//  PostMapping: tạo thêm ms doctor  add doctor
    // add thêm các doctor
//    Valid: kiểm tra các giá trị của đối tượng  và add
    public String createDoctor(
            @Valid Doctor doctor, BindingResult doctorBindingResult,
            @Valid User user, BindingResult userBindingResult,
            @RequestParam("image") MultipartFile multipartFile, Model model
    ) {
        User u = userService.getByEmail(user.getEmail());

        if (u != null) {
            model.addAttribute("email", "Email already used");
        }

        if (multipartFile.isEmpty()) {
            model.addAttribute("image", "Avatar must be mandatory");
        }

        if (doctor.getDateOfBirth() == null) {
            model.addAttribute("dateOfBirth", "Date of birth must be mandatory");
        }

        if (doctor.getGender() == null) {
            model.addAttribute("gender", "Gender must be mandatory");
        }

        if (doctorBindingResult.hasErrors() || userBindingResult.hasErrors() || multipartFile.isEmpty() || u != null) {
            List<Doctor> doctors = doctorService.getAll();
            model.addAttribute("doctors", doctors);
            return "admin/doctor/add-doctor";
        }

        String fileName = UploadFile.getFileName(multipartFile);
        doctor.setAvatar(fileName);

        user.setPassword("123456789");
        user.setRole("Doctor");

        try {
            UploadFile.saveFile(fileName, multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            userService.save(user);
            User u2 = userService.getByEmail(user.getEmail());
            doctor.setUser(u2);
            if (u2 != null) {
                doctorService.save(doctor);
            }

            return "redirect:/admin/doctor";
        } catch (Error e) {
            System.out.println(e);
            return "admin/doctor/add-doctor";
        }
    }

    @GetMapping("/edit/{doctorId}")
    //    GetMapping: đẩy dữ liệu lên update-doctor
    public String editDoctor(@PathVariable("doctorId") int doctorId, Model model) {
        List<Doctor> doctors = doctorService.getAll();
        Doctor doctor = doctorService.get(doctorId);
        User user = userService.get(doctor.getUser().getUserId());
        model.addAttribute("doctor", doctor);
        model.addAttribute("user", user);
        model.addAttribute("doctors", doctors);
        return "admin/doctor/update-doctor";
    }

    //  PostMapping: tạo thêm ms doctor  add doctor
    //    Valid: kiểm tra các giá trị của đối tượng  và add
    @PostMapping("/update")
    public String updateDoctor(
        @Valid Doctor doctor, BindingResult doctorBindingResult,
        @Valid User user, BindingResult userBindingResult,
        Model model, @RequestParam(value = "image", required = false) MultipartFile multipartFile
    ) {
        int doctorId = doctor.getDoctorId();

        Doctor d = doctorService.get(doctorId);
        doctor.setUser(d.getUser());

        if (doctorBindingResult.hasErrors() || userBindingResult.hasErrors()) {
            return "admin/doctor/update-doctor";
        }

        if (multipartFile != null) {
            String fileName = UploadFile.getFileName(multipartFile);

            if (!fileName.isEmpty()) {
                doctor.setAvatar(fileName);
                try {
                    UploadFile.saveFile(fileName, multipartFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        try {
            userService.updateUser(user.getFullName(), user.isStatus(), d.getUser().getUserId());
            doctorService.save(doctor);
            return "redirect:/admin/doctor/" + doctorId;
        } catch (Error e) {
            System.out.println(e);
            return "admin/doctor/update-doctor";
        }
    }

    @PostMapping("/delete/{doctorId}")
    public String deleteUser(@PathVariable("doctorId") int doctorId, Model model) throws IllegalAccessException {
        try {
            Doctor u = doctorService.get(doctorId);
            doctorService.delete(doctorId);
            userService.delete(u.getUser().getUserId());
        } catch (Error e) {
            throw new IllegalAccessException("Failed to delete!");
        }
        return "redirect:/admin/doctor";
    }
}
