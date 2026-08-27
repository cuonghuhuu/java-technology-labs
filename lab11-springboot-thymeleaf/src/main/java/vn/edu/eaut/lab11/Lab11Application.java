package vn.edu.eaut.lab11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lab11Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab11Application.class, args);
        System.out.println("==========================================================");
        System.out.println(">> Lab 11 Spring Boot & Thymeleaf is running!");
        System.out.println(">> URL Trang chu    : http://localhost:8080/");
        System.out.println(">> URL Gioi thieu   : http://localhost:8080/about");
        System.out.println(">> URL Sinh vien    : http://localhost:8080/students");
        System.out.println(">> URL Khoa hoc     : http://localhost:8080/courses");
        System.out.println(">> URL Lien he      : http://localhost:8080/contact");
        System.out.println("==========================================================");
    }
}
