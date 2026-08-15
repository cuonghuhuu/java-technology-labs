CREATE DATABASE IF NOT EXISTS minishop_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE minishop_db;

CREATE TABLE IF NOT EXISTS danh_muc (
    ma_dm INT AUTO_INCREMENT PRIMARY KEY,
    ten_dm VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS san_pham (
    ma_sp INT AUTO_INCREMENT PRIMARY KEY,
    ten_sp VARCHAR(150) NOT NULL,
    don_gia DECIMAL(15,2) NOT NULL,
    so_luong INT NOT NULL DEFAULT 0,
    ma_dm INT NULL,
    CONSTRAINT fk_san_pham_danh_muc FOREIGN KEY (ma_dm) REFERENCES danh_muc(ma_dm)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS khach_hang (
    ma_kh INT AUTO_INCREMENT PRIMARY KEY,
    ten_kh VARCHAR(150) NOT NULL,
    sdt VARCHAR(10) NOT NULL,
    dia_chi VARCHAR(255) NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS hoa_don (
    ma_hd INT AUTO_INCREMENT PRIMARY KEY,
    ma_kh INT NOT NULL,
    ngay_lap DATE NOT NULL,
    tong_tien DECIMAL(15,2) NOT NULL DEFAULT 0,
    CONSTRAINT fk_hoa_don_khach_hang FOREIGN KEY (ma_kh) REFERENCES khach_hang(ma_kh)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS chi_tiet_hoa_don (
    ma_hd INT NOT NULL,
    ma_sp INT NOT NULL,
    so_luong INT NOT NULL,
    don_gia DECIMAL(15,2) NOT NULL,
    thanh_tien DECIMAL(15,2) NOT NULL,
    PRIMARY KEY (ma_hd, ma_sp),
    CONSTRAINT fk_cthd_hoa_don FOREIGN KEY (ma_hd) REFERENCES hoa_don(ma_hd)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_cthd_san_pham FOREIGN KEY (ma_sp) REFERENCES san_pham(ma_sp)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS tai_khoan (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    ho_ten VARCHAR(100) NOT NULL,
    vai_tro VARCHAR(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO danh_muc(ma_dm, ten_dm) VALUES
    (1, 'Đồ uống'), (2, 'Đồ ăn nhẹ'), (3, 'Gia dụng');
INSERT IGNORE INTO san_pham(ma_sp, ten_sp, don_gia, so_luong, ma_dm) VALUES
    (1, 'Cà phê đen', 25000, 20, 1),
    (2, 'Trà đào', 30000, 15, 1),
    (3, 'Bánh mì', 18000, 25, 2),
    (4, 'Bánh quy', 22000, 10, 2);
INSERT IGNORE INTO khach_hang(ma_kh, ten_kh, sdt, dia_chi) VALUES
    (1, 'Nguyễn Văn An', '0900000001', 'Hà Nội'),
    (2, 'Trần Thị Bình', '0900000002', 'Hà Nội');
INSERT IGNORE INTO tai_khoan(username, password, ho_ten, vai_tro) VALUES
    ('admin', 'admin123', 'Quản trị viên', 'ADMIN'),
    ('nhanvien', 'nv123', 'Nhân viên bán hàng', 'NHANVIEN'),
    ('ketoan', 'kt123', 'Nhân viên kế toán', 'KETOAN');
