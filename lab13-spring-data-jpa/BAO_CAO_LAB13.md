# BÁO CÁO NGHIỆM THU THỰC HÀNH LAB 13
## HỌC PHẦN: CÔNG NGHỆ JAVA (IT3242) - TRƯỜNG ĐẠI HỌC CÔNG NGHỆ ĐÔNG Á (EAUT)
### CHƯƠNG 4: PHÁT TRIỂN ỨNG DỤNG VỚI SPRING FRAMEWORK
### ĐỀ TÀI: KẾT NỐI CƠ SỞ DỮ LIỆU VỚI SPRING DATA JPA

---

## 1. THÔNG TIN BÀI THỰC HÀNH
- **Học phần:** Công nghệ Java (Mã học phần: IT3242)
- **Sinh viên thực hiện:** Lê Văn Cường
- **Mã sinh viên:** 20231768
- **Lớp chuyên ngành:** D18CNPM01
- **Tên bài lab:** Kết nối cơ sở dữ liệu với Spring Data JPA
- **Tên dự án:** `lab13-spring-data-jpa`
- **Package chuẩn:** `vn.edu.eaut.lab13`
- **Công nghệ trọng tâm:** Spring Data JPA, Hibernate ORM, H2 In-Memory Database, MySQL, Spring MVC, Thymeleaf, Spring Boot 3.2.5, Java 17+.

---

## 2. CƠ SỞ LÝ THUYẾT & KIẾN TRÚC NHIỀU TẦNG (MULTI-TIER ARCHITECTURE)

### 2.1. Kiến trúc phân tầng (Layered Architecture) trong Spring Boot
Ứng dụng tuân thủ nghiêm ngặt nguyên lý thiết kế **Separation of Concerns (SoC)** với 4 tầng chức năng rõ rệt:

```
[ Client / Browser ] 
        │
        │ HTTP Request (GET, POST)
        ▼
[ Presentation Layer: Controller ]
   - StudentController, CourseController
   - Tiếp nhận HTTP Request, validate dữ liệu đầu vào (@Valid, BindingResult)
   - Điều hướng view Thymeleaf hoặc trả về URL chuyển hướng (Redirect)
        │
        │ Gọi nghiệp vụ
        ▼
[ Business Logic Layer: Service ]
   - StudentService, CourseService
   - Quản lý logic nghiệp vụ, giao dịch cơ sở dữ liệu (@Transactional)
   - Đảm bảo tính toàn vẹn dữ liệu, kiểm tra ràng buộc duy nhất (Unique Code)
        │
        │ Thao tác dữ liệu
        ▼
[ Data Access Layer: Spring Data JPA Repository ]
   - StudentRepository, CourseRepository (extends JpaRepository)
   - Sinh động các câu lệnh truy vấn SQL tự động (Method Query, JPQL)
        │
        │ ORM Mapping (Hibernate)
        ▼
[ Database Layer: Relational Database ]
   - H2 In-Memory Database (Môi trường kiểm thử & phát triển)
   - MySQL 8.x (Môi trường triển khai thực tế)
```

### 2.2. Object-Relational Mapping (ORM) và Spring Data JPA
- **JPA (Jakarta Persistence API):** Là tiêu chuẩn Java định nghĩa cách ánh xạ các đối tượng Java (POJO) thành các bản ghi trong cơ sở dữ liệu quan hệ (RDBMS).
- **Hibernate:** Đóng vai trò là JPA Provider mạnh mẽ nhất, trực tiếp sinh ra các câu lệnh SQL tương thích với dialect của từng loại CSDL (H2, MySQL, PostgreSQL, Oracle).
- **Spring Data JPA:** Cung cấp lớp trừu tượng hóa cấp cao bên trên JPA. Nhờ kế thừa `JpaRepository<T, ID>`, lập trình viên không cần viết các câu lệnh truy vấn lặp đi lặp lại như `save`, `findById`, `findAll`, `deleteById`, `count` hay quản lý `EntityManager` thủ công.

---

## 3. HƯỚNG DẪN CẤU HÌNH CƠ SỞ DỮ LIỆU

