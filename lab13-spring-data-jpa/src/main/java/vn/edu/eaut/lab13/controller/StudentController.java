package vn.edu.eaut.lab13.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab13.entity.Student;
import vn.edu.eaut.lab13.service.StudentService;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Bài 5 & Bài 7: Hiển thị danh sách sinh viên hoặc kết quả tìm kiếm
     */
    @GetMapping
    public String list(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<Student> students;
        if (keyword != null && !keyword.trim().isEmpty()) {
            students = studentService.search(keyword.trim());
            model.addAttribute("keyword", keyword.trim());
        } else {
            students = studentService.findAll();
        }
        model.addAttribute("students", students);
        model.addAttribute("totalStudents", students.size());
        return "students/list";
    }

    /**
     * Bài 7: Endpoint tìm kiếm riêng biệt theo họ tên
     */
    @GetMapping("/search")
    public String search(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        return list(keyword, model);
    }

    /**
     * Bài 5: Mở form thêm mới sinh viên
     */
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Thêm Mới Sinh Viên");
        return "students/form";
    }

    /**
     * Bài 6: Mở form chỉnh sửa sinh viên theo ID
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Student student = studentService.findById(id);
            model.addAttribute("student", student);
            model.addAttribute("pageTitle", "Chỉnh Sửa Sinh Viên");
            return "students/form";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/students";
        }
    }

    /**
     * Bài 5 & Bài 6: Lưu sinh viên (Thêm mới hoặc Cập nhật) với validation
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("student") Student student,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        // Kiểm tra trùng lặp mã sinh viên
        if (student.getStudentCode() != null && !student.getStudentCode().trim().isEmpty()) {
            if (studentService.existsByStudentCode(student.getStudentCode(), student.getId())) {
                bindingResult.rejectValue("studentCode", "duplicate", "Mã sinh viên đã tồn tại trong hệ thống!");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", student.getId() == null ? "Thêm Mới Sinh Viên" : "Chỉnh Sửa Sinh Viên");
            return "students/form";
        }

        boolean isUpdate = student.getId() != null;
        studentService.save(student);

        if (isUpdate) {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sinh viên thành công!");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Thêm mới sinh viên thành công!");
        }

        return "redirect:/students";
    }

    /**
     * Bài 5: Xóa sinh viên theo ID
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa sinh viên thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/students";
    }
}
