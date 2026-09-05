package vn.edu.eaut.lab13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.eaut.lab13.entity.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Tìm kiếm môn học theo tên môn học (không phân biệt hoa thường)
     */
    List<Course> findByCourseNameContainingIgnoreCase(String keyword);

    /**
     * Kiểm tra tồn tại theo mã môn học
     */
    boolean existsByCourseCode(String courseCode);

    /**
     * Kiểm tra tồn tại mã môn học nhưng loại trừ ID hiện tại (khi cập nhật)
     */
    boolean existsByCourseCodeAndIdNot(String courseCode, Long id);

    /**
     * Tìm kiếm linh hoạt theo mã môn hoặc tên môn
     */
    @Query("SELECT c FROM Course c WHERE " +
           "LOWER(c.courseCode) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
           "LOWER(c.courseName) LIKE LOWER(CONCAT('%', :kw, '%'))")
    List<Course> searchByKeyword(@Param("kw") String keyword);
}
