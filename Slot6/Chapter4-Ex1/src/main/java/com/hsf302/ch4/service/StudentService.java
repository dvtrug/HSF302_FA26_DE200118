package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Student;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    // Các method được bổ sung dần từ TODO 6
    long count();
    Optional<Student> findById(long id);

    List<Student> findAllOrderByGpaDesc();                              // TODO 7a
    Page<Student> findPage(int pageIndex, int size, String sortField);  // TODO 7b

    Optional<Student> findByStudentCode(String studentCode);   // TODO 8a
    boolean isEmailExisted(String email);                      // TODO 8b
    long countActive();                                        // TODO 8c
}