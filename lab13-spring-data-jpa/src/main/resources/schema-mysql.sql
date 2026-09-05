-- ===================================================================
-- Lab 13: Cấu hình cơ sở dữ liệu MySQL (Bài 10)
-- Tên CSDL gợi ý: eautdb hoặc eaut_lab13
-- ===================================================================

CREATE DATABASE IF NOT EXISTS eautdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE eautdb;

-- 1. Bảng students (Sinh viên)
DROP TABLE IF EXISTS students;
CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_code VARCHAR(20) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    class_name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. Bảng courses (Môn học - Bài 8)
DROP TABLE IF EXISTS courses;
CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(20) NOT NULL UNIQUE,
    course_name VARCHAR(150) NOT NULL,
    credits INT NOT NULL,
    description VARCHAR(500)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. Nạp dữ liệu mẫu
INSERT INTO students (student_code, full_name, email, class_name) VALUES
('20231768', 'Lê Văn Cường', 'cuong11220055@gmail.com', 'D18CNPM01'),
('20231701', 'Trần Thị Mai', 'maitran@eaut.edu.vn', 'D18CNPM01'),
('20231702', 'Nguyễn Văn An', 'annguyen@eaut.edu.vn', 'D18CNPM02'),
('20231703', 'Hoàng Minh Đức', 'duchoang@eaut.edu.vn', 'D18CNPM01'),
('20231704', 'Phạm Thúy Nga', 'ngapham@eaut.edu.vn', 'D18CNPM02');

INSERT INTO courses (course_code, course_name, credits, description) VALUES
('IT3242', 'Công nghệ Java (Spring Framework)', 3, 'Lập trình backend với Spring Boot, Spring MVC, Spring Data JPA'),
('IT3240', 'Phát triển ứng dụng Web chuyên sâu', 3, 'Xây dựng hệ thống web full-stack hiện đại'),
('IT3120', 'Cơ sở dữ liệu nâng cao', 4, 'Thiết kế, chuẩn hóa và tối ưu hóa truy vấn SQL'),
('IT2110', 'Cấu trúc dữ liệu và giải thuật', 3, 'Các thuật toán tìm kiếm, sắp xếp và đồ thị');
