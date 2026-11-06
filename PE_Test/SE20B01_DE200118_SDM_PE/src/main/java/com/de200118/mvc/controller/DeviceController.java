package com.de200118.mvc.controller;

import com.de200118.mvc.dto.DeviceDTO;
import com.de200118.mvc.entity.Device;
import com.de200118.mvc.service.CategoryService;
import com.de200118.mvc.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class DeviceController {
    private final DeviceService deviceService;
    private final CategoryService categoryService;
    public DeviceController(DeviceService deviceService, CategoryService categoryService) {
        this.deviceService = deviceService;
        this.categoryService = categoryService;
    }

    private Map<String, String> statusOptions() {
        Map<String, String> statuses = new LinkedHashMap<>();
        statuses.put("AC", "AC-Active");
        statuses.put("IN", "In-Inactive");
        statuses.put("DR", "DR-Draft");
        return statuses;
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("DeviceDTO") DeviceDTO deviceForm,
                       BindingResult bindingResult, Model model) {
        if (deviceForm.getDeviceCode() != null) {
            deviceForm.setDeviceCode(deviceForm.getDeviceCode().trim());
        }
        if (deviceForm.getDeviceName() != null) {
            deviceForm.setDeviceName(deviceForm.getDeviceName().trim());
        }

        deviceService.validataBusinessRules(deviceForm, bindingResult);

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("statuses", statusOptions());

        if (bindingResult.hasErrors()) {
            return "add";
        }

        deviceService.createDevice(deviceForm);

        model.addAttribute("device", deviceForm);
        model.addAttribute("successMessage", "Device added successfully!");
        return "device-add";
    }

    @GetMapping
    public String view (@PathVariable("id") Integer id, Model model) {
        Device device = deviceService.getById(id);
        if (device == null) {
            model.addAttribute("errorMessage", "Device not found");
        } else {
            model.addAttribute("device", device);
        }
        return "device-view";
    }
}
