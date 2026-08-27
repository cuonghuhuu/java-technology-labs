package vn.edu.eaut.lab11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.eaut.lab11.model.Student;

import java.util.List;

@Controller
public class StudentController {

    /**
     * Bài 4: Hiển thị danh sách sinh viên mẫu bằng Thymeleaf
     * URL: /students
     */
    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> students = List.of(
                new Student("SV001", "Nguyễn Văn An", "an@eaut.edu.vn", "DCCNTT13.10.1"),
                new Student("SV002", "Trần Thị Bình", "binh@eaut.edu.vn", "DCCNTT13.10.2"),
                new Student("SV003", "Lê Văn Cường", "cuong@eaut.edu.vn", "DCCNTT13.10.3"),
                new Student("SV004", "Phạm Thu Dung", "dung@eaut.edu.vn", "DCCNTT13.10.1"),
                new Student("SV005", "Hoàng Minh Đức", "duc@eaut.edu.vn", "DCCNTT13.10.2")
        );

        model.addAttribute("students", students);
        model.addAttribute("totalStudents", students.size());
        model.addAttribute("activePage", "students");
        return "students";
    }
}
