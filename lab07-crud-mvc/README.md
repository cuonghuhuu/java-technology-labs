# Lab 7 - CRUD MVC

## Mô hình MVC

`Browser -> Servlet Controller -> Repository -> Model -> JSP/JSTL -> Response`

Repository dùng `List` trong bộ nhớ, không dùng database/JPA. Vì vậy mọi dữ liệu thêm/sửa/xóa sẽ được reset khi restart hoặc redeploy ứng dụng.

## Trạng thái bài thực hành

| Bài | Nội dung | Trạng thái |
| --- | --- | --- |
| 1–5 | CRUD SinhVien, đăng nhập và filter | Hoàn thành |
| 6 | CRUD Sách, tìm theo tên/tác giả, chi tiết | Hoàn thành |
| 7 | CRUD Sản phẩm, chi tiết, tìm kiếm, validate giá/số lượng | Hoàn thành |
| 8 | CRUD Lớp học, tìm mã/tên lớp | Hoàn thành |
| 9 | Nhập điểm, tính tổng kết và xếp loại | Hoàn thành |
| 10 | Giỏ hàng bằng HttpSession | Hoàn thành |
| 11 | Phân trang SinhVien 5 dòng/trang | Hoàn thành |
| 12 | AppListener và SessionListener | Hoàn thành |
| 13 | Tổng hợp MVC, menu và bảo vệ URL quản lý | Hoàn thành |

## Tài khoản demo

`admin / 123456`

## Công thức điểm đã chọn

Đề không quy định công thức, nên project chọn công thức đơn giản: `Tổng kết = Chuyên cần × 10% + Giữa kỳ × 30% + Cuối kỳ × 60%`.

Xếp loại: A (>= 8.5), B (>= 7.0), C (>= 5.5), D (>= 4.0), F (< 4.0).

## Build và deploy

```bash
mvn clean package
```

Deploy file `target/lab07-crud-mvc.war` lên Tomcat 10.1.x (Java 17). Truy cập `/lab07-crud-mvc/`.
