package vn.edu.eaut.lab11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.eaut.lab11.model.Course;

import java.util.List;

@Controller
public class CourseController {

    /**
     * Bài 8 & Bài 9: Tạo danh sách 5 khóa học mẫu và hiển thị trang /courses bằng Thymeleaf
     * URL: /courses
     */
    @GetMapping("/courses")
    public String listCourses(Model model) {
        List<Course> courses = List.of(
                new Course("IT3242", "Công nghệ Java", 3, "TS. Đặng Thanh Hưng", "Phát triển ứng dụng Web đa lớp với Spring Boot và Thymeleaf"),
                new Course("IT3010", "Cấu trúc dữ liệu và giải thuật", 3, "ThS. Nguyễn Văn A", "Các cấu trúc dữ liệu căn bản, cây, đồ thị và giải thuật tìm kiếm, sắp xếp"),
                new Course("IT3100", "Cơ sở dữ liệu nâng cao", 3, "ThS. Trần Thị B", "Mô hình quan hệ, ngôn ngữ SQL, thiết kế lược đồ quan hệ và tối ưu hóa truy vấn"),
                new Course("IT3080", "Mạng máy tính", 3, "TS. Lê Văn C", "Kiến trúc mạng máy tính, giao thức TCP/IP, định tuyến và bảo mật mạng căn bản"),
                new Course("IT3310", "Lập trình Web nâng cao", 4, "ThS. Hoàng Minh D", "Xây dựng Single Page Application, RESTful API và xác thực JWT")
        );

        int totalCredits = courses.stream().mapToInt(Course::getCredits).sum();

        model.addAttribute("courses", courses);
        model.addAttribute("totalCourses", courses.size());
        model.addAttribute("totalCredits", totalCredits);
        model.addAttribute("activePage", "courses");
        return "courses";
    }
}
