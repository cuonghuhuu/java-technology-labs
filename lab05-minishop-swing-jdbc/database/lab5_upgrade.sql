USE minishop_db;

CREATE TABLE IF NOT EXISTS danh_muc (
    ma_dm INT AUTO_INCREMENT PRIMARY KEY,
    ten_dm VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET @has_ma_dm = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
                  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'san_pham' AND COLUMN_NAME = 'ma_dm');
SET @sql = IF(@has_ma_dm = 0, 'ALTER TABLE san_pham ADD COLUMN ma_dm INT NULL', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @has_stock_fk = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
                     WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'san_pham'
                       AND COLUMN_NAME = 'ma_dm' AND REFERENCED_TABLE_NAME = 'danh_muc');
SET @sql = IF(@has_stock_fk = 0, 'ALTER TABLE san_pham ADD CONSTRAINT fk_san_pham_danh_muc FOREIGN KEY (ma_dm) REFERENCES danh_muc(ma_dm) ON UPDATE CASCADE ON DELETE RESTRICT', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @has_thanh_tien = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
                       WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'chi_tiet_hoa_don' AND COLUMN_NAME = 'thanh_tien');
SET @sql = IF(@has_thanh_tien = 0, 'ALTER TABLE chi_tiet_hoa_don ADD COLUMN thanh_tien DECIMAL(15,2) NOT NULL DEFAULT 0', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

CREATE TABLE IF NOT EXISTS tai_khoan (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    ho_ten VARCHAR(100) NOT NULL,
    vai_tro VARCHAR(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO tai_khoan(username, password, ho_ten, vai_tro) VALUES
('admin','admin123','Quản trị viên','ADMIN'),
('nhanvien','nv123','Nhân viên bán hàng','NHANVIEN'),
('ketoan','kt123','Nhân viên kế toán','KETOAN');
