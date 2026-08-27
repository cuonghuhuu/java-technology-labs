# BÁO CÁO KẾT QUẢ THỰC HÀNH LAB 11
## HỌC PHẦN: CÔNG NGHỆ JAVA (MÃ HỌC PHẦN: IT3242)
### CHƯƠNG 4: PHÁT TRIỂN ỨNG DỤNG VỚI SPRING FRAMEWORK
### Đề tài: Khởi tạo ứng dụng Spring Boot và giao diện Thymeleaf

---

## THÔNG TIN CHUNG
- **Trường:** Đại học Công nghệ Đông Á (EAUT)
- **Khoa:** Công nghệ Thông tin
- **Học phần:** Công nghệ Java (IT3242)
- **Tên bài Lab:** Lab 11 - Khởi tạo ứng dụng Spring Boot và giao diện Thymeleaf
- **Project thực hiện:** `lab11-springboot-thymeleaf`
- **Môi trường & Công nghệ:** Java 17/21/25, Spring Boot 3.2.5, Thymeleaf Template Engine, Spring MVC, Spring Boot DevTools, Maven 3.9+, Bootstrap 5.3, Custom CSS.

---

## 1. MỤC TIÊU BÀI LAB
1. Nắm vững phương pháp khởi tạo dự án Spring Boot bằng công cụ Spring Initializr và cấu hình Maven `pom.xml`.
2. Hiểu rõ cấu trúc tổ chức mã nguồn chuẩn của một dự án Spring Boot theo mô hình đa lớp MVC.
3. Làm chủ cơ chế hoạt động của **Spring MVC Controller** (`@Controller`, `@GetMapping`, `@RequestMapping`).
4. Sử dụng thành thạo đối tượng `org.springframework.ui.Model` để truyền dữ liệu từ Controller sang tầng View.
5. Làm chủ cú pháp **Thymeleaf Template Engine**:
   - `th:text`: Hiển thị nội dung văn bản động.
   - `th:each`: Duyệt mảng/danh sách đối tượng (Collection, List).
   - `th:href`: Xây dựng liên kết URL theo context path (`@{/...}`).
   - `th:replace` / `th:fragment`: Tái sử dụng các thành phần giao diện chung (Navbar, Footer).
6. Xây dựng hoàn chỉnh các trang: Trang chủ (`/`), Giới thiệu (`/about`), Danh sách sinh viên (`/students`), Danh sách khóa học (`/courses`), Liên hệ (`/contact`).
7. Thiết kế giao diện web responsive, hiện đại với CSS tùy chỉnh (`static/css/style.css`) và Bootstrap 5.
8. Viết bộ kiểm thử tự động với JUnit 5 & MockMvc, đảm bảo 100% test cases vượt qua thành công.

---

## 2. KIẾN TRÚC VÀ CẤU TRÚC DỰ ÁN

### 2.1. Sơ đồ luồng hoạt động MVC trong Spring Boot & Thymeleaf
```
                      HTTP Request (ví dụ: GET /students)
                                    │
                                    ▼
                     ┌─────────────────────────────┐
                     │     DispatcherServlet       │ (Front Controller)
                     └──────────────┬──────────────┘
                                    │ Phân giải HandlerMapping
                                    ▼
                     ┌─────────────────────────────┐
                     │     StudentController       │ (@Controller)
                     └──────────────┬──────────────┘
                                    │ 1. Xử lý logic & tạo List<Student>
                                    │ 2. model.addAttribute("students", list)
                                    │ 3. return "students" (tên View)
                                    ▼
                     ┌─────────────────────────────┐
                     │    ThymeleafViewResolver    │
                     └──────────────┬──────────────┘
                                    │ Nạp template /templates/students.html
                                    │ Render Thymeleaf expressions (${students})
                                    ▼
                     ┌─────────────────────────────┐
                     │  HTML Response (Full Render)│
                     └──────────────┬──────────────┘
                                    │
                                    ▼ Trả về Client Browser
```

