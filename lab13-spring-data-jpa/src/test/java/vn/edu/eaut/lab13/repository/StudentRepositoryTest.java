package vn.edu.eaut.lab13.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vn.edu.eaut.lab13.entity.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @DisplayName("Test lưu sinh viên và tìm kiếm theo ID")
    void testSaveAndFindById() {
        Student student = new Student("TEST001", "Trần Văn Test", "test@eaut.edu.vn", "CNTT01");
        Student saved = studentRepository.save(student);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getStudentCode()).isEqualTo("TEST001");

        Student found = studentRepository.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getFullName()).isEqualTo("Trần Văn Test");
    }

    @Test
    @DisplayName("Bài 3 & Bài 7: Test tìm kiếm theo họ tên (không phân biệt hoa thường)")
    void testFindByFullNameContainingIgnoreCase() {
        Student s1 = new Student("TEST002", "Lê Văn Cường", "cuong@eaut.edu.vn", "CNTT01");
        Student s2 = new Student("TEST003", "Trần Mai Phương", "phuong@eaut.edu.vn", "CNTT02");
        studentRepository.save(s1);
        studentRepository.save(s2);

        List<Student> results = studentRepository.findByFullNameContainingIgnoreCase("cường");
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getStudentCode()).isEqualTo("TEST002");

        List<Student> allWithVan = studentRepository.findByFullNameContainingIgnoreCase("văn");
        assertThat(allWithVan).isNotEmpty();
    }

    @Test
    @DisplayName("Test kiểm tra trùng mã sinh viên")
    void testExistsByStudentCode() {
        Student s = new Student("TEST004", "Nguyễn Văn B", "b@eaut.edu.vn", "CNTT01");
        Student saved = studentRepository.save(s);

        assertThat(studentRepository.existsByStudentCode("TEST004")).isTrue();
        assertThat(studentRepository.existsByStudentCode("NON_EXISTENT")).isFalse();

        // Kiểm tra loại trừ ID chính nó
        assertThat(studentRepository.existsByStudentCodeAndIdNot("TEST004", saved.getId())).isFalse();
    }
}
