package com.hsf302.ch4.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentServiceImpl implements EnrollmentService{
    private final CourseService courseService;
    private final StudentService studentService;
}