### 2.2. Cây thư mục dự án
```
lab11-springboot-thymeleaf/
├── pom.xml                                   # Quản lý dependency Spring Boot & Plugins
├── README.md                                 # Hướng dẫn chạy và danh sách URL
├── BAO_CAO_LAB11.md                          # Báo cáo tổng kết nghiệm thu bài lab
├── src/
│   ├── main/
│   │   ├── java/vn/edu/eaut/lab11/
│   │   │   ├── Lab11Application.java         # Lớp Bootstrap khởi chạy Spring Boot
│   │   │   ├── controller/
│   │   │   │   ├── HomeController.java        # Xử lý: /, /about, /contact
│   │   │   │   ├── StudentController.java     # Xử lý: /students
│   │   │   │   └── CourseController.java      # Xử lý: /courses
│   │   │   └── model/
│   │   │       ├── Student.java               # Model Sinh viên (Mã SV, Tên, Email, Lớp)
│   │   │       ├── Course.java                # Model Khóa học (Mã môn, Tên môn, Tín chỉ,...)
│   │   │       └── ContactInfo.java           # Model Thông tin Khoa CNTT
│   │   └── resources/
│   │       ├── application.properties        # Cấu hình cổng 8080 và Thymeleaf
│   │       ├── static/
│   │       │   └── css/
│   │       │       └── style.css              # CSS định dạng giao diện tùy biến (Bài 10)
│   │       └── templates/
│   │           ├── fragments/
│   │           │   ├── navbar.html            # Fragment Header / Navigation Bar chung
│   │           │   └── footer.html            # Fragment Chân trang chung
│   │           ├── index.html                 # View Trang chủ (Bài 2)
│   │           ├── about.html                 # View Trang giới thiệu (Bài 5)
│   │           ├── students.html              # View Danh sách sinh viên (Bài 4)
│   │           ├── courses.html               # View Danh sách khóa học (Bài 9)
│   │           └── contact.html               # View Liên hệ khoa (Bài 6)
│   └── test/
│       └── java/vn/edu/eaut/lab11/
│           └── Lab11ApplicationTests.java     # Bộ test MockMvc kiểm thử 5 Endpoints
```

---

## 3. CHI TIẾT KẾT QUẢ THỰC HIỆN 10 BÀI TẬP

### BÀI 1: Tạo project Spring Boot
- **Yêu cầu:** Khởi tạo project Spring Boot có `Spring Web`, `Thymeleaf` và `DevTools`. Đảm bảo chạy trên cổng `8080`.
- **Hiện thực `pom.xml`:**
  - Kế thừa `spring-boot-starter-parent` phiên bản `3.2.5`.
  - Khai báo các dependencies: `spring-boot-starter-web`, `spring-boot-starter-thymeleaf`, `spring-boot-devtools` (scope runtime/optional), `spring-boot-starter-test`.
- **Cấu hình `application.properties`:**
  ```properties
  server.port=8080
  spring.application.name=lab11-springboot-thymeleaf
  spring.thymeleaf.cache=false
  spring.thymeleaf.prefix=classpath:/templates/
  spring.thymeleaf.suffix=.html
  spring.thymeleaf.encoding=UTF-8
  ```
- **Lớp khởi chạy `Lab11Application.java`:**
  ```java
  package vn.edu.eaut.lab11;
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;

  @SpringBootApplication
  public class Lab11Application {
      public static void main(String[] args) {
          SpringApplication.run(Lab11Application.class, args);
      }
  }
  ```

---

### BÀI 2: Tạo trang chủ (`/`)
- **Yêu cầu:** Khi truy cập `/`, Controller truyền dữ liệu `title` và `message` sang View `index.html`.
- **Hiện thực `HomeController.java`:**
  ```java
  @GetMapping("/")
  public String index(Model model) {
      model.addAttribute("title", "Hệ thống quản lý sinh viên");
      model.addAttribute("message", "Chào mừng đến với Spring Boot");
      model.addAttribute("activePage", "home");
      return "index";
  }
  ```
- **Hiện thực `templates/index.html`:**
  - Nhận và hiển thị `${title}` trên thẻ `<title>`, `${message}` trên thẻ `<h1>`.
  - Thẻ liên kết `@{/students}` chuyển đến trang danh sách sinh viên.

---

### BÀI 3: Tạo lớp Student (`Student.java`)
- **Yêu cầu:** Xây dựng Model `Student` gồm các thuộc tính: `studentCode`, `fullName`, `email`, `className` cùng constructor và getter/setter.
- **Hiện thực `vn/edu/eaut/lab11/model/Student.java`:**
  ```java
  package vn.edu.eaut.lab11.model;
  import java.io.Serializable;

  public class Student implements Serializable {
      private String studentCode;
      private String fullName;
      private String email;
      private String className;

      public Student() {}
      public Student(String studentCode, String fullName, String email, String className) {
          this.studentCode = studentCode;
          this.fullName = fullName;
          this.email = email;
          this.className = className;
      }
      // Getter & Setter đầy đủ cho tất cả thuộc tính
  }
  ```

