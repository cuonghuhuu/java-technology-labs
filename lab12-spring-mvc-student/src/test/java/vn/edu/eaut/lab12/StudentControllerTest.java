package vn.edu.eaut.lab12;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import vn.edu.eaut.lab12.model.Student;
import vn.edu.eaut.lab12.service.StudentService;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Test
    @DisplayName("Bài 3: GET /students - Hiển thị danh sách sinh viên")
    void testListStudents() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/list"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attribute("students", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @DisplayName("Root URL: GET / - Tự động chuyển hướng về /students")
    void testRootRedirect() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));
    }

    @Test
    @DisplayName("Bài 4: GET /students/create - Hiển thị form tạo mới với đối tượng rỗng")
    void testCreateForm() throws Exception {
        mockMvc.perform(get("/students/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/form"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attribute("isEdit", false));
    }

    @Test
    @DisplayName("Bài 4 & 5: POST /students/save - Thêm sinh viên hợp lệ thành công")
    void testSaveNewStudentSuccess() throws Exception {
        mockMvc.perform(post("/students/save")
                        .param("studentCode", "SV9999")
                        .param("fullName", "Trần Văn Kiểm Thử")
                        .param("email", "kiemthu.tv@eaut.edu.vn")
                        .param("className", "D15CNPM99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"))
                .andExpect(flash().attributeExists("successMessage"));

        // Kiểm tra dữ liệu đã lưu trong service
        boolean exists = studentService.findAll().stream()
                .anyMatch(s -> "SV9999".equals(s.getStudentCode()));
        assertTrue(exists, "Sinh viên mới phải có trong danh sách của StudentService");
    }

    @Test
    @DisplayName("Bài 5: POST /students/save - Bắt lỗi validation khi để trống hoặc email sai định dạng")
    void testSaveValidationErrors() throws Exception {
        mockMvc.perform(post("/students/save")
                        .param("studentCode", "")
                        .param("fullName", "")
                        .param("email", "dinh-dang-sai-email")
                        .param("className", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("students/form"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("student", "studentCode", "fullName", "email", "className"));
    }

    @Test
    @DisplayName("Bài 1 & 5: POST /students/save - Bắt lỗi mã sinh viên dưới 5 ký tự")
    void testSaveStudentCodeMinSize() throws Exception {
        mockMvc.perform(post("/students/save")
                        .param("studentCode", "SV1") // < 5 ký tự
                        .param("fullName", "Lê Văn Tám")
                        .param("email", "tam.lv@eaut.edu.vn")
                        .param("className", "D15CNPM01"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/form"))
                .andExpect(model().attributeHasFieldErrorCode("student", "studentCode", "Size"));
    }

    @Test
    @DisplayName("Bài 6: GET /students/detail/{id} & GET /students/{id} - Xem chi tiết sinh viên")
    void testStudentDetail() throws Exception {
        // Kiểm tra endpoint /students/detail/1
        mockMvc.perform(get("/students/detail/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/detail"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attribute("student", hasProperty("studentCode", equalTo("SV0001"))));

        // Kiểm tra endpoint /students/1
        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/detail"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    @DisplayName("Bài 6: GET /students/detail/{id} - Chuyển hướng khi không tìm thấy sinh viên")
    void testStudentDetailNotFound() throws Exception {
        mockMvc.perform(get("/students/detail/99999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"))
                .andExpect(flash().attributeExists("errorMessage"));
    }

    @Test
    @DisplayName("Bài 7: GET /students/edit/{id} - Mở form chỉnh sửa sinh viên có sẵn")
    void testEditForm() throws Exception {
        mockMvc.perform(get("/students/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/form"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attribute("isEdit", true))
                .andExpect(model().attribute("student", hasProperty("fullName", equalTo("Nguyễn Văn An"))));
    }

    @Test
    @DisplayName("Bài 7: POST /students/save - Cập nhật thông tin sinh viên thành công")
    void testUpdateStudent() throws Exception {
        mockMvc.perform(post("/students/save")
                        .param("id", "1")
                        .param("studentCode", "SV0001")
                        .param("fullName", "Nguyễn Văn An (Đã Đổi Tên)")
                        .param("email", "an.nv_updated@eaut.edu.vn")
                        .param("className", "D15CNPM01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"))
                .andExpect(flash().attributeExists("successMessage"));

        Student updated = studentService.findById(1L).orElse(null);
        assertNotNull(updated);
        assertEquals("Nguyễn Văn An (Đã Đổi Tên)", updated.getFullName());
    }

    @Test
    @DisplayName("Bài 8: GET /students/delete/{id} - Xóa sinh viên khỏi danh sách")
    void testDeleteStudent() throws Exception {
        // Tạo thêm 1 sinh viên tạm thời để xóa
        Student temp = new Student("SV_DEL_01", "Sinh Viên Tạm", "temp@eaut.edu.vn", "D15CNPM01");
        studentService.save(temp);
        Long tempId = temp.getId();

        mockMvc.perform(get("/students/delete/" + tempId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"))
                .andExpect(flash().attributeExists("successMessage"));

        assertTrue(studentService.findById(tempId).isEmpty(), "Sinh viên vừa xóa không được tồn tại");
    }

    @Test
    @DisplayName("Bài 9: GET /students?keyword=... - Tìm kiếm sinh viên theo từ khóa")
    void testSearchByKeyword() throws Exception {
        mockMvc.perform(get("/students").param("keyword", "Bình"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/list"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attribute("keyword", "Bình"));
    }

    @Test
    @DisplayName("Bài 10: POST /students/save - Validation mã sinh viên không trùng trong danh sách")
    void testDuplicateStudentCodeValidation() throws Exception {
        // Thử thêm mới sinh viên với mã SV0002 đã có trong hệ thống
        mockMvc.perform(post("/students/save")
                        .param("studentCode", "SV0002") // Trùng với Trần Thị Bình
                        .param("fullName", "Người Trùng Mã")
                        .param("email", "trungma@eaut.edu.vn")
                        .param("className", "D15CNPM02"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/form"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("student", "studentCode", "duplicate"));
    }
}
