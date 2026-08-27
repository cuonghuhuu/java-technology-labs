# BÀI THỰC HÀNH LAB 11 - CÔNG NGHỆ JAVA (IT3242)
## CHƯƠNG 4: PHÁT TRIỂN ỨNG DỤNG VỚI SPRING FRAMEWORK
### Khởi tạo ứng dụng Spring Boot và giao diện Thymeleaf

---

## 1. Giới thiệu dự án
Dự án **Lab 11** thuộc học phần **Công nghệ Java (IT3242)** tại **Trường Đại học Công nghệ Đông Á (EAUT)**. Dự án xây dựng một ứng dụng web hoàn chỉnh sử dụng hệ sinh thái **Spring Boot 3.x** và công nghệ hiển thị giao diện phía máy chủ **Thymeleaf Template Engine** theo kiến trúc chuẩn **MVC (Model - View - Controller)**.

---

## 2. Kiến trúc & Công nghệ sử dụng
- **Ngôn ngữ & Phiên bản:** Java 17+ (Tương thích Java 21, Java 25 LTS).
- **Framework nền tảng:** Spring Boot 3.2.5
  - `spring-boot-starter-web`: Cung cấp Spring MVC, Jackson JSON, và Embedded Tomcat Server.
  - `spring-boot-starter-thymeleaf`: Tích hợp Thymeleaf Engine để render giao diện HTML động.
  - `spring-boot-devtools`: Hỗ trợ tự động reload (LiveReload) khi chỉnh sửa mã nguồn.
  - `spring-boot-starter-test`: Kiểm thử tự động với JUnit 5 và MockMvc.
- **Công cụ Build & Quản lý:** Apache Maven 3.9+
- **Giao diện & Trải nghiệm (UI/UX):** 
  - Bootstrap 5.3 + Bootstrap Icons
  - Custom CSS (`src/main/resources/static/css/style.css`)
  - Layout dạng Thymeleaf Fragments tái sử dụng (`navbar.html`, `footer.html`).

---

## 3. Cấu trúc thư mục chuẩn
```
lab11-springboot-thymeleaf/
├── pom.xml                                   # Cấu hình Maven dependencies & Spring Boot plugin
├── README.md                                 # Hướng dẫn sử dụng & kiểm thử
├── BAO_CAO_LAB11.md                          # Báo cáo chi tiết nghiệm thu bài Lab 11
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── vn/edu/eaut/lab11/
│   │   │       ├── Lab11Application.java     # Lớp khởi chạy Spring Boot Main Class
│   │   │       ├── controller/               # Lớp Controller điều hướng URL & xử lý Model
│   │   │       │   ├── HomeController.java        # /, /about, /contact (Bài 2, 5, 6)
│   │   │       │   ├── StudentController.java     # /students (Bài 4)
│   │   │       │   └── CourseController.java      # /courses (Bài 8, 9)
│   │   │       └── model/                    # Đối tượng dữ liệu (POJO/JavaBeans)
│   │   │           ├── Student.java               # Sinh viên (Bài 3)
│   │   │           ├── Course.java                # Khóa học (Bài 8)
│   │   │           └── ContactInfo.java           # Thông tin liên hệ khoa (Bài 6)
│   │   └── resources/
│   │       ├── application.properties        # Cấu hình cổng 8080, Thymeleaf & logs
│   │       ├── static/                       # Tài nguyên tĩnh (CSS, JS, Images)
│   │       │   └── css/
│   │       │       └── style.css                  # CSS tùy biến hiện đại (Bài 10)
│   │       └── templates/                    # Giao diện HTML Thymeleaf
│   │           ├── fragments/                # Component layout tái sử dụng (Bài 7)
│   │           │   ├── navbar.html                # Menu điều hướng dùng chung
│   │           │   └── footer.html                # Chân trang thông tin học phần
│   │           ├── index.html                # Trang chủ (Bài 2)
│   │           ├── about.html                # Trang giới thiệu (Bài 5)
│   │           ├── students.html             # Trang danh sách sinh viên (Bài 4)
│   │           ├── courses.html              # Trang danh sách khóa học (Bài 9)
│   │           └── contact.html              # Trang liên hệ khoa/bộ môn (Bài 6)
│   └── test/
│       └── java/
│           └── vn/edu/eaut/lab11/
│               └── Lab11ApplicationTests.java # Bộ test tự động MockMvc cho cả 5 endpoint
```

---

## 4. Hướng dẫn cách chạy ứng dụng

### Cách 1: Chạy bằng dòng lệnh Maven (Khuyên dùng)
Mở cửa sổ Terminal / PowerShell tại thư mục `lab11-springboot-thymeleaf` và chạy:
```powershell
mvn spring-boot:run
```

### Cách 2: Chạy trực tiếp trong VS Code / IntelliJ IDEA
1. Mở file [Lab11Application.java](file:///D:/IT2023/Java_Technology/projectLab/lab11-springboot-thymeleaf/src/main/java/vn/edu/eaut/lab11/Lab11Application.java).
2. Nhấn nút **Run** (hoặc tổ hợp phím `Shift + F10` trên IntelliJ, `F5` / `Run Java` trên VS Code).

### Cách 3: Đóng gói thành file JAR độc lập và chạy
```powershell
mvn clean package
java -jar target/lab11-springboot-thymeleaf.jar
```

---

## 5. Danh sách các đường dẫn (URLs) kiểm thử

Sau khi khởi chạy ứng dụng thành công, truy cập bằng trình duyệt web:

| STT | Tên trang / Bài tập | Đường dẫn URL | Mô tả & Dữ liệu hiển thị |
|:---:|:---|:---|:---|
| 1 | **Trang chủ** *(Bài 2)* | [http://localhost:8080/](http://localhost:8080/) | Banner chào mừng, danh sách tính năng cốt lõi của Spring Boot. |
| 2 | **Sinh viên** *(Bài 4)* | [http://localhost:8080/students](http://localhost:8080/students) | Bảng sinh viên mẫu (`SV001`, `SV002`, `SV003`,...) hiển thị qua `th:each`. |
| 3 | **Khóa học** *(Bài 8, 9)* | [http://localhost:8080/courses](http://localhost:8080/courses) | Bảng 5 khóa học mẫu gồm mã môn, tên môn, số tín chỉ, giảng viên. |
| 4 | **Giới thiệu** *(Bài 5)* | [http://localhost:8080/about](http://localhost:8080/about) | Giới thiệu môn học "Công nghệ Java" và nội dung Chương 4. |
| 5 | **Liên hệ** *(Bài 6)* | [http://localhost:8080/contact](http://localhost:8080/contact) | Thông tin liên hệ Khoa CNTT - EAUT & Form gửi thắc mắc. |

---

## 6. Tổng kết 10 bài tập đã hoàn thành trong Lab 11
- [x] **Bài 1:** Khởi tạo project Spring Boot có `Spring Web`, `Thymeleaf`, `DevTools`, chạy trên cổng `8080`.
- [x] **Bài 2:** Tạo trang chủ `/` trả về `index.html` nhận dữ liệu `title`, `message` từ `HomeController`.
- [x] **Bài 3:** Xây dựng Model `Student.java` đầy đủ thuộc tính, constructor, getter/setter.
- [x] **Bài 4:** Xây dựng Controller `/students` và template `students.html` hiển thị danh sách sinh viên bằng `th:each`.
- [x] **Bài 5:** Xây dựng Controller `/about` và template `about.html` hiển thị tên học phần và chương học.
- [x] **Bài 6:** Xây dựng trang `/contact` hiển thị thông tin liên hệ Khoa/Bộ môn CNTT.
- [x] **Bài 7:** Tạo menu điều hướng chung (Navbar) liên kết giữa Home, About, Students, Courses, Contact.
- [x] **Bài 8:** Tạo danh sách 5 khóa học mẫu với Model `Course.java` (mã môn, tên môn, tín chỉ).
- [x] **Bài 9:** Xây dựng trang `/courses` hiển thị danh sách 5 khóa học bằng Thymeleaf.
- [x] **Bài 10:** Tùy biến giao diện chuyên nghiệp bằng CSS riêng tại `static/css/style.css`.