---

### BÀI 4: Hiển thị danh sách sinh viên (`/students`)
- **Yêu cầu:** Tạo URL `/students` trả về danh sách sinh viên mẫu hiển thị qua bảng Thymeleaf với `th:each`.
- **Hiện thực `StudentController.java`:**
  ```java
  @Controller
  public class StudentController {
      @GetMapping("/students")
      public String listStudents(Model model) {
          List<Student> students = List.of(
              new Student("SV001", "Nguyễn Văn An", "an@eaut.edu.vn", "DCCNTT13.10.1"),
              new Student("SV002", "Trần Thị Bình", "binh@eaut.edu.vn", "DCCNTT13.10.2"),
              new Student("SV003", "Lê Văn Cường", "cuong@eaut.edu.vn", "DCCNTT13.10.3"),
              new Student("SV004", "Phạm Thu Dung", "dung@eaut.edu.vn", "DCCNTT13.10.1"),
              new Student("SV005", "Hoàng Minh Đức", "duc@eaut.edu.vn", "DCCNTT13.10.2")
          );
          model.addAttribute("students", students);
          model.addAttribute("totalStudents", students.size());
          model.addAttribute("activePage", "students");
          return "students";
      }
  }
  ```
- **Hiện thực `templates/students.html`:**
  - Sử dụng cú pháp lặp:
    ```html
    <tr th:each="s, stat : ${students}">
        <td th:text="${stat.count}">1</td>
        <td th:text="${s.studentCode}">SV001</td>
        <td th:text="${s.fullName}">Nguyễn Văn An</td>
        <td th:text="${s.email}">an@eaut.edu.vn</td>
        <td th:text="${s.className}">DCCNTT13.10.1</td>
    </tr>
    ```

---

### BÀI 5: Tạo trang giới thiệu (`/about`)
- **Yêu cầu:** URL `/about` giới thiệu học phần và nội dung chương học.
- **Bổ sung trong `HomeController.java`:**
  ```java
  @GetMapping("/about")
  public String about(Model model) {
      model.addAttribute("course", "Công nghệ Java");
      model.addAttribute("chapter", "Chương 4 - Spring Framework");
      model.addAttribute("courseCode", "IT3242");
      model.addAttribute("activePage", "about");
      return "about";
  }
  ```
- **Hiện thực `templates/about.html`:** Hiển thị `${course}` và `${chapter}` trên giao diện thẻ card trực quan.

---

### BÀI 6: Tạo trang liên hệ (`/contact`)
- **Yêu cầu:** URL `/contact` hiển thị thông tin liên hệ của Khoa/Bộ môn Công nghệ Thông tin.
- **Model `ContactInfo.java`:** Đóng gói thông tin: Tên khoa, Trường, Địa chỉ, Email, Điện thoại bàn, Hotline, Website, Giờ làm việc, Trưởng khoa.
- **Hiện thực `templates/contact.html`:** Hiển thị thông tin liên lạc và form hỗ trợ sinh viên trực quan.

---

### BÀI 7: Thêm menu điều hướng chung (Navigation Bar)
- **Yêu cầu:** Điều hướng đồng bộ giữa Trang chủ, Giới thiệu, Sinh viên, Khóa học, Liên hệ.
- **Giải pháp:** Sử dụng Thymeleaf Fragment (`fragments/navbar.html`):
  - Áp dụng `th:classappend="${activePage == 'home' ? 'active' : ''}"` để tự động highlight menu của trang hiện tại.
  - Nhúng vào các trang bằng cú pháp: `<div th:replace="~{fragments/navbar :: navbar}"></div>`.

---

### BÀI 8: Tạo danh sách 5 khóa học mẫu
- **Yêu cầu:** Tạo Model `Course.java` gồm mã môn, tên môn, số tín chỉ, giảng viên, mô tả.
- **Dữ liệu 5 khóa học chuẩn:**
  1. `IT3242` - Công nghệ Java (3 tín chỉ) - TS. Đặng Thanh Hưng
  2. `IT3010` - Cấu trúc dữ liệu và giải thuật (3 tín chỉ) - ThS. Nguyễn Văn A
  3. `IT3100` - Cơ sở dữ liệu nâng cao (3 tín chỉ) - ThS. Trần Thị B
  4. `IT3080` - Mạng máy tính (3 tín chỉ) - TS. Lê Văn C
  5. `IT3310` - Lập trình Web nâng cao (4 tín chỉ) - ThS. Hoàng Minh D

