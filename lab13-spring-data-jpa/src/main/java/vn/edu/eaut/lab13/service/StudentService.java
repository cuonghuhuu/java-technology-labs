package vn.edu.eaut.lab13.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.eaut.lab13.entity.Student;
import vn.edu.eaut.lab13.repository.StudentRepository;

import java.util.List;

/**
 * Bài 4: StudentService xử lý nghiệp vụ và gọi Repository
 */
@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với ID: " + id));
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void deleteById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sinh viên với ID: " + id + " để xóa.");
        }
        studentRepository.deleteById(id);
    }

    /**
     * Bài 7: Tìm kiếm sinh viên theo từ khóa (họ tên, mã SV, lớp)
     */
    @Transactional(readOnly = true)
    public List<Student> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return studentRepository.findAll();
        }
        return studentRepository.findByFullNameContainingIgnoreCase(keyword.trim());
    }

    /**
     * Tìm kiếm nâng cao kết hợp họ tên, mã sinh viên và lớp
     */
    @Transactional(readOnly = true)
    public List<Student> searchAdvanced(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return studentRepository.findAll();
        }
        return studentRepository.searchByKeyword(keyword.trim());
    }

    @Transactional(readOnly = true)
    public boolean existsByStudentCode(String studentCode) {
        if (studentCode == null) return false;
        return studentRepository.existsByStudentCode(studentCode.trim());
    }

    @Transactional(readOnly = true)
    public boolean existsByStudentCode(String studentCode, Long excludeId) {
        if (studentCode == null) return false;
        if (excludeId == null) {
            return studentRepository.existsByStudentCode(studentCode.trim());
        }
        return studentRepository.existsByStudentCodeAndIdNot(studentCode.trim(), excludeId);
    }
}
