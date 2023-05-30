package com.dental.controller;

import com.dental.entity.RateStar;
import com.dental.entity.Service;
import com.dental.entity.User;
import com.dental.service.RateStarService;
import com.dental.service.SService;
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
import java.util.List;

@Controller
@RequestMapping()
public class ServiceController {

    @Autowired
    SService serviceService;

    @Autowired
    RateStarService rateStarService;

    @Autowired
    UserService userService;

    @GetMapping("admin/service")
    public String getAll(
            Model model,
            @RequestParam(name = "page", required = false, defaultValue = Const.PAGE_DEFAULT_STR) Integer pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = Const.PAGE_SIZE_DEFAULT_STR) Integer pageSize,
            @RequestParam(name = "titleSearch", required = false) String titleSearch,
            @RequestParam(name = "statusSearch", required = false) String statusSearch
    ) {
        if (pageNum < 1) {
            pageNum = 1;
        }

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        List<Service> services = serviceService.getAll();
        Page<Service> Service;
//        Page<Object[]> findAllServicesWithAverageStar = serviceService.findAllServicesWithAverageStar(pageable);
//        List<Object[]> findAllServicesWithAverageStar = serviceService.findAllServicesWithAverageStar();
//        List<Object[]> servicesWithAVG = findAllServicesWithAverageStar.getContent();

        List<Object[]> servicesWithAVG = rateStarService.findAllWithAvg();

        boolean status = true;
        if (statusSearch != null && statusSearch.equals("0")) {
            status = false;
        }

        if (titleSearch != null && !titleSearch.isEmpty() && statusSearch != null && !statusSearch.isEmpty()){
            Service = serviceService.findAllByStatusAndTitleContaining(status, titleSearch, pageable);
        } else if (statusSearch != null && !statusSearch.isEmpty()) {
            Service = serviceService.findAllByStatus(status, pageable);
        } else if (titleSearch != null && !titleSearch.isEmpty()) {
            Service = serviceService.findAllByTitleContaining(titleSearch, pageable);
        } else {
            Service = serviceService.findAll(pageable);
        }
//        System.out.println(servicesWithAVG);
//        System.out.println(servicesWithAVG.get(0));
//        System.out.println(findAllServicesWithAverageStar);
        model.addAttribute("statusSearch", statusSearch);
        model.addAttribute("usesPage", Service);
        model.addAttribute("numberOfPage", Service.getTotalPages());
        model.addAttribute("services", services);
        model.addAttribute("reviews", servicesWithAVG);

        return "admin/service/services";
    }

    @GetMapping("admin/service/{serviceId}")
    public String getOne(
            @PathVariable("serviceId") int serviceId,
            Model model,
            @RequestParam(name = "page", required = false, defaultValue = Const.PAGE_DEFAULT_STR) Integer pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = Const.PAGE_SIZE_DEFAULT_STR) Integer pageSize
    ) {
        if (pageNum < 1) {
            pageNum = 1;
        }

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Service service = serviceService.get(serviceId);
        List<Service> services = serviceService.getAll();
        Page<RateStar> reviewsByServiceId = rateStarService.getAllByServiceId(serviceId, pageable);
        float avg = 0;
        for (RateStar review : reviewsByServiceId) {
            avg += review.getStar();
        }

        avg = avg / reviewsByServiceId.getContent().size();

        model.addAttribute("service", service);
        model.addAttribute("services", services);
        model.addAttribute("reviews", reviewsByServiceId);
        model.addAttribute("numberOfPage", reviewsByServiceId.getTotalPages());
        model.addAttribute("avg", avg);

        return "admin/service/service-detail";
    }

    @GetMapping("admin/service/service-add")
    public String addServiceForm(Model model) {
        List<Service> services = serviceService.getAll();
        model.addAttribute("service", new Service());
        model.addAttribute("services", services);

        return "admin/service/add-service";
    }

    @PostMapping("admin/service/save")
    public String createService(
            @Valid Service service, BindingResult result,
            @RequestParam("image") MultipartFile multipartFile, Model model
    ) {
        if (multipartFile.isEmpty()) {
            model.addAttribute("image", "Thumbnail must be mandatory");
        }

        if (result.hasErrors()  || multipartFile.isEmpty()) {
            List<Service> services = serviceService.getAll();
            model.addAttribute("services", services);
            return "admin/service/add-service";
        }

        String fileName = UploadFile.getFileName(multipartFile);
        service.setThumbnail(fileName);

        try {
            UploadFile.saveFile(fileName, multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            serviceService.save(service);
            return "redirect:/admin/service";
        } catch (Error e) {
            System.out.println(e);
            return "admin/service/add-service";
        }
    }

    @GetMapping("admin/service/edit/{serviceId}")
    public String editService(@PathVariable("serviceId") int serviceId, Model model) {
        List<Service> services = serviceService.getAll();
        Service service = serviceService.get(serviceId);
        model.addAttribute("service", service);
        model.addAttribute("services", services);
        return "admin/service/update-service";
    }

    @PostMapping("admin/service/update")
    public String updateService(
            @Valid Service service, BindingResult result,
            Model model, @RequestParam(value = "image", required = false) MultipartFile multipartFile
    ) {
        int serviceId = service.getServiceId();

        Service d = serviceService.get(serviceId);
        service.setCreatedAt(d.getCreatedAt());

        if (result.hasErrors()) {
            return "admin/service/update-service";
        }

        if (multipartFile != null) {
            String fileName = UploadFile.getFileName(multipartFile);

            if (!fileName.isEmpty()) {
                service.setThumbnail(fileName);
                try {
                    UploadFile.saveFile(fileName, multipartFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        try {
            serviceService.save(service);
            return "redirect:/admin/service/" + serviceId;
        } catch (Error e) {
            System.out.println(e);
            return "admin/service/update-service";
        }
    }

    @PostMapping("admin/service/delete/{serviceId}")
    public String deleteUser(@PathVariable("serviceId") int serviceId, Model model) throws IllegalAccessException {
        try {
            serviceService.delete(serviceId);
        } catch (Error e) {
            throw new IllegalAccessException("Failed to delete!");
        }
        return "redirect:/admin/service";
    }
}