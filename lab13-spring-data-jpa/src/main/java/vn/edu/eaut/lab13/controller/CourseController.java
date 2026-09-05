package vn.edu.eaut.lab13.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab13.entity.Course;
import vn.edu.eaut.lab13.service.CourseService;

import java.util.List;

/**
 * Bài 9: CourseController thực hiện CRUD cho môn học
 */
@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Hiển thị danh sách môn học
     */
    @GetMapping
    public String list(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<Course> courses;
        if (keyword != null && !keyword.trim().isEmpty()) {
            courses = courseService.search(keyword.trim());
            model.addAttribute("keyword", keyword.trim());
        } else {
            courses = courseService.findAll();
        }
        model.addAttribute("courses", courses);
        model.addAttribute("totalCourses", courses.size());
        return "courses/list";
    }

    /**
     * Mở form thêm mới môn học
     */
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("pageTitle", "Thêm Mới Môn Học");
        return "courses/form";
    }

    /**
     * Mở form chỉnh sửa môn học theo ID
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Course course = courseService.findById(id);
            model.addAttribute("course", course);
            model.addAttribute("pageTitle", "Chỉnh Sửa Môn Học");
            return "courses/form";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/courses";
        }
    }

    /**
     * Lưu môn học (Thêm mới hoặc Cập nhật) với validation
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("course") Course course,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        // Kiểm tra trùng lặp mã môn học
        if (course.getCourseCode() != null && !course.getCourseCode().trim().isEmpty()) {
            if (courseService.existsByCourseCode(course.getCourseCode(), course.getId())) {
                bindingResult.rejectValue("courseCode", "duplicate", "Mã môn học đã tồn tại!");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", course.getId() == null ? "Thêm Mới Môn Học" : "Chỉnh Sửa Môn Học");
            return "courses/form";
        }

        boolean isUpdate = course.getId() != null;
        courseService.save(course);

        if (isUpdate) {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật môn học thành công!");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Thêm mới môn học thành công!");
        }

        return "redirect:/courses";
    }

    /**
     * Xóa môn học theo ID
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa môn học thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/courses";
    }
}
