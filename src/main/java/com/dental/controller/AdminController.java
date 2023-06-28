package com.dental.controller;

import com.dental.entity.User;
import com.dental.service.AppointmentService;
import com.dental.service.BlogService;
import com.dental.service.DoctorService;
import com.dental.service.RateStarService;
import com.dental.service.SService;
import com.dental.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Controller
public class AdminController {

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

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    SService sService;

    @GetMapping("/admin")
    public String viewHomeAdminPage(Model model) {
        User user = new User();
        int numberPatient = appointmentService.countAllPatientBooking();
        double revenue = appointmentService.getTotalRevenue();
        int numberEmployeeActive = userService.countEmployeeActive(true);
        int numberServices = sService.countNumberServices();
        int numberAppointments = appointmentService.countAppointments();


        model.addAttribute("numberPatient", numberPatient);
        model.addAttribute("revenue", revenue);
        model.addAttribute("numberEmployeeActive", numberEmployeeActive);
        model.addAttribute("numberServices", numberServices);
        model.addAttribute("numberAppointments", numberAppointments);


        return "admin/index";
    }


    @GetMapping("/chart-data")
    @ResponseBody
    public ChartDataResponse getChartData(@RequestParam("option") String option, @RequestParam("type") String type) {
        ChartDataResponse response = new ChartDataResponse();
        double[] data;
        String[] categories1 = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String[] categories2 = {
                "Day 1",
                "Day 2",
                "Day 3",
                "Day 4",
                "Day 5",
                "Day 6",
                "Day 7",
                "Day 8",
                "Day 9",
                "Day 10",
                "Day 11",
                "Day 12",
                "Day 13",
                "Day 14",
                "Day 15",
                "Day 16",
                "Day 17",
                "Day 18",
                "Day 19",
                "Day 20",
                "Day 21",
                "Day 22",
                "Day 23",
                "Day 24",
                "Day 25",
                "Day 26",
                "Day 27",
                "Day 28",
                "Day 29",
                "Day 30",
                "Day 31",
        };
        if (type.equals("chart1")){
            double revenueCurrent;
            if (option.equals("month")) {
                data = new double[13];
                for (int month = 1; month <= 12; month++) {
                    revenueCurrent = appointmentService.getRevenueByMonth(month);
                    System.out.println("testchart");
                    System.out.println(revenueCurrent);
                    data[month] = revenueCurrent;
                }
                response.setData(data);
                response.setCategories(categories1);
            } else if (option.equals("week")) {
                data = new double[31];
                for (int day = 1; day <= 30; day++) {
                    revenueCurrent = appointmentService.getRevenueByDay(day);
                    System.out.println("testchart");
                    System.out.println(revenueCurrent);
                    data[day] = revenueCurrent;
                }
                response.setData(data);
                response.setCategories(categories2);
            } else {
                throw new IllegalArgumentException("Invalid option: " + option);
            }

            if (data == null) {
                data = new double[0];
            }
        }else{
            double appointmentNumber = 0;
            if (option.equals("month")){
                data = new double[13];
                for (int month = 1; month <= 12; month++) {
                    appointmentNumber = appointmentService.countAppointmentByMonth(month);
                    data[month] = appointmentNumber;
                }
                response.setData(data);
                response.setCategories(categories1);
            } else if (option.equals("week")) {
                data = new double[32];
                for (int day = 1; day <= 31; day++) {
                    appointmentNumber = appointmentService.countAppointmentByDay(day);
                    data[day] = appointmentNumber;
                }
                response.setData(data);
                response.setCategories(categories2);
            }else {
                throw new IllegalArgumentException("Invalid option: " + option);
            }
        }



        return response;
    }

    static class ChartDataResponse {
        private double[] data;
        private String[] categories;

        // Getters and setters

        public double[] getData() {
            return data;
        }

        public void setData(double[] data) {
            this.data = data;
        }

        public String[] getCategories() {
            return categories;
        }

        public void setCategories(String[] categories) {
            this.categories = categories;
        }
    }
}
