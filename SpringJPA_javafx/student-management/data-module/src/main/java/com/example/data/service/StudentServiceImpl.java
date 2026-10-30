package com.example.data.service;

import com.example.data.exception.StudentNotFoundException;
import com.example.data.exception.ValidationException;
import com.example.data.pojo.Student;
import com.example.data.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

// TODO 3.1: @Service + @Transactional(readOnly = true)
@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[\\w.-]+$");
    // TODO 3.2: field final StudentRepository + constructor injection (KHÔNG dùng @Autowired trên field)\
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    @Override
    // TODO 3.3: @Transactional — normalize → validate → existsByEmailIgnoreCase → save
    @Transactional
    public Student create(Student s) {
        normalize(s);
        validate(s);
        if (studentRepository.existsByEmailIgnoreCase(s.getEmail())) {
            throw new ValidationException("Email already exists");
        }
        s.setId(null);
        return studentRepository.save(s);
    }

    @Override
    // TODO 3.4: findById(...).orElseThrow(() -> new StudentNotFoundException(id))
    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    // TODO 3.5: findAllByOrderByIdAsc()
    public List<Student> findAll() {
        return studentRepository.findAllByOrderByIdAsc();
    }

    @Override
    // TODO 3.6: @Transactional — findById (entity managed) → normalize → validate
    //           → existsByEmailIgnoreCaseAndIdNot → copy field → save
    @Transactional
    public Student update(Student s) {
        Student old = findById(s.getId());
        normalize(s);
        validate(s);
        if (studentRepository.existsByEmailIgnoreCaseAndIdNot(s.getEmail(), s.getId())) {
            throw new ValidationException("Email already exists");
        }
        old.setName(s.getName());
        old.setEmail(s.getEmail());
        old.setAge(s.getAge());
        return studentRepository.save(old);
    }

    @Override
    // TODO 3.7: @Transactional — !existsById → StudentNotFoundException; ngược lại deleteById
    @Transactional
    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    // TODO 3.8
    public List<Student> search(String keyword) {
        if (keyword.isBlank()) {
            return findAll();
        }
        return studentRepository.search(keyword.trim());
    }

    private void normalize(Student s) {
        // TODO 3.9
        if (s.getEmail() != null) {
            s.setEmail(s.getEmail().toLowerCase());
        }
        if (s.getName() != null) {
            s.setName(s.getName().trim());
        }
    }

    private void validate(Student s) {
        // TODO 3.10
        if (s.getName() == null || s.getName().isBlank()) {
            throw new ValidationException("Name is required");
        }
        if (s.getEmail() == null || !EMAIL.matcher(s.getEmail()).matches()) {
            throw new ValidationException("Invalid email");
        }
        if (s.getAge() == null || s.getAge() < 18 || s.getAge() > 60) {
            throw new ValidationException("Age must be at least 18 and at most 60");
        }
    }
}