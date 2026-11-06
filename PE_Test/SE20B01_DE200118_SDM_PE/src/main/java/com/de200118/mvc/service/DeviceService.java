package com.de200118.mvc.service;

import com.de200118.mvc.dto.DeviceDTO;
import com.de200118.mvc.entity.Device;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface DeviceService {
    List<Device> getAllDevices();

    List<Device> searchByName(String keyword);

    Device getById(Integer id);

    void validataBusinessRules(DeviceDTO deviceDTO, BindingResult bindingResult);

    void createDevice(DeviceDTO deviceDTO);

    void deleteDevice(Integer id);
}