---

### BÀI 9: Tạo trang hiển thị danh sách khóa học (`/courses`)
- **Yêu cầu:** Xây dựng `CourseController.java` và View `templates/courses.html` hiển thị danh sách khóa học qua bảng Thymeleaf.
- **Cú pháp lặp Thymeleaf:**
  ```html
  <tr th:each="c, stat : ${courses}">
      <td th:text="${stat.count}">1</td>
      <td th:text="${c.courseCode}">IT3242</td>
      <td th:text="${c.courseName}">Công nghệ Java</td>
      <td th:text="${c.credits}">3</td>
      <td th:text="${c.lecturer}">TS. Đặng Thanh Hưng</td>
      <td th:text="${c.description}">Mô tả môn học</td>
  </tr>
  ```

---

### BÀI 10: Định dạng giao diện bằng CSS riêng trong `static/css`
- **Yêu cầu:** Tùy biến toàn bộ giao diện bằng file CSS riêng đặt tại `src/main/resources/static/css/style.css`.
- **Điểm nổi bật của CSS:**
  - Bảng màu gradient hiện đại (`--primary-gradient`, `--card-gradient`).
  - Card bo tròn viền mượt (`border-radius: 12px`, `box-shadow`).
  - Bảng dữ liệu có hiệu ứng hover mượt mà và badges màu phân loại mã SV, tín chỉ.
  - Navbar dính cố định (`sticky-top`) với active indicator đẹp mắt.
  - Footer thông tin khóa học chuyên nghiệp.

---

## 4. KẾT QUẢ KIỂM THỬ VÀ ĐÁNH GIÁ

### 4.1. Bảng ma trận kiểm thử Endpoints (Test Matrix)

| Endpoint | HTTP Method | View Template | Model Attributes | Kết quả kiểm thử |
|---|---|---|---|---|
| `/` | `GET` | `index` | `title`, `message`, `features` | **PASSED (HTTP 200)** |
| `/students` | `GET` | `students` | `students`, `totalStudents` | **PASSED (HTTP 200)** |
| `/courses` | `GET` | `courses` | `courses`, `totalCourses`, `totalCredits` | **PASSED (HTTP 200)** |
| `/about` | `GET` | `about` | `course`, `chapter`, `courseCode`, `topics` | **PASSED (HTTP 200)** |
| `/contact` | `GET` | `contact` | `contact` | **PASSED (HTTP 200)** |

### 4.2. Kết quả chạy JUnit Test tự động (`MockMvc`)
```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running vn.edu.eaut.lab11.Lab11ApplicationTests
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.445 s
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

---

## 5. HƯỚNG DẪN BIÊN DỊCH VÀ CHẠY ỨNG DỤNG

### Bước 1: Mở Terminal tại thư mục project
```powershell
cd D:\IT2023\Java_Technology\projectLab\lab11-springboot-thymeleaf
```

### Bước 2: Chạy ứng dụng bằng lệnh Maven
```powershell
mvn spring-boot:run
```

### Bước 3: Mở trình duyệt và kiểm thử
- Trang chủ: [http://localhost:8080/](http://localhost:8080/)
- Danh sách sinh viên: [http://localhost:8080/students](http://localhost:8080/students)
- Danh sách khóa học: [http://localhost:8080/courses](http://localhost:8080/courses)
- Trang giới thiệu: [http://localhost:8080/about](http://localhost:8080/about)
- Trang liên hệ: [http://localhost:8080/contact](http://localhost:8080/contact)

---

## 6. KẾT LUẬN
- Dự án **Lab 11** đã hoàn thành xuất sắc 100% các tiêu chí yêu cầu trong đề cương thực hành Chương 4 (cả 5 bài có code gợi ý và 5 bài tự làm không có code gợi ý).
- Ứng dụng hoạt động ổn định trên môi trường Java hiện đại kết hợp Spring Boot 3.x và Thymeleaf Template Engine.
- Cấu trúc thư mục chuẩn mực, mã nguồn phân tách rành mạch theo kiến trúc MVC, sẵn sàng mở rộng cho các bài lab kết nối Spring Data JPA và Spring Security tiếp theo.
