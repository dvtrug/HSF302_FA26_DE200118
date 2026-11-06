package com.de200118.mvc.service.impl;

import com.de200118.mvc.dto.DeviceDTO;
import com.de200118.mvc.entity.Category;
import com.de200118.mvc.entity.Device;
import com.de200118.mvc.repository.CategoryRepository;
import com.de200118.mvc.repository.DeviceRepository;
import com.de200118.mvc.service.DeviceService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DeviceRepository deviceRepository;
    private final CategoryServiceImpl categoryService;
    private final CategoryRepository categoryRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository, CategoryServiceImpl categoryService, CategoryRepository categoryRepository) {
        this.deviceRepository = deviceRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public List<Device> searchByName(String keyword) {
        return deviceRepository.findAllOrderByInstallationDateDesc();
    }

    @Override
    public Device getById(Integer id) {
        return deviceRepository.findById(id).orElse(null);
    }

    @Override
    public void validataBusinessRules(DeviceDTO deviceDTO, BindingResult bindingResult) {
        if (deviceDTO.getDeviceCode() != null && deviceDTO.getDeviceCode().isBlank() && bindingResult.hasFieldErrors("deviceCode")) {
            Optional<Device> existing = deviceRepository.findByDeviceCode(deviceDTO.getDeviceCode());
            if (existing.isPresent()) {
                bindingResult.addError(new FieldError("deviceDTO", "deviceCode", "Device code already exists"));
            }
        }

        if (deviceDTO.getCategoryId() != null) {
            if (!categoryRepository.existsById(deviceDTO.getCategoryId())) {
                bindingResult.addError(new FieldError("deviceDTO", "categoryId", "Category does not exist"));
            }
        }

        if (deviceDTO.getInstallationDate() != null && !deviceDTO.getInstallationDate().isBlank() &&
        !bindingResult.hasFieldErrors("installationDate")) {
            try {
                LocalDate installationDate = LocalDate.parse(deviceDTO.getInstallationDate().trim(), DATE_FORMATTER);
                if (!installationDate.isAfter(LocalDate.now())) {
                    bindingResult.addError(new FieldError("deviceDTO", "installationDate", "Installation date cannot be in the future"));
                }
            } catch (DateTimeParseException e) {
                bindingResult.addError(new FieldError("deviceDTO", "installationDate", "Invalid date format"));
            }
        }
    }

    @Override
    public void createDevice(DeviceDTO deviceDTO) {
        Category category = categoryRepository.findById(deviceDTO.getCategoryId()).orElseThrow(
                () -> new IllegalArgumentException("Category not found with id: " + deviceDTO.getCategoryId())
        );
        Device device = new Device();
        device.setDeviceCode(deviceDTO.getDeviceCode());
        device.setDeviceName(deviceDTO.getDeviceName());
        device.setCategory(category);
        device.setPrice(deviceDTO.getPrice());
        device.setInstallationDate(LocalDate.parse(deviceDTO.getInstallationDate().trim(), DATE_FORMATTER));
        device.setStatus(deviceDTO.getStatus());
        deviceRepository.save(device);
    }

    @Override
    public void deleteDevice(Integer id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Device not found with id: " + id));
        if ("AC".equalsIgnoreCase(device.getStatus())) {
            throw new IllegalStateException("Cannot delete an active device");
        }
        deviceRepository.deleteById(id);
    }
}
