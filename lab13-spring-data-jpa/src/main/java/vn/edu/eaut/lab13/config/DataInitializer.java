package vn.edu.eaut.lab13.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vn.edu.eaut.lab13.entity.Course;
import vn.edu.eaut.lab13.entity.Student;
import vn.edu.eaut.lab13.repository.CourseRepository;
import vn.edu.eaut.lab13.repository.StudentRepository;

import java.util.List;

/**
 * Tự động nạp dữ liệu mẫu khi khởi động ứng dụng nếu cơ sở dữ liệu chưa có dữ liệu.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public DataInitializer(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) {
        if (studentRepository.count() == 0) {
            studentRepository.saveAll(List.of(
                    new Student("20231768", "Lê Văn Cường", "cuong11220055@gmail.com", "D18CNPM01"),
                    new Student("20231701", "Trần Thị Mai", "maitran@eaut.edu.vn", "D18CNPM01"),
                    new Student("20231702", "Nguyễn Văn An", "annguyen@eaut.edu.vn", "D18CNPM02"),
                    new Student("20231703", "Hoàng Minh Đức", "duchoang@eaut.edu.vn", "D18CNPM01"),
                    new Student("20231704", "Phạm Thúy Nga", "ngapham@eaut.edu.vn", "D18CNPM02")
            ));
            System.out.println(">>> Đã nạp dữ liệu mẫu cho bảng students thành công!");
        }

        if (courseRepository.count() == 0) {
            courseRepository.saveAll(List.of(
                    new Course("IT3242", "Công nghệ Java (Spring Framework)", 3, "Lập trình backend với Spring Boot, Spring MVC, Spring Data JPA"),
                    new Course("IT3240", "Phát triển ứng dụng Web chuyên sâu", 3, "Xây dựng hệ thống web full-stack hiện đại"),
                    new Course("IT3120", "Cơ sở dữ liệu nâng cao", 4, "Thiết kế, chuẩn hóa và tối ưu hóa truy vấn SQL"),
                    new Course("IT2110", "Cấu trúc dữ liệu và giải thuật", 3, "Các thuật toán tìm kiếm, sắp xếp và đồ thị")
            ));
            System.out.println(">>> Đã nạp dữ liệu mẫu cho bảng courses thành công!");
        }
    }
}
