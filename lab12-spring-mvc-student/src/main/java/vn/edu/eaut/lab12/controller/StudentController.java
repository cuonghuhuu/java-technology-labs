package vn.edu.eaut.lab12.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab12.model.Student;
import vn.edu.eaut.lab12.service.StudentService;

import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Bài 3 & Bài 9: Hiển thị danh sách sinh viên hoặc tìm kiếm theo từ khóa
     */
    @GetMapping
    public String list(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("students", studentService.searchByKeyword(keyword.trim()));
            model.addAttribute("keyword", keyword.trim());
        } else {
            model.addAttribute("students", studentService.findAll());
        }
        return "students/list";
    }

    /**
     * Bài 4: Hiển thị form tạo mới sinh viên
     */
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("isEdit", false);
        return "students/form";
    }

    /**
     * Bài 7: Hiển thị form chỉnh sửa sinh viên theo ID
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sinh viên có ID: " + id);
            return "redirect:/students";
        }
        model.addAttribute("student", studentOpt.get());
        model.addAttribute("isEdit", true);
        return "students/form";
    }

    /**
     * Bài 4, Bài 5 & Bài 10: Xử lý lưu sinh viên (thêm mới hoặc cập nhật)
     * Kèm validation các trường bắt buộc và kiểm tra không trùng mã sinh viên
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("student") Student student,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        // Bài 10: Validation mã sinh viên không trùng trong danh sách hiện có
        if (student.getStudentCode() != null && !student.getStudentCode().trim().isEmpty()) {
            if (studentService.existsByStudentCode(student.getStudentCode().trim(), student.getId())) {
                result.rejectValue("studentCode", "duplicate", "Mã sinh viên đã tồn tại trong hệ thống!");
            }
        }

        // Bài 5: Kiểm tra lỗi validation
        if (result.hasErrors()) {
            model.addAttribute("isEdit", student.getId() != null);
            return "students/form";
        }

        boolean isUpdate = (student.getId() != null);
        studentService.save(student);

        redirectAttributes.addFlashAttribute("successMessage",
                isUpdate ? "Cập nhật sinh viên thành công!" : "Thêm mới sinh viên thành công!");
        return "redirect:/students";
    }

    /**
     * Bài 6: Xem chi tiết sinh viên theo ID (Hỗ trợ cả /students/{id} và /students/detail/{id})
     */
    @GetMapping({"/detail/{id}", "/{id}"})
    public String detail(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sinh viên có ID: " + id);
            return "redirect:/students";
        }
        model.addAttribute("student", studentOpt.get());
        return "students/detail";
    }

    /**
     * Bài 8: Xóa sinh viên khỏi danh sách theo ID
     */
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean removed = studentService.deleteById(id);
        if (removed) {
            redirectAttributes.addFlashAttribute("successMessage", "Xóa sinh viên thành công!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sinh viên để xóa!");
        }
        return "redirect:/students";
    }
}