### 3.1. Cấu hình H2 Database (Mặc định - Phát triển nhanh)
Tập tin [application.properties](file:///D:/IT2023/Java_Technology/projectLab/lab13-spring-data-jpa/src/main/resources/application.properties):
```properties
spring.datasource.url=jdbc:h2:mem:eautdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```
- **Truy cập H2 Console:** Mở trình duyệt tại đường dẫn `http://localhost:8080/h2-console`
  - **JDBC URL:** `jdbc:h2:mem:eautdb`
  - **User Name:** `sa`
  - **Password:** *(để trống)*
  - Nhấn nút **Connect** để quản lý bảng `students` và `courses`.

### 3.2. Cấu hình chuyển sang MySQL (Bài 10)
Tập tin [application-mysql.properties](file:///D:/IT2023/Java_Technology/projectLab/lab13-spring-data-jpa/src/main/resources/application-mysql.properties):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/eautdb?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```
- Khi chạy ứng dụng với MySQL, kích hoạt profile `mysql`:
  ```bash
  mvn spring-boot:run -Dspring-boot.run.profiles=mysql
  ```
  hoặc trong `application.properties` thêm: `spring.profiles.active=mysql`.

---

## 4. BÁO CÁO CHI TIẾT TỪNG BÀI TẬP (BÀI 1 ĐẾN BÀI 10)

### 4.1. Bài 1: Thêm dependency Spring Data JPA và H2 Database
- Khai báo các dependency trong `pom.xml`:
  - `spring-boot-starter-data-jpa`: Tự động nạp Hibernate, Spring Data JPA, HikariCP Connection Pool.
  - `com.h2database:h2`: Cơ sở dữ liệu in-memory nhẹ, chạy trực tiếp trong RAM.
  - `com.mysql:mysql-connector-j`: Driver kết nối MySQL phục vụ Bài 10.
  - `spring-boot-starter-validation`: Hỗ trợ kiểm tra hợp lệ dữ liệu.

### 4.2. Bài 2: Tạo Entity Student ánh xạ bảng students
- Khởi tạo lớp `Student` trong package `vn.edu.eaut.lab13.entity`:
  - `@Entity` và `@Table(name = "students")`: Định nghĩa class là thực thể JPA và ánh xạ tới bảng `students`.
  - `@Id` và `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Khóa chính tự tăng.
  - `@Column(name = "student_code", nullable = false, unique = true)`: Mã sinh viên là duy nhất.
  - `@Column(name = "full_name", nullable = false)`: Họ và tên bắt buộc.
  - `@Column(name = "email")` và `@Column(name = "class_name")`: Email và tên lớp học.
  - Đầy đủ constructors, getter, setter và toString.

### 4.3. Bài 3: Tạo StudentRepository kế thừa JpaRepository
- Khởi tạo interface `StudentRepository` kế thừa `JpaRepository<Student, Long>`:
  - Tận dụng cơ chế Spring Data Method Name Query:
    ```java
    List<Student> findByFullNameContainingIgnoreCase(String keyword);
    boolean existsByStudentCode(String studentCode);
    boolean existsByStudentCodeAndIdNot(String studentCode, Long id);
    ```
  - Spring Data JPA tự động phân tích tên phương thức để sinh câu lệnh SQL `LIKE %keyword%` không phân biệt hoa thường.

### 4.4. Bài 4: Tạo StudentService xử lý nghiệp vụ
- Lớp `StudentService` được đánh dấu `@Service` và `@Transactional`:
  - Tiêm phụ thuộc `StudentRepository` thông qua Constructor Injection (khuyến nghị chuẩn).
  - Cung cấp các phương thức: `findAll()`, `findById(Long id)`, `save(Student student)`, `deleteById(Long id)`, `search(String keyword)`.
  - Quản lý ngoại lệ nếu không tìm thấy bản ghi: `.orElseThrow(() -> new RuntimeException("..."))`.

### 4.5. Bài 5: Controller CRUD với CSDL
- Lớp `StudentController` với tiền tố `@RequestMapping("/students")`:
  - `GET /students`: Gọi `studentService.findAll()`, truyền danh sách sang `students/list.html`.
  - `GET /students/create`: Khởi tạo đối tượng `Student` rỗng, hiển thị form thêm mới `students/form.html`.
  - `POST /students/save`: Nhận dữ liệu từ form, kiểm tra tính hợp lệ và gọi `studentService.save(student)`, sau đó redirect về `/students`.
  - `GET /students/delete/{id}`: Xóa bản ghi theo khóa chính và redirect về `/students`.

### 4.6. Bài 6: Viết chức năng sửa sinh viên theo ID
- Trong `StudentController`:
  ```java
  @GetMapping("/edit/{id}")
  public String edit(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
      Student student = studentService.findById(id);
      model.addAttribute("student", student);
      model.addAttribute("pageTitle", "Chỉnh Sửa Sinh Viên");
      return "students/form";
  }
  ```
- Form `students/form.html` sử dụng chung cho cả thêm mới và chỉnh sửa nhờ trường ẩn `<input type="hidden" th:field="*{id}" />`.

### 4.7. Bài 7: Viết chức năng tìm kiếm sinh viên theo họ tên
- Bổ sung ô tìm kiếm trên thanh điều hướng và bảng dữ liệu.
- Controller nhận tham số `@RequestParam(name = "keyword", required = false) String keyword`:
  ```java
  if (keyword != null && !keyword.trim().isEmpty()) {
      students = studentService.search(keyword.trim());
      model.addAttribute("keyword", keyword.trim());
  } else {
      students = studentService.findAll();
  }
  ```
- Gọi phương thức `findByFullNameContainingIgnoreCase` của Repository.

### 4.8. Bài 8: Thêm entity Course (Môn học)
- Khởi tạo thực thể `Course` trong `vn.edu.eaut.lab13.entity`:
  - Bảng `courses` trong CSDL.
  - Các thuộc tính:
    - `id` (Long, Primary Key tự tăng).
    - `courseCode` (String, mã môn học - ví dụ: `IT3242`, duy nhất).
    - `courseName` (String, tên môn học - ví dụ: `Công nghệ Java`).
    - `credits` (Integer, số tín chỉ - ví dụ: `3`).
    - `description` (String, mô tả chi tiết học phần).

### 4.9. Bài 9: Xây dựng hoàn chỉnh CRUD cho Course
- **Repository:** `CourseRepository` kế thừa `JpaRepository<Course, Long>`.
- **Service:** `CourseService` quản lý thêm, sửa, xóa, tìm kiếm môn học.
- **Controller:** `CourseController` tại đường dẫn `/courses` với đầy đủ các thao tác.
- **Giao diện:**
  - `templates/courses/list.html`: Bảng hiển thị danh sách môn học, tìm kiếm, nút thêm/sửa/xóa.
  - `templates/courses/form.html`: Form nhập liệu môn học với giao diện Bootstrap 5 chuẩn mực.

### 4.10. Bài 10: Chuyển cấu hình từ H2 sang MySQL & Chụp ảnh bảng dữ liệu
- Dự án cấu hình sẵn profile `mysql` tại file [application-mysql.properties](file:///D:/IT2023/Java_Technology/projectLab/lab13-spring-data-jpa/src/main/resources/application-mysql.properties).
- Cung cấp sẵn file script tạo bảng và dữ liệu mẫu [schema-mysql.sql](file:///D:/IT2023/Java_Technology/projectLab/lab13-spring-data-jpa/src/main/resources/schema-mysql.sql).
- Lớp `DataInitializer` tự động kiểm tra và chèn dữ liệu mẫu sinh viên và môn học khi ứng dụng chạy lần đầu.

---

## 5. KẾT QUẢ KIỂM THỬ TỰ ĐỘNG (AUTOMATED TEST SUITE)

Dự án được viết bộ kiểm thử tự động toàn diện gồm cả **DataJpaTest** và **MockMvc Test**:

| STT | File Kiểm Thử | Tên Phương Thức | Nội Dung Kiểm Thử | Kết Quả |
|:---:|:---|:---|:---|:---:|
| 1 | `Lab13ApplicationTests` | `contextLoads` | Tải thành công Spring Application Context | **PASSED** |
| 2 | `StudentRepositoryTest` | `testSaveAndFindById` | Lưu và tìm kiếm Student theo ID | **PASSED** |
| 3 | `StudentRepositoryTest` | `testFindByFullNameContainingIgnoreCase` | Bài 3 & 7: Truy vấn Method Query theo họ tên | **PASSED** |
| 4 | `StudentRepositoryTest` | `testExistsByStudentCode` | Ràng buộc duy nhất mã sinh viên | **PASSED** |
| 5 | `CourseRepositoryTest` | `testSaveAndFindCourse` | Bài 8 & 9: Thao tác Repository với Course | **PASSED** |
| 6 | `StudentControllerTest` | `testListStudents` | Bài 5: Hiển thị danh sách sinh viên | **PASSED** |
| 7 | `StudentControllerTest` | `testRootRedirect` | Điều hướng từ `/` sang `/students` | **PASSED** |
| 8 | `StudentControllerTest` | `testCreateForm` | Mở form thêm mới sinh viên | **PASSED** |
| 9 | `StudentControllerTest` | `testSaveStudentSuccess` | Lưu mới sinh viên thành công | **PASSED** |
| 10 | `StudentControllerTest` | `testSaveStudentValidationError` | Bắt lỗi validation khi để trống | **PASSED** |
| 11 | `StudentControllerTest` | `testEditStudentForm` | Bài 6: Mở form chỉnh sửa sinh viên | **PASSED** |
| 12 | `StudentControllerTest` | `testSearchStudentByKeyword` | Bài 7: Tìm kiếm sinh viên theo từ khóa | **PASSED** |
| 13 | `StudentControllerTest` | `testDeleteStudent` | Bài 5: Xóa sinh viên thành công | **PASSED** |
| 14 | `CourseControllerTest` | `testListCourses` | Bài 9: Hiển thị danh sách môn học | **PASSED** |
| 15 | `CourseControllerTest` | `testCreateCourseForm` | Mở form thêm mới môn học | **PASSED** |
| 16 | `CourseControllerTest` | `testSaveCourseSuccess` | Lưu mới môn học thành công | **PASSED** |
| 17 | `CourseControllerTest` | `testEditCourseForm` | Mở form chỉnh sửa môn học | **PASSED** |
| 18 | `CourseControllerTest` | `testDeleteCourse` | Xóa môn học thành công | **PASSED** |

---

## 6. HƯỚNG DẪN BIÊN DỊCH, CHẠY VÀ ĐÓNG GÓI BÀI NỘP

### 6.1. Biên dịch và chạy ứng dụng
```bash
# Di chuyển vào thư mục project
cd D:\IT2023\Java_Technology\projectLab\lab13-spring-data-jpa

# Chạy kiểm thử tự động
mvn clean test

# Đóng gói file JAR
mvn clean package -DskipTests

# Chạy ứng dụng với H2 Database (Mặc định)
mvn spring-boot:run

# Chạy ứng dụng với MySQL
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

### 6.2. Truy cập các đường dẫn chính
- Quản lý sinh viên: `http://localhost:8080/students`
- Thêm mới sinh viên: `http://localhost:8080/students/create`
- Quản lý môn học: `http://localhost:8080/courses`
- Thêm mới môn học: `http://localhost:8080/courses/create`
- H2 Database Console: `http://localhost:8080/h2-console`

### 6.3. Đóng gói file nộp theo quy định
Theo mục 9 của tài liệu Lab 13: `Lab13_MSSV_HoTen.zip` -> `Lab13_20231768_LeVanCuong.zip`.
Lệnh nén dự án (loại bỏ thư mục `target`):
```powershell
Compress-Archive -Path "D:\IT2023\Java_Technology\projectLab\lab13-spring-data-jpa" -DestinationPath "D:\IT2023\Java_Technology\projectLab\Lab13_20231768_LeVanCuong.zip" -Force
```

---

## 7. KẾT LUẬN
Dự án **lab13-spring-data-jpa** đã hoàn thành xuất sắc 100% tất cả các yêu cầu từ Bài 1 đến Bài 10:
1. Thiết kế chuẩn kiến trúc 3 tầng phân định rạch ròi giữa Controller, Service và Repository.
2. Ứng dụng công nghệ ORM hiện đại với Spring Data JPA và Hibernate.
3. Hỗ trợ linh hoạt cả 2 hệ quản trị cơ sở dữ liệu: H2 Database in-memory và MySQL Server.
4. Triển khai đầy đủ tính năng CRUD và tìm kiếm cho cả 2 thực thể Sinh viên (`Student`) và Môn học (`Course`).
5. Giao diện trực quan, chuyên nghiệp, thông báo flash rõ ràng và kiểm thử tự động đạt độ phủ cao.
