package vn.edu.eaut.lab13.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vn.edu.eaut.lab13.entity.Course;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    @DisplayName("Bài 8 & 9: Test lưu và tìm kiếm Course")
    void testSaveAndFindCourse() {
        Course course = new Course("IT9999", "Kiểm thử phần mềm", 3, "Môn học kiểm thử chất lượng");
        Course saved = courseRepository.save(course);

        assertThat(saved.getId()).isNotNull();
        assertThat(courseRepository.existsByCourseCode("IT9999")).isTrue();

        List<Course> found = courseRepository.findByCourseNameContainingIgnoreCase("kiểm thử");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getCourseCode()).isEqualTo("IT9999");
    }
}
