# BÀI THỰC HÀNH LAB 12 - CÔNG NGHỆ JAVA (IT3242)
## CHƯƠNG 4: PHÁT TRIỂN ỨNG DỤNG VỚI SPRING FRAMEWORK
### Phát triển ứng dụng web với Spring MVC

---

## 1. Giới thiệu dự án
Dự án **Lab 12: lab12-spring-mvc-student** thuộc học phần **Công nghệ Java (IT3242)** tại **Trường Đại học Công nghệ Đông Á (EAUT)**.
Dự án tập trung nghiên cứu và xây dựng ứng dụng web hoàn chỉnh sử dụng mô hình kiến trúc **Spring MVC (Model - View - Controller)** kết hợp với **Thymeleaf Template Engine**, cơ chế **Data Binding (@ModelAttribute)**, **Bean Validation (JSR-380 / Hibernate Validator)** và các thao tác **CRUD (Create - Read - Update - Delete)** giả lập trên bộ nhớ RAM.

---

## 2. Công nghệ sử dụng
- **Ngôn ngữ:** Java 17+ (tương thích Java 21, Java 25 LTS).
- **Framework nền tảng:** Spring Boot 3.2.5
  - `spring-boot-starter-web`: Cung cấp Spring MVC, DispatcherServlet, Embedded Tomcat Server.
  - `spring-boot-starter-thymeleaf`: Template Engine hiển thị giao diện HTML động phía server.
  - `spring-boot-starter-validation`: Hibernate Validator hỗ trợ xác thực dữ liệu (@NotBlank, @Size, @Email, Custom duplicate validation).
  - `spring-boot-devtools`: Tự động tải lại khi có thay đổi mã nguồn.
  - `spring-boot-starter-test`: Kiểm thử tự động với JUnit 5, Hamcrest, MockMvc.
- **Công cụ build:** Apache Maven 3.9+
- **Giao diện (UI/UX):** Bootstrap 5.3 + Bootstrap Icons + Custom Responsive CSS.

---

## 3. Cấu trúc dự án chuẩn
```
lab12-spring-mvc-student/
├── pom.xml                                   # Cấu hình Maven dependencies & plugins
├── README.md                                 # Hướng dẫn dự án & kiểm thử
├── BAO_CAO_LAB12.md                          # Báo cáo chi tiết luồng request & kết quả nghiệm thu
└── src/
    ├── main/
    │   ├── java/vn/edu/eaut/lab12/
    │   │   ├── Lab12Application.java         # Lớp khởi chạy Spring Boot Main
    │   │   ├── controller/
    │   │   │   ├── HomeController.java        # Điều hướng trang chủ (/) về /students
    │   │   │   └── StudentController.java     # Xử lý các request /students (Bài 3 -> 10)
    │   │   ├── model/
    │   │   │   └── Student.java               # Đối tượng sinh viên & validation (Bài 1)
    │   │   └── service/
    │   │       └── StudentService.java        # Service quản lý CRUD, tìm kiếm, kiểm tra trùng (Bài 2)
    │   └── resources/
    │       ├── application.properties        # Cấu hình cổng 8080, Thymeleaf UTF-8
    │       ├── static/
    │       │   └── css/
    │       │       └── style.css              # Tùy chỉnh CSS giao diện hiện đại
    │       └── templates/
    │           ├── fragments/                 # Fragment giao diện tái sử dụng
    │           │   ├── navbar.html            # Thanh điều hướng dùng chung
    │           │   └── footer.html            # Chân trang thông tin học phần
    │           └── students/
    │               ├── list.html              # Trang danh sách sinh viên & tìm kiếm (Bài 3, 9)
    │               ├── form.html              # Form thêm mới & chỉnh sửa (Bài 4, 5, 7, 10)
    │               └── detail.html            # Trang xem chi tiết thông tin sinh viên (Bài 6)
    └── test/
        └── java/vn/edu/eaut/lab12/
            ├── Lab12ApplicationTests.java    # Kiểm thử Spring Context
            └── StudentControllerTest.java     # Kiểm thử toàn diện MockMvc cho cả 10 bài tập
```

---

## 4. Chi tiết triển khai 10 bài tập

### Bài 1: Tạo Model Student
- Đường dẫn: `src/main/java/vn/edu/eaut/lab12/model/Student.java`
- Định nghĩa các thuộc tính: `id` (Long), `studentCode` (String), `fullName` (String), `email` (String), `className` (String).
- Khai báo các annotation validation:
  - `@NotBlank(message = "Mã sinh viên không được để trống")`
  - `@Size(min = 5, message = "Mã sinh viên tối thiểu 5 ký tự")`
  - `@NotBlank(message = "Họ tên không được để trống")`
  - `@NotBlank(message = "Email không được để trống")` & `@Email(message = "Email không đúng định dạng")`
  - `@NotBlank(message = "Lớp không được để trống")`

