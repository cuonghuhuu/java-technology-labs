package vn.edu.eaut.lab13.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.eaut.lab13.entity.Course;
import vn.edu.eaut.lab13.repository.CourseRepository;

import java.util.List;

/**
 * Bài 9: CourseService xử lý nghiệp vụ cho Môn học
 */
@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học với ID: " + id));
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void deleteById(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy môn học với ID: " + id + " để xóa.");
        }
        courseRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Course> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return courseRepository.findAll();
        }
        return courseRepository.searchByKeyword(keyword.trim());
    }

    @Transactional(readOnly = true)
    public boolean existsByCourseCode(String courseCode) {
        if (courseCode == null) return false;
        return courseRepository.existsByCourseCode(courseCode.trim());
    }

    @Transactional(readOnly = true)
    public boolean existsByCourseCode(String courseCode, Long excludeId) {
        if (courseCode == null) return false;
        if (excludeId == null) {
            return courseRepository.existsByCourseCode(courseCode.trim());
        }
        return courseRepository.existsByCourseCodeAndIdNot(courseCode.trim(), excludeId);
    }
}
