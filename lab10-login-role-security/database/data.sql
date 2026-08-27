-- =======================================================
-- BÀI THỰC HÀNH LAB 10 - CÔNG NGHỆ JAVA (IT3242)
-- SCRIPT DỮ LIỆU MẪU BAN ĐẦU (SEED DATA)
-- Mật khẩu mặc định của tất cả các tài khoản là: 123456 (được hash bằng BCrypt)
-- =======================================================

USE `lab10_db`;

-- 1. Dữ liệu Tài Khoản Người Dùng Mẫu (ADMIN, STAFF, USER)
INSERT INTO `users` (`id`, `email`, `password`, `full_name`, `role`, `active`, `created_at`) VALUES
(1, 'admin@eaut.edu.vn', '$2a$10$fV38f5fQjM7/w8F3y0Y1.exv6K2h2WpTfVf5y1m5vFw9eB6e3bEvy', 'Quản Trị Viên (Admin)', 'ADMIN', TRUE, NOW()),
(2, 'staff@eaut.edu.vn', '$2a$10$fV38f5fQjM7/w8F3y0Y1.exv6K2h2WpTfVf5y1m5vFw9eB6e3bEvy', 'Nhân Viên Nghiệp Vụ (Staff)', 'STAFF', TRUE, NOW()),
(3, 'user@eaut.edu.vn', '$2a$10$fV38f5fQjM7/w8F3y0Y1.exv6K2h2WpTfVf5y1m5vFw9eB6e3bEvy', 'Người Dùng Thường (User)', 'USER', TRUE, NOW());

-- 2. Dữ liệu Sinh Viên Mẫu (Module 1)
INSERT INTO `sinh_vien` (`id`, `ma_sinh_vien`, `ho_ten`, `email`, `lop`, `gpa`, `ngay_sinh`) VALUES
(1, 'SV001', 'Nguyễn Văn An', 'an.nv@eaut.edu.vn', 'DCCNTT15.10.1', 3.65, '2003-05-12'),
(2, 'SV002', 'Trần Thị Bình', 'binh.tt@eaut.edu.vn', 'DCCNTT15.10.2', 3.40, '2003-08-20'),
(3, 'SV003', 'Lê Văn Cường', 'cuong.lv@eaut.edu.vn', 'DCCNTT15.10.1', 3.85, '2003-11-05'),
(4, 'SV004', 'Phạm Thu Hà', 'ha.pt@eaut.edu.vn', 'DCCNTT15.10.3', 3.20, '2003-02-18'),
(5, 'SV005', 'Hoàng Minh Đức', 'duc.hm@eaut.edu.vn', 'DCCNTT15.10.2', 2.95, '2003-09-30');

-- 3. Dữ liệu Sách Thư Viện Mẫu (Module 2)
INSERT INTO `sach` (`id`, `ma_sach`, `ten_sach`, `tac_gia`, `the_loai`, `nam_xuat_ban`, `gia`, `so_luong`) VALUES
(1, 'BK001', 'Lập trình Java Căn bản & Nâng cao', 'Nguyễn Vũ', 'Công nghệ thông tin', 2023, 150000.0, 35),
(2, 'BK002', 'Phát triển Web với Jakarta EE & Spring', 'Trần Nam', 'Công nghệ thông tin', 2024, 185000.0, 20),
(3, 'BK003', 'Thiết kế Kiến trúc Phần mềm Microservices', 'Lê Hùng', 'Kiến trúc phần mềm', 2022, 220000.0, 15),
(4, 'BK004', 'Cấu trúc Dữ liệu và Giải thuật với Java', 'Đỗ Hưng', 'Khoa học máy tính', 2021, 130000.0, 50),
(5, 'BK005', 'Học sâu và Trí tuệ Nhân tạo thực chiến', 'Phạm Long', 'Trí tuệ nhân tạo', 2024, 260000.0, 18);

-- 4. Dữ liệu Sản Phẩm Mẫu (Module 3)
INSERT INTO `san_pham` (`id`, `ma_san_pham`, `ten_san_pham`, `danh_muc`, `gia`, `so_luong`, `mo_ta`) VALUES
(1, 'SP001', 'Laptop Dell XPS 15 9530', 'Máy tính xách tay', 38500000.0, 12, 'Core i7 13700H, RAM 32GB, SSD 1TB, RTX 4060'),
(2, 'SP002', 'MacBook Pro 14 M3 Pro', 'Máy tính xách tay', 49900000.0, 8, 'Apple M3 Pro 18GB Unified Memory, SSD 512GB'),
(3, 'SP003', 'Màn hình Dell UltraSharp 27 4K', 'Màn hình máy tính', 12800000.0, 25, '27 inch IPS 4K HDR400, USB-C 90W Hub'),
(4, 'SP004', 'Bàn phím cơ Keychron Q1 Pro', 'Phụ kiện', 4200000.0, 30, 'Custom Wireless Mechanical Keyboard, QMK/VIA'),
(5, 'SP005', 'Chuột không dây Logitech MX Master 3S', 'Phụ kiện', 2450000.0, 45, 'Cảm biến 8K DPI, cuộn vô cực MagSpeed');

-- 5. Dữ liệu Nhật Ký Khởi Tạo
INSERT INTO `activity_logs` (`id`, `timestamp`, `user_email`, `user_role`, `action`, `details`, `ip_address`, `status`) VALUES
(1, NOW(), 'SYSTEM', 'SYSTEM', 'SERVER_START', 'Khởi tạo cơ sở dữ liệu và nạp dữ liệu mẫu ban đầu', '127.0.0.1', 'SUCCESS');
