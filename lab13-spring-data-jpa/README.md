# BÀI THỰC HÀNH LAB 13 - CÔNG NGHỆ JAVA (IT3242)
## CHƯƠNG 4: PHÁT TRIỂN ỨNG DỤNG VỚI SPRING FRAMEWORK
### Kết nối cơ sở dữ liệu với Spring Data JPA

---

## 1. Giới thiệu dự án
Dự án **Lab 13: lab13-spring-data-jpa** thuộc học phần **Công nghệ Java (IT3242)** tại **Trường Đại học Công nghệ Đông Á (EAUT)**.
Dự án tập trung nghiên cứu và xây dựng ứng dụng web hoàn chỉnh kết nối cơ sở dữ liệu quan hệ sử dụng **Spring Data JPA**, **Hibernate ORM**, hỗ trợ cả **H2 In-Memory Database** và **MySQL Server**, phân tách kiến trúc 3 tầng chuẩn (**Controller - Service - Repository**) và các thao tác **CRUD (Create - Read - Update - Delete)** kèm chức năng tìm kiếm dữ liệu.

- **Sinh viên thực hiện:** Lê Văn Cường
- **Mã sinh viên:** 20231768
- **Lớp chuyên ngành:** D18CNPM01

---

## 2. Công nghệ sử dụng
- **Ngôn ngữ:** Java 17+ (tương thích Java 21, Java 25 LTS).
- **Framework nền tảng:** Spring Boot 3.2.5
  - `spring-boot-starter-web`: Spring MVC, DispatcherServlet, Embedded Tomcat Server.
  - `spring-boot-starter-thymeleaf`: Template Engine hiển thị giao diện HTML động phía server.
  - `spring-boot-starter-data-jpa`: Hibernate, JPA Provider, JpaRepository, tự động sinh SQL.
  - `spring-boot-starter-validation`: Jakarta Validation / Hibernate Validator (@NotBlank, @Size, @Email, @NotNull...).
  - `com.h2database:h2`: Cơ sở dữ liệu bộ nhớ H2 In-Memory tốc độ cao, hỗ trợ H2 Web Console.
  - `com.mysql:mysql-connector-j`: JDBC Driver kết nối hệ quản trị cơ sở dữ liệu MySQL (Bài 10).
  - `spring-boot-devtools`: Hot-reload và tự động restart ứng dụng khi sửa mã nguồn.
  - `spring-boot-starter-test`: Kiểm thử tự động với JUnit 5, AssertJ, MockMvc, DataJpaTest.
- **Công cụ build:** Apache Maven 3.9+
- **Giao diện (UI/UX):** Bootstrap 5.3 + Bootstrap Icons + Custom Responsive CSS.

---

## 3. Cấu trúc dự án chuẩn
```
lab13-spring-data-jpa/
├── pom.xml                                   # Cấu hình Maven dependencies & plugins
├── README.md                                 # Hướng dẫn dự án & kiểm thử
├── BAO_CAO_LAB13.md                          # Báo cáo chi tiết nghiệm thu bài lab
└── src/
    ├── main/
    │   ├── java/vn/edu/eaut/lab13/
    │   │   ├── Lab13Application.java         # Lớp khởi chạy Spring Boot Main
    │   │   ├── config/
    │   │   │   └── DataInitializer.java      # Tự động nạp dữ liệu mẫu sinh viên và môn học
    │   │   ├── controller/
    │   │   │   ├── HomeController.java        # Điều hướng trang chủ (/) về /students
    │   │   │   ├── StudentController.java     # Controller CRUD & tìm kiếm sinh viên (Bài 5, 6, 7)
    │   │   │   └── CourseController.java      # Controller CRUD & tìm kiếm môn học (Bài 9)
    │   │   ├── entity/
    │   │   │   ├── Student.java               # Thực thể JPA ánh xạ bảng students (Bài 2)
    │   │   │   └── Course.java                # Thực thể JPA ánh xạ bảng courses (Bài 8)
    │   │   ├── repository/
    │   │   │   ├── StudentRepository.java     # Repository Spring Data JPA cho Student (Bài 3)
    │   │   │   └── CourseRepository.java      # Repository Spring Data JPA cho Course (Bài 9)
    │   │   └── service/
    │   │       ├── StudentService.java        # Service nghiệp vụ sinh viên (Bài 4)
    │   │       └── CourseService.java         # Service nghiệp vụ môn học (Bài 9)
    │   └── resources/
    │       ├── application.properties        # Cấu hình H2 Database & H2 Console (Bài 1, 6)
    │       ├── application-mysql.properties  # Cấu hình MySQL profile (Bài 10)
    │       ├── schema-mysql.sql              # Script khởi tạo database và dữ liệu mẫu MySQL
    │       ├── static/
    │       │   └── css/
    │       │       └── style.css              # Tùy chỉnh CSS giao diện Bootstrap hiện đại
    │       └── templates/
    │           ├── fragments/                 # Fragment giao diện dùng chung
    │           │   ├── navbar.html            # Thanh điều hướng (Sinh viên, Môn học, H2 Console)
    │           │   └── footer.html            # Chân trang thông tin học phần
    │           ├── students/
    │           │   ├── list.html              # Bảng danh sách sinh viên & tìm kiếm (Bài 5, 7)
    │           │   └── form.html              # Form thêm mới & sửa thông tin sinh viên (Bài 5, 6)
    │           └── courses/
    │               ├── list.html              # Bảng danh sách môn học & tìm kiếm (Bài 9)
    │               └── form.html              # Form thêm mới & sửa thông tin môn học (Bài 9)
    └── test/
        └── java/vn/edu/eaut/lab13/
            ├── Lab13ApplicationTests.java    # Kiểm thử Spring Application Context
            ├── repository/
            │   ├── StudentRepositoryTest.java # DataJpaTest cho StudentRepository
            │   └── CourseRepositoryTest.java  # DataJpaTest cho CourseRepository
            └── controller/
                ├── StudentControllerTest.java # MockMvc Test CRUD và tìm kiếm sinh viên
                └── CourseControllerTest.java  # MockMvc Test CRUD môn học
```

---

## 4. Hướng dẫn chạy ứng dụng

### 4.1. Môi trường mặc định: H2 In-Memory Database
1. Mở terminal tại thư mục `lab13-spring-data-jpa`:
   ```bash
   mvn spring-boot:run
   ```
2. Truy cập ứng dụng tại trình duyệt:
   - Danh sách sinh viên: [http://localhost:8080/students](http://localhost:8080/students)
   - Thêm mới sinh viên: [http://localhost:8080/students/create](http://localhost:8080/students/create)
   - Danh sách môn học: [http://localhost:8080/courses](http://localhost:8080/courses)
   - Thêm mới môn học: [http://localhost:8080/courses/create](http://localhost:8080/courses/create)
   - H2 Database Web Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
     - **JDBC URL:** `jdbc:h2:mem:eautdb`
     - **User Name:** `sa`
     - **Password:** *(để trống)*

### 4.2. Môi trường triển khai MySQL (Bài 10)
1. Đảm bảo MySQL Server đang chạy và tạo database `eautdb` (hoặc import script `schema-mysql.sql`).
2. Khởi chạy ứng dụng với profile `mysql`:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=mysql
   ```

---

## 5. Kiểm thử tự động (Unit & Integration Tests)
Chạy toàn bộ 18 test cases bằng lệnh Maven:
```bash
mvn test
```
Tất cả các tầng Controller, Service và Data JPA Repository đều được kiểm thử và vượt qua 100%.
