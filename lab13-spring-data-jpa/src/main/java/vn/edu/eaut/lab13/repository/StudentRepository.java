package vn.edu.eaut.lab13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.eaut.lab13.entity.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Bài 3 & Bài 7: Tìm kiếm sinh viên theo họ tên (không phân biệt chữ hoa, chữ thường)
     */
    List<Student> findByFullNameContainingIgnoreCase(String keyword);

    /**
     * Kiểm tra tồn tại theo mã sinh viên
     */
    boolean existsByStudentCode(String studentCode);

    /**
     * Kiểm tra tồn tại mã sinh viên nhưng loại trừ ID hiện tại (khi cập nhật)
     */
    boolean existsByStudentCodeAndIdNot(String studentCode, Long id);

    /**
     * Tìm kiếm linh hoạt theo họ tên, mã sinh viên hoặc tên lớp
     */
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.fullName) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
           "LOWER(s.studentCode) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
           "LOWER(s.className) LIKE LOWER(CONCAT('%', :kw, '%'))")
    List<Student> searchByKeyword(@Param("kw") String keyword);
}
