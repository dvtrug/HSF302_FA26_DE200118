package com.hsf302.ch4.service;

import com.hsf302.ch4.dto.DepartmentStatDTO;
import com.hsf302.ch4.pojo.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    // Các method được bổ sung dần từ TODO 6
    long count();
    boolean existsById(long id);

    List<Department> findDepartmentsWithoutStudents();  // TODO 11d

    List<DepartmentStatDTO> getStatistics();   // TODO 14 (dùng lại ở TODO 23)

    Optional<Department> findByCode(String code);   // TODO 16a

    Department getWithStudents(String code);   // TODO 16b
}