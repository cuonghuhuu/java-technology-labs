# Dự Án Java Web: javalabTuyen

Bài tập môn **Công nghệ Java** - Quản lý, kiểm tra dữ liệu, tính điểm tổng kết và xếp loại sinh viên.

---

## 📋 Yêu Cầu Bài Tập & Chức Năng (Theo Đề Bài)

| Chức năng | Mô tả yêu cầu | Ví dụ minh họa |
| :--- | :--- | :--- |
| **1. Nhập dữ liệu** | Nhập Mã sinh viên, Họ tên, Điểm chuyên cần, Điểm giữa kỳ, Điểm cuối kỳ. | `SV001`, `Nguyễn Văn An`, `8`, `7`, `9` |
| **2. Tính điểm tổng kết** | `Điểm tổng kết` = `chuyên cần × 10%` + `giữa kỳ × 30%` + `cuối kỳ × 60%`. | `8 × 0.1 + 7 × 0.3 + 9 × 0.6 = 8.3` |
| **3. Xếp loại** | **A**: từ 8.5 \| **B**: từ 7.0 \| **C**: từ 5.5 \| **D**: từ 4.0 \| **F**: dưới 4.0. | `8.3 → B` |
| **4. Hiển thị kết quả** | In bảng thông tin sinh viên, điểm tổng kết và xếp loại. | `SV001 - Nguyễn Văn A - 8.30 - B` |
| **5. Kiểm tra dữ liệu** | Nếu điểm ngoài khoảng **0-10**, chương trình báo lỗi và yêu cầu nhập lại. | `Điểm 12 → không hợp lệ` |

---

## 🛠️ Công Nghệ Sử Dụng

- **Ngôn ngữ**: Java (JDK 17 trở lên)
- **Công cụ quản lý dự án**: Apache Maven
- **Web Server**: Apache Tomcat 10.1+ (hỗ trợ `jakarta.servlet`)
- **Kiến trúc**: MVC (Model - View - Controller)
  - **Model**: `com.javalab.tuyen.model.Student`
  - **Service / Validation**: `com.javalab.tuyen.service.StudentService`
  - **Controller**: `com.javalab.tuyen.servlet.StudentServlet`
  - **View**: JSP (`index.jsp`, `result.jsp`) + JSTL + Custom CSS

---

## 📁 Cấu Trúc Dự Án

```
javalabTuyen/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── javalab/
        │           └── tuyen/
        │               ├── model/
        │               │   └── Student.java
        │               ├── service/
        │               │   └── StudentService.java
        │               └── servlet/
        │                   └── StudentServlet.java
        └── webapp/
            ├── WEB-INF/
            │   └── web.xml
            ├── css/
            │   └── style.css
            ├── index.jsp
            └── result.jsp
```

---

## 🚀 Hướng Dẫn Biên Dịch & Chạy Ứng Dụng

### Cách 1: Build file WAR & Deploy vào Apache Tomcat 10.1+

1. **Biên dịch dự án**:
   ```bash
   cd D:\IT2023\Java_Technology\projectLab\javalabTuyen
   mvn clean package
   ```
   File `javalabTuyen.war` sẽ được tạo tại thư mục `target/javalabTuyen.war`.

2. **Copy file WAR vào Tomcat**:
   Chép file `target/javalabTuyen.war` vào thư mục `webapps/` của Apache Tomcat.

3. **Truy cập ứng dụng**:
   Mở trình duyệt và truy cập:
   👉 `http://localhost:8080/javalabTuyen/`

---

### Cách 2: Chạy trực tiếp bằng Maven Cargo Plugin

Trong thư mục dự án `javalabTuyen`, chạy lệnh:
```bash
mvn cargo:run
```
Sau đó truy cập trình duyệt tại:
👉 `http://localhost:8080/javalabTuyen/`

---

### Cách 3: Chạy bằng VS Code / Eclipse / IntelliJ IDEA

- Mở thư mục `javalabTuyen` bằng IDE.
- Cấu hình server **Apache Tomcat 10.1+**.
- Run on Server.
