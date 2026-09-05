package vn.edu.eaut.lab13.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import vn.edu.eaut.lab13.entity.Course;
import vn.edu.eaut.lab13.service.CourseService;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseService courseService;

    @Test
    @DisplayName("Bài 9: Test GET /courses hiển thị danh sách môn học")
    void testListCourses() throws Exception {
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/list"))
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    @DisplayName("Bài 9: Test GET /courses/create mở form thêm mới")
    void testCreateCourseForm() throws Exception {
        mockMvc.perform(get("/courses/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/form"))
                .andExpect(model().attributeExists("course"))
                .andExpect(model().attribute("pageTitle", "Thêm Mới Môn Học"));
    }

    @Test
    @DisplayName("Bài 9: Test POST /courses/save lưu môn học thành công")
    void testSaveCourseSuccess() throws Exception {
        mockMvc.perform(post("/courses/save")
                        .param("courseCode", "IT8888")
                        .param("courseName", "An toàn thông tin")
                        .param("credits", "3")
                        .param("description", "Môn học bảo mật hệ thống"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    @DisplayName("Bài 9: Test GET /courses/edit/{id} mở form sửa môn học")
    void testEditCourseForm() throws Exception {
        Course c = courseService.save(new Course("IT7777", "Điện toán đám mây", 3, "Cloud computing"));

        mockMvc.perform(get("/courses/edit/" + c.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/form"))
                .andExpect(model().attributeExists("course"))
                .andExpect(model().attribute("course", hasProperty("courseCode", is("IT7777"))));
    }

    @Test
    @DisplayName("Bài 9: Test GET /courses/delete/{id} xóa môn học")
    void testDeleteCourse() throws Exception {
        Course c = courseService.save(new Course("IT6666", "Trí tuệ nhân tạo", 4, "AI"));

        mockMvc.perform(get("/courses/delete/" + c.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"))
                .andExpect(flash().attributeExists("successMessage"));
    }
}
