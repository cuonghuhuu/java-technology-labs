package vn.edu.eaut.lab11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.eaut.lab11.model.ContactInfo;

import java.util.List;

@Controller
public class HomeController {

    /**
     * Bài 2: Tạo trang chủ
     * URL: /
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Hệ thống quản lý sinh viên");
        model.addAttribute("message", "Chào mừng đến với Spring Boot");
        model.addAttribute("activePage", "home");
        
        // Dữ liệu mở rộng mô tả các tính năng nổi bật của Spring Boot & Thymeleaf
        model.addAttribute("features", List.of(
                "Khởi tạo nhanh chóng với Spring Initializr & Spring Boot Starters",
                "Tích hợp Embedded Tomcat Server - chạy trực tiếp không cần cài đặt Tomcat độc lập",
                "Giao diện động đa năng với Thymeleaf Template Engine (Server-side rendering)",
                "Mô hình kiến trúc chuẩn MVC: Model - View - Controller rõ ràng, dễ bảo trì",
                "Tự động tải lại nhanh trong quá trình phát triển với Spring Boot DevTools"
        ));
        
        return "index";
    }

    /**
     * Bài 5: Tạo trang giới thiệu
     * URL: /about
     */
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("course", "Công nghệ Java");
        model.addAttribute("chapter", "Chương 4 - Spring Framework");
        model.addAttribute("courseCode", "IT3242");
        model.addAttribute("faculty", "Khoa Công nghệ Thông tin");
        model.addAttribute("university", "Trường Đại học Công nghệ Đông Á (EAUT)");
        model.addAttribute("activePage", "about");
        
        model.addAttribute("topics", List.of(
                "Giới thiệu Spring Framework & Spring Boot 3.x",
                "Cấu hình dự án với Maven (pom.xml) & Spring Initializr",
                "Thymeleaf syntax: th:text, th:each, th:href, th:if, th:replace",
                "Kiến trúc Spring MVC: Controller, Model, ViewResolver",
                "Tổ chức tài nguyên tĩnh (Static Resources: CSS, JS, Images)"
        ));

        return "about";
    }

    /**
     * Bài 6: Tạo trang liên hệ khoa/bộ môn
     * URL: /contact
     */
    @GetMapping("/contact")
    public String contact(Model model) {
        ContactInfo contact = new ContactInfo(
                "Khoa Công nghệ Thông tin",
                "Trường Đại học Công nghệ Đông Á (EAUT)",
                "Tòa nhà EAUT, Đường Trịnh Văn Bô, Xuân Phương, Nam Từ Liêm, Hà Nội",
                "fit@eaut.edu.vn",
                "024.6262.7797",
                "0388.656.656",
                "https://eaut.edu.vn",
                "Thứ 2 - Thứ 6: 08:00 - 17:00",
                "TS. Trưởng Khoa CNTT"
        );

        model.addAttribute("contact", contact);
        model.addAttribute("activePage", "contact");
        return "contact";
    }
}
