package vn.edu.eaut.lab12.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import vn.edu.eaut.lab12.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>();
    private long nextId = 1;

    @PostConstruct
    public void initData() {
        save(new Student("SV0001", "Nguyễn Văn An", "an.nv@eaut.edu.vn", "D15CNPM01"));
        save(new Student("SV0002", "Trần Thị Bình", "binh.tt@eaut.edu.vn", "D15CNPM02"));
        save(new Student("SV0003", "Lê Hoàng Cường", "cuong.lh@eaut.edu.vn", "D15CNPM01"));
        save(new Student("SV0004", "Phạm Minh Đức", "duc.pm@eaut.edu.vn", "D15CNPM03"));
    }

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public Optional<Student> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return students.stream()
                .filter(s -> id.equals(s.getId()))
                .findFirst();
    }

    public void save(Student student) {
        if (student == null) {
            return;
        }
        if (student.getId() == null) {
            // Thêm mới
            student.setId(nextId++);
            students.add(student);
        } else {
            // Cập nhật sinh viên đã có
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId().equals(student.getId())) {
                    students.set(i, student);
                    return;
                }
            }
            // Nếu id không tìm thấy trong danh sách (phòng hờ)
            students.add(student);
        }
    }

    public boolean deleteById(Long id) {
        if (id == null) {
            return false;
        }
        return students.removeIf(s -> id.equals(s.getId()));
    }

    public List<Student> searchByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        String lowerKeyword = keyword.trim().toLowerCase();
        return students.stream()
                .filter(s -> (s.getFullName() != null && s.getFullName().toLowerCase().contains(lowerKeyword))
                        || (s.getStudentCode() != null && s.getStudentCode().toLowerCase().contains(lowerKeyword))
                        || (s.getClassName() != null && s.getClassName().toLowerCase().contains(lowerKeyword)))
                .collect(Collectors.toList());
    }

    public boolean existsByStudentCode(String studentCode, Long excludeId) {
        if (studentCode == null || studentCode.trim().isEmpty()) {
            return false;
        }
        String trimmedCode = studentCode.trim();
        return students.stream()
                .anyMatch(s -> s.getStudentCode() != null
                        && s.getStudentCode().equalsIgnoreCase(trimmedCode)
                        && (excludeId == null || !excludeId.equals(s.getId())));
    }
}
