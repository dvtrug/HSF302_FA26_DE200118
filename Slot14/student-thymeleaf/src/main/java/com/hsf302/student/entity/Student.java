package com.hsf302.student.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Period;

// TODO 18: Ánh xạ entity với bảng SQL Server
//  - Lớp     : @Entity, @Table(name = "Students")
//  - id      : @Id + @GeneratedValue(strategy = GenerationType.IDENTITY)
//  - Mỗi field: @Column(name = "...", nullable = false, length = ...) theo bảng ở B0
//      + studentCode: length = 8, unique = true
//      + fullName   : length = 50 và thêm @Nationalized (NVARCHAR – lưu tiếng Việt)
//      + email      : length = 100
//      + major      : length = 2
//      + dateOfBirth, gpa, active: nullable = false
@Entity
@Table(name = "Students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO 1: Thêm validation
    //  - studentCode: bắt buộc; regex ^(SE|SS|SA|HE)\d{6}$
    //                 message: "Mã SV phải có dạng SE/SS/SA/HE + 6 chữ số (VD: SE180001)"
    //  - fullName   : bắt buộc; độ dài 2 - 50
    //  - email      : bắt buộc; đúng định dạng email
    @Column(unique = true, length = 8)
    private String studentCode;
    @Column(length = 50)
    @Nationalized
    private String fullName;
    @Column(length = 100)
    private String email;

    // TODO 2: Thêm validation
    //  - dateOfBirth: bắt buộc; phải là ngày trong quá khứ;
    //                 thêm @DateTimeFormat(pattern = "yyyy-MM-dd") để bind với <input type="date">
    //  - gpa        : bắt buộc; 0.0 <= gpa <= 10.0
    //  - major      : bắt buộc (chọn ngành)
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @Column(nullable = false)
    private Double gpa;
    @Column(nullable = false, length = 2)
    private String major;

    private boolean active = true;

    public Student() {
    }

    public Student(Long id, String studentCode, String fullName, String email,
                   LocalDate dateOfBirth, Double gpa, String major, boolean active) {
        this.id = id;
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gpa = gpa;
        this.major = major;
        this.active = active;
    }

    // TODO 3: Cài đặt 2 thuộc tính tính toán
    //  - getAge(): trả về số tuổi (dùng Period.between); dateOfBirth null → 0
    //  - getRank(): >=9 "Xuất sắc", >=8 "Giỏi", >=6.5 "Khá", >=5 "Trung bình", còn lại "Yếu";
    //               gpa null → "Chưa xếp loại"
    public int getAge() {
        if (dateOfBirth != null) {
            return Period.between(dateOfBirth, LocalDate.now()).getYears();
        }
        return 0;
    }

    public String getRank() {
        if (gpa >= 9) {
            return "Xuất sắc";
        }
        else if (gpa >= 8) {
            return "Giỏi";
        }
        else if (gpa >= 6.5) {
            return "Khá";
        }
        else if (gpa >= 5) {
            return "Trung bình";
        }
        else if (gpa < 5 && gpa > 0) {
            return "Yếu";
        }
        return "Chưa xếp loại";
    }

    // TODO 0: Generate Getters & Setters cho tất cả field (IntelliJ: Alt + Insert)
    //  Lưu ý: field boolean "active" → getter là isActive()

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}