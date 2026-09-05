package vn.edu.eaut.lab13.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import vn.edu.eaut.lab13.entity.Student;
import vn.edu.eaut.lab13.service.StudentService;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Test
    @DisplayName("Bài 5: Test GET /students trả về danh sách sinh viên")
    void testListStudents() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/list"))
                .andExpect(model().attributeExists("students"));
    }

    @Test
    @DisplayName("Test chuyển hướng từ / về /students")
    void testRootRedirect() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));
    }

    @Test
    @DisplayName("Bài 5: Test GET /students/create mở form thêm mới")
    void testCreateForm() throws Exception {
        mockMvc.perform(get("/students/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/form"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attribute("pageTitle", "Thêm Mới Sinh Viên"));
    }

    @Test
    @DisplayName("Bài 5: Test POST /students/save lưu sinh viên thành công")
    void testSaveStudentSuccess() throws Exception {
        mockMvc.perform(post("/students/save")
                        .param("studentCode", "20239991")
                        .param("fullName", "Sinh Vien Test")
                        .param("email", "testsave@eaut.edu.vn")
                        .param("className", "D18CNPM01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    @DisplayName("Bài 5: Test POST /students/save lỗi validation khi để trống")
    void testSaveStudentValidationError() throws Exception {
        mockMvc.perform(post("/students/save")
                        .param("studentCode", "")
                        .param("fullName", "")
                        .param("email", "invalid-email")
                        .param("className", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("students/form"))
                .andExpect(model().hasErrors());
    }

    @Test
    @DisplayName("Bài 6: Test GET /students/edit/{id} mở form sửa sinh viên")
    void testEditStudentForm() throws Exception {
        // Lưu 1 sinh viên tạm
        Student s = studentService.save(new Student("20238888", "Sinh Viên Sửa", "sua@eaut.edu.vn", "D18CNPM01"));

        mockMvc.perform(get("/students/edit/" + s.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("students/form"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attribute("student", hasProperty("studentCode", is("20238888"))));
    }

    @Test
    @DisplayName("Bài 7: Test GET /students với keyword tìm kiếm")
    void testSearchStudentByKeyword() throws Exception {
        studentService.save(new Student("20237777", "Nguyễn Tìm Kiếm", "timkiem@eaut.edu.vn", "D18CNPM01"));

        mockMvc.perform(get("/students").param("keyword", "Tìm Kiếm"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/list"))
                .andExpect(model().attribute("keyword", "Tìm Kiếm"))
                .andExpect(model().attributeExists("students"));
    }

    @Test
    @DisplayName("Bài 5: Test GET /students/delete/{id} xóa sinh viên")
    void testDeleteStudent() throws Exception {
        Student s = studentService.save(new Student("20236666", "Sinh Viên Xóa", "xoa@eaut.edu.vn", "D18CNPM01"));

        mockMvc.perform(get("/students/delete/" + s.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"))
                .andExpect(flash().attributeExists("successMessage"));
    }
}
