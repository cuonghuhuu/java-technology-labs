# Lab 4 - SwingWorker (IT3242)

Project thực hành Công nghệ Java sử dụng Java 17, Maven, Java Swing và
`SwingWorker`. Ứng dụng gồm một menu chung để mở 10 bài thực hành về xử lý sự
kiện, EDT, đa luồng, thanh tiến độ, chọn file và bảng dữ liệu.

| Bài | Nội dung |
|---:|---|
| 1 | Đồng hồ đếm ngược với `publish/process` |
| 2 | Mô phỏng tải dữ liệu và cập nhật tiến độ |
| 3 | Tính tổng các số nguyên tố nhỏ hơn N |
| 4 | Tính Fibonacci bằng memoization |
| 5 | Chọn file và đếm số dòng |
| 6 | Bắt đầu và hủy tác vụ nền |
| 7 | Tìm từ khóa trong file văn bản |
| 8 | Đọc CSV và thống kê điểm sinh viên |
| 9 | Mô phỏng tải danh sách sản phẩm |
| 10 | Quản lý sản phẩm và đọc/ghi CSV |

## Build và chạy

Yêu cầu Maven và JDK 17 trở lên. Project được biên dịch với mức tương thích
Java 17, kể cả khi chạy Maven bằng JDK 25.

```powershell
mvn clean compile
mvn exec:java
```

Các file demo nằm trong thư mục `data/`. Khi chạy bài đọc file, chọn file bằng
`JFileChooser`, không cần sửa đường dẫn trong mã nguồn.
