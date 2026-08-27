-- =======================================================
-- BÀI THỰC HÀNH LAB 10 - CÔNG NGHỆ JAVA (IT3242)
-- SCRIPT TẠO CẤU TRÚC BẢNG (DATABASE SCHEMA)
-- Tương thích: MySQL 8.x, SQL Server, H2 Database
-- =======================================================

CREATE DATABASE IF NOT EXISTS `lab10_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `lab10_db`;

-- 1. Bảng Người Dùng (Users & Roles)
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `full_name` VARCHAR(100) NOT NULL,
    `role` VARCHAR(20) NOT NULL,
    `active` BOOLEAN NOT NULL DEFAULT TRUE,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Bảng Sinh Viên (Module 1 Nghiệp vụ)
DROP TABLE IF EXISTS `sinh_vien`;
CREATE TABLE `sinh_vien` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `ma_sinh_vien` VARCHAR(30) NOT NULL UNIQUE,
    `ho_ten` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `lop` VARCHAR(50) NOT NULL,
    `gpa` DOUBLE DEFAULT 0.0,
    `ngay_sinh` DATE NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Bảng Sách Thư Viện (Module 2 Nghiệp vụ)
DROP TABLE IF EXISTS `sach`;
CREATE TABLE `sach` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `ma_sach` VARCHAR(30) NOT NULL UNIQUE,
    `ten_sach` VARCHAR(150) NOT NULL,
    `tac_gia` VARCHAR(100) NOT NULL,
    `the_loai` VARCHAR(50) NULL,
    `nam_xuat_ban` INT NULL,
    `gia` DOUBLE DEFAULT 0.0,
    `so_luong` INT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Bảng Sản Phẩm Kho (Module 3 Nghiệp vụ)
DROP TABLE IF EXISTS `san_pham`;
CREATE TABLE `san_pham` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `ma_san_pham` VARCHAR(30) NOT NULL UNIQUE,
    `ten_san_pham` VARCHAR(150) NOT NULL,
    `danh_muc` VARCHAR(50) NULL,
    `gia` DOUBLE DEFAULT 0.0,
    `so_luong` INT DEFAULT 0,
    `mo_ta` VARCHAR(500) NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. Bảng Nhật Ký Hoạt Động (Audit Logs)
DROP TABLE IF EXISTS `activity_logs`;
CREATE TABLE `activity_logs` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `timestamp` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `user_email` VARCHAR(100) NULL,
    `user_role` VARCHAR(20) NULL,
    `action` VARCHAR(100) NOT NULL,
    `details` VARCHAR(500) NULL,
    `ip_address` VARCHAR(50) NULL,
    `status` VARCHAR(20) DEFAULT 'SUCCESS'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
