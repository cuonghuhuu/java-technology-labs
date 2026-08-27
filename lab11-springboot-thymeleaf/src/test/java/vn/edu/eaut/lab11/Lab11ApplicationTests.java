package vn.edu.eaut.lab11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class Lab11ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Kiểm tra context tải thành công")
    void contextLoads() {
    }

    @Test
    @DisplayName("Bài 2: Kiểm tra trang chủ / trả về view 'index' và chứa message")
    void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("title"))
                .andExpect(model().attributeExists("message"))
                .andExpect(content().string(containsString("Chào mừng đến với Spring Boot")));
    }

    @Test
    @DisplayName("Bài 5: Kiểm tra trang giới thiệu /about trả về view 'about'")
    void testAboutPage() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"))
                .andExpect(model().attribute("course", "Công nghệ Java"))
                .andExpect(model().attribute("chapter", "Chương 4 - Spring Framework"))
                .andExpect(content().string(containsString("Công nghệ Java")));
    }

    @Test
    @DisplayName("Bài 4: Kiểm tra trang danh sách sinh viên /students")
    void testStudentsPage() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students"))
                .andExpect(model().attributeExists("students"))
                .andExpect(content().string(containsString("SV001")))
                .andExpect(content().string(containsString("Nguyễn Văn An")))
                .andExpect(content().string(containsString("SV002")))
                .andExpect(content().string(containsString("SV003")));
    }

    @Test
    @DisplayName("Bài 8 & 9: Kiểm tra trang danh sách khóa học /courses")
    void testCoursesPage() throws Exception {
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attribute("totalCourses", 5))
                .andExpect(content().string(containsString("IT3242")))
                .andExpect(content().string(containsString("Công nghệ Java")));
    }

    @Test
    @DisplayName("Bài 6: Kiểm tra trang liên hệ /contact")
    void testContactPage() throws Exception {
        mockMvc.perform(get("/contact"))
                .andExpect(status().isOk())
                .andExpect(view().name("contact"))
                .andExpect(model().attributeExists("contact"))
                .andExpect(content().string(containsString("Khoa Công nghệ Thông tin")));
    }
}
