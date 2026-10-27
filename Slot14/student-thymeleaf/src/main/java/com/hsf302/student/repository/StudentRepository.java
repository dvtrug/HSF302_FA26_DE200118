package com.hsf302.student.repository;

import com.hsf302.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // 1. Tìm theo mã SV HOẶC họ tên chứa từ khóa (không phân biệt hoa thường), order by id tăng dần
    List<Student> findByStudentCodeContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrderByIdAsc(String code, String name);

    // 2. Kiểm tra mã SV đã tồn tại (dùng khi thêm mới)
    boolean existsByStudentCodeIgnoreCase(String studentCode);

    // 3. Kiểm tra mã SV đã tồn tại ở sinh viên khác (dùng khi cập nhật – bỏ qua chính nó)
    boolean existsByStudentCodeIgnoreCaseAndIdNot(String studentCode, Long id);
}