### Bài 2: Tạo Service giả lập dữ liệu (StudentService)
- Đường dẫn: `src/main/java/vn/edu/eaut/lab12/service/StudentService.java`
- Khởi tạo danh sách mẫu qua `@PostConstruct` (4 sinh viên mẫu SV0001 -> SV0004).
- Cung cấp các phương thức: `findAll()`, `findById()`, `save()`, `deleteById()`, `searchByKeyword()`, `existsByStudentCode()`.

### Bài 3: Tạo Controller danh sách sinh viên
- Đường dẫn: `src/main/java/vn/edu/eaut/lab12/controller/StudentController.java`
- Xử lý endpoint `GET /students` trả về view `students/list.html` cùng thuộc tính `students`.

### Bài 4: Tạo form thêm sinh viên
- `GET /students/create`: Chuẩn bị model attribute `student` mới và trả về `students/form.html`.
- `POST /students/save`: Nhận đối tượng binding `@ModelAttribute Student student` và lưu vào `StudentService`.

### Bài 5: Thêm validation cho form
- Sử dụng `@Valid` kết hợp `BindingResult result`.
- Kiểm tra `result.hasErrors()`: nếu có lỗi thì trả lại `students/form` để hiển thị lỗi chi tiết cho từng ô nhập liệu.

### Bài 6 (Bài tự làm): Xem chi tiết sinh viên theo ID
- Endpoint: `GET /students/detail/{id}` và `GET /students/{id}`.
- Trả về view `students/detail.html` với đầy đủ thông tin chi tiết sinh viên và các nút điều hướng.

### Bài 7 (Bài tự làm): Chỉnh sửa thông tin sinh viên
- Endpoint: `GET /students/edit/{id}` lấy thông tin sinh viên theo id đưa vào form `students/form.html`.
- Form chứa trường ẩn `<input type="hidden" th:field="*{id}">` để phân biệt thao tác cập nhật hay thêm mới.
- Phương thức `save()` trong `StudentService` tự động cập nhật nếu `id` đã tồn tại.

### Bài 8 (Bài tự làm): Xóa sinh viên khỏi danh sách
- Endpoint: `GET /students/delete/{id}` xóa sinh viên tương ứng và chuyển hướng về `/students` kèm thông báo thành công.

### Bài 9 (Bài tự làm): Tìm kiếm sinh viên theo từ khóa
- Xử lý tham số `keyword` tại `GET /students?keyword=...`.
- Tìm kiếm không phân biệt hoa thường theo họ tên, mã sinh viên hoặc lớp học.

### Bài 10 (Bài tự làm): Validation mã sinh viên không trùng lặp
- Kiểm tra thông qua `studentService.existsByStudentCode(studentCode, excludeId)`.
- Khi mã bị trùng, gán lỗi tùy biến:
  `result.rejectValue("studentCode", "duplicate", "Mã sinh viên đã tồn tại trong hệ thống!");`

---

## 5. Hướng dẫn chạy ứng dụng

### Cách 1: Chạy bằng Maven (Khuyên dùng)
Tại thư mục `lab12-spring-mvc-student`, mở Terminal / PowerShell và chạy:
```powershell
mvn spring-boot:run
```

### Cách 2: Chạy trực tiếp từ file JAR
```powershell
mvn clean package
java -jar target/lab12-spring-mvc-student.jar
```

### Cách 3: Chạy trong IDE (IntelliJ IDEA / VS Code / Eclipse)
Mở file `Lab12Application.java` và chọn **Run Java**.

---

## 6. Danh sách URL kiểm thử

| STT | Chức năng | Đường dẫn URL | Phương thức | Kết quả mong đợi |
|:---:|:---|:---|:---:|:---|
| 1 | Trang chủ | `http://localhost:8080/` | GET | Tự động chuyển hướng về `/students` |
| 2 | Danh sách sinh viên | `http://localhost:8080/students` | GET | Hiển thị bảng sinh viên và thanh công cụ |
| 3 | Form thêm mới | `http://localhost:8080/students/create` | GET | Form rỗng với tiêu đề "Thêm Mới Sinh Viên" |
| 4 | Lưu sinh viên | `http://localhost:8080/students/save` | POST | Lưu thành công -> Redirect về danh sách; Lỗi -> Hiển thị thông báo đỏ |
| 5 | Chi tiết sinh viên | `http://localhost:8080/students/detail/1` | GET | Card chi tiết sinh viên ID 1 |
| 6 | Sửa sinh viên | `http://localhost:8080/students/edit/1` | GET | Form chứa dữ liệu sinh viên 1 với tiêu đề "Cập Nhật..." |
| 7 | Xóa sinh viên | `http://localhost:8080/students/delete/1` | GET | Xóa sinh viên ID 1 và thông báo thành công |
| 8 | Tìm kiếm | `http://localhost:8080/students?keyword=An` | GET | Lọc danh sách sinh viên có họ tên/mã chứa "An" |

---

## 7. Chạy kiểm thử tự động (Unit & Integration Tests)
Chạy lệnh kiểm thử:
```powershell
mvn test
```
Toàn bộ 14 test cases trong bộ kiểm thử `StudentControllerTest` và `Lab12ApplicationTests` đều chạy tự động với kết quả **SUCCESS 100%**.
