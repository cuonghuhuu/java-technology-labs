# Lab 5 - MiniShop Swing JDBC

## Công nghệ

Java 17, Swing, JDBC, MySQL 8.4, Maven.

## Kiến trúc

Ứng dụng tách thành `Model -> BUS -> DAL -> DBHelper -> MySQL`. GUI không chứa SQL; các thao tác load/tìm kiếm/thống kê dùng `SwingWorker`.

## Database

Database mặc định là `minishop_db`. Chạy `database/minishop_db.sql` cho database mới. Với database Lab 5 đã có các bảng cơ bản, chạy thêm `database/lab5_upgrade.sql`. Các script không dùng `DROP DATABASE`.

## Cấu hình local

PowerShell:

```powershell
$env:MINISHOP_DB_PASSWORD="YOUR_PASSWORD"
```

Không ghi mật khẩu thật vào source hoặc README.

## Build và chạy

```powershell
mvn clean compile
mvn exec:java
```

## Chức năng

- CRUD sản phẩm, khách hàng và danh mục.
- Lập hóa đơn nhiều dòng, kiểm tra/trừ tồn kho bằng transaction.
- Thống kê doanh thu, hóa đơn lớn nhất và sản phẩm bán chạy.
- Xuất hóa đơn TXT UTF-8.
- Tìm kiếm sản phẩm, lọc danh mục và sắp xếp.
- Đăng nhập và phân quyền `ADMIN`, `NHANVIEN`, `KETOAN`.

## Tài khoản demo

Các tài khoản mẫu trong SQL: `admin/admin123`, `nhanvien/nv123`, `ketoan/kt123`. Đây chỉ là credential demo của database local.
