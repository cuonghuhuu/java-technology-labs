# BÀI THỰC HÀNH LAB 10 - CÔNG NGHỆ JAVA (IT3242)
## CHƯƠNG 3: PHÁT TRIỂN ỨNG DỤNG ĐA LỚP TRONG JAKARTA EE
### Thêm Login, Role, Bảo Vệ URL và Hoàn Thiện Ứng Dụng

---

## 1. Giới thiệu tổng quan
Dự án **Lab 10** là sản phẩm hoàn thiện chuỗi thực hành từ Lab 6 đến Lab 10, xây dựng hệ thống ứng dụng web đa lớp hoàn chỉnh theo kiến trúc Jakarta EE (Jakarta Servlet 6.0, JSP/JSTL, Hibernate JPA 6.x, Filters, Listeners) kết hợp cơ chế Xác thực (Authentication), Phân quyền theo vai trò (Role-Based Access Control - RBAC) và bảo vệ an toàn toàn bộ các đường dẫn URL.

---

## 2. Kiến trúc & Công nghệ sử dụng
- **Ngôn ngữ & Nền tảng:** Java 17+ (tương thích Java 21, Java 25)
- **Web Container / Application Server:** Apache Tomcat 10.x / 11.x (Jakarta EE 10 namespace `jakarta.*`)
- **Quản lý dự án & Thư viện:** Apache Maven 3.9+
- **Persistence (ORM):** Hibernate Core 6.4.4.Final + Jakarta Persistence API 3.1
- **Cơ sở dữ liệu:**
  - **Mặc định:** H2 Database Embedded (Chạy ngay lập tức không cần cài đặt hoặc khởi động MySQL).
  - **Tùy chọn:** MySQL 8.x (Có sẵn script SQL và cấu hình trong `persistence.xml`).
- **Giao diện & Trải nghiệm:** Bootstrap 5.3, Bootstrap Icons, Responsive Layout, Role-based Dynamic Navbar, Custom CSS.
- **Bảo mật:** Filter Chain (`CharacterEncodingFilter` -> `AuthenticationFilter` -> `AuthorizationFilter` -> `NoCacheFilter`), mã hóa mật khẩu BCrypt.

---

## 3. Cấu trúc thư mục dự án
```
lab10-login-role-security/
├── pom.xml                                   # Cấu hình Maven dependencies & Cargo plugin
├── README.md                                 # Tài liệu hướng dẫn sử dụng & cài đặt
├── BAO_CAO_LAB10.md                          # Báo cáo tổng hợp chi tiết bài Lab 10
├── database/
│   ├── schema.sql                            # Script tạo bảng DDL (MySQL/H2)
│   └── data.sql                              # Script nạp dữ liệu mẫu DML
└── src/
    └── main/
        ├── java/
        │   └── vn/edu/eaut/lab10/
        │       ├── controller/               # MVC Controllers (Servlets)
        │       │   ├── AuthController.java           # Xử lý /auth: login, logout
        │       │   ├── DashboardController.java      # Thống kê tổng hợp /dashboard
        │       │   ├── UserController.java           # Quản trị người dùng /admin/users
        │       │   ├── ProfileController.java        # Hồ sơ cá nhân /user/profile
        │       │   ├── ChangePasswordController.java # Đổi mật khẩu /user/change-password
        │       │   ├── SinhVienController.java       # CRUD Sinh viên /staff/sinh-vien
        │       │   ├── SachController.java           # CRUD Sách /staff/sach
        │       │   ├── SanPhamController.java        # CRUD Sản phẩm /staff/san-pham
        │       │   └── LogController.java            # Xem nhật ký /admin/logs
        │       ├── filter/                   # Lớp bảo vệ URL & mã hóa
        │       │   ├── AuthenticationFilter.java     # Kiểm tra đăng nhập
        │       │   ├── AuthorizationFilter.java      # Kiểm tra vai trò (RBAC)
        │       │   ├── CharacterEncodingFilter.java  # Mã hóa UTF-8 tiếng Việt
        │       │   └── NoCacheFilter.java            # Chặn Back-button sau logout
        │       ├── listener/                 # Context & Session Listeners
        │       │   ├── AppContextListener.java       # Khởi tạo DB & nạp dữ liệu mẫu
        │       │   └── AppSessionListener.java       # Theo dõi vòng đời session
        │       ├── model/                    # JPA Entities & Enums
        │       │   ├── Role.java                     # Enum ADMIN, STAFF, USER
        │       │   ├── User.java                     # Entity tài khoản người dùng
        │       │   ├── SinhVien.java                 # Entity Sinh viên (Module 1)
        │       │   ├── Sach.java                     # Entity Sách thư viện (Module 2)
        │       │   ├── SanPham.java                  # Entity Sản phẩm kho (Module 3)
        │       │   └── ActivityLog.java              # Entity Nhật ký hoạt động
        │       ├── repository/               # Data Access Layer (JPA Query)
        │       │   ├── UserRepository.java
        │       │   ├── SinhVienRepository.java
        │       │   ├── SachRepository.java
        │       │   ├── SanPhamRepository.java
        │       │   └── ActivityLogRepository.java
        │       ├── service/                  # Business Logic Layer & Validation
        │       │   ├── AuthService.java
        │       │   ├── UserService.java
        │       │   ├── SinhVienService.java
        │       │   ├── SachService.java
        │       │   ├── SanPhamService.java
        │       │   └── ActivityLogService.java
        │       └── util/                     # Utilities
        │           ├── JPAUtil.java                  # Quản lý EntityManagerFactory & Transaction
        │           └── PasswordUtil.java             # Mã hóa & kiểm tra BCrypt
        ├── resources/
        │   └── META-INF/
        │       └── persistence.xml           # Cấu hình Hibernate JPA Provider
        └── webapp/
            ├── WEB-INF/
            │   ├── jspf/
            │   │   ├── header.jspf           # Header HTML & CSS links
            │   │   ├── navbar.jspf           # Menu điều hướng động theo vai trò
            │   │   └── footer.jspf           # Footer bản quyền & Scripts
            │   └── web.xml                   # Cấu hình Error Pages 403, 404, 500
            ├── assets/
            │   ├── css/style.css             # CSS tùy chỉnh giao diện
            │   └── js/app.js                 # JS tương tác client
            ├── error/                        # Giao diện thông báo lỗi thân thiện
            │   ├── 403.jsp                   # Lỗi 403 Forbidden (Truy cập bị từ chối)
            │   ├── 404.jsp                   # Lỗi 404 Not Found (Không tìm thấy)
            │   └── 500.jsp                   # Lỗi 500 Internal Server Error
            ├── admin/                        # Giao diện dành riêng cho ADMIN
            │   ├── user-list.jsp             # Danh sách & Quản lý tài khoản
            │   ├── user-form.jsp             # Thêm / sửa người dùng
            │   └── audit-logs.jsp            # Nhật ký hoạt động hệ thống
            ├── staff/                        # Giao diện nghiệp vụ (STAFF + ADMIN)
            │   ├── sinhvien/                 # Module Sinh viên (list.jsp, form.jsp)
            │   ├── sach/                     # Module Sách (list.jsp, form.jsp)
            │   └── sanpham/                  # Module Sản phẩm (list.jsp, form.jsp)
            ├── user/                         # Giao diện cá nhân (Tất cả user đã login)
            │   ├── profile.jsp               # Xem và cập nhật thông tin cá nhân
            │   └── change-password.jsp       # Đổi mật khẩu tài khoản
            ├── dashboard.jsp                 # Bảng điều khiển thống kê tổng quan
            ├── index.jsp                     # Trang chủ giới thiệu
            └── login.jsp                     # Trang đăng nhập với nút điền nhanh tài khoản
```

---

## 4. Ma trận phân quyền & Bảo vệ URL (RBAC)

| Đường dẫn (URL Pattern) | Vai trò được phép | Hành vi khi vi phạm |
|---|---|---|
| `/index.jsp`, `/login.jsp`, `/auth` | Public (Tất cả mọi người) | Cho phép truy cập bình thường |
| `/assets/*` | Public (CSS, JS, Fonts) | Cho phép truy cập bình thường |
| `/dashboard` | ADMIN, STAFF, USER | Chưa login -> Chuyển hướng về `/login.jsp` |
| `/user/*` (`/user/profile`, `/user/change-password`) | ADMIN, STAFF, USER | Chưa login -> Chuyển hướng về `/login.jsp` |
| `/staff/*` (`/staff/sinh-vien`, `/staff/sach`, `/staff/san-pham`) | ADMIN, STAFF | Chưa login -> `/login.jsp`<br>User thường -> Chuyển hướng về `/error/403.jsp` |
| `/admin/*` (`/admin/users`, `/admin/logs`) | ADMIN | Chưa login -> `/login.jsp`<br>Staff/User -> Chuyển hướng về `/error/403.jsp` |

---

## 5. Tài khoản thử nghiệm mặc định
Khi ứng dụng khởi động, `AppContextListener` sẽ tự động nạp sẵn dữ liệu tài khoản và nghiệp vụ mẫu:

| Email đăng nhập | Mật khẩu mặc định | Họ và tên | Vai trò | Quyền hạn chính |
|---|---|---|---|---|
| `admin@eaut.edu.vn` | `123456` | Quản Trị Viên (Admin) | **ADMIN** | Toàn quyền: Quản lý người dùng, xem logs, quản lý sinh viên, sách, sản phẩm, dashboard |
| `staff@eaut.edu.vn` | `123456` | Nhân Viên Nghiệp Vụ (Staff) | **STAFF** | Quản lý nghiệp vụ: Sinh viên, Sách, Sản phẩm, Dashboard, Đổi mật khẩu cá nhân |
| `user@eaut.edu.vn` | `123456` | Người Dùng Thường (User) | **USER** | Xem Dashboard, cập nhật hồ sơ cá nhân, đổi mật khẩu tài khoản |

> **Mẹo tiện ích:** Tại trang `/login.jsp`, có sẵn các nút bấm 1-click **Admin**, **Staff**, **User** để tự động điền thông tin đăng nhập phục vụ kiểm thử nhanh chóng.

---

## 6. Hướng dẫn chạy chương trình

### Cách 1: Chạy bằng Maven Cargo Plugin (Nhanh nhất, không cần cài Tomcat rời)
Mở terminal tại thư mục `lab10-login-role-security` và thực hiện lệnh:
```bash
mvn cargo:run
```
Sau đó truy cập trình duyệt tại địa chỉ:
👉 **`http://localhost:8080/lab10-login-role-security/`**

---

### Cách 2: Deploy file `.war` lên Apache Tomcat 10.x rời
1. Đóng gói ứng dụng:
   ```bash
   mvn clean package
   ```
2. Copy file `target/lab10-login-role-security.war` vào thư mục `webapps/` của Tomcat 10.x.
3. Khởi động Tomcat (`bin/startup.bat` hoặc `bin/startup.sh`).
4. Mở trình duyệt truy cập: `http://localhost:8080/lab10-login-role-security/`.

---

### Cách 3: Chạy trên IntelliJ IDEA / Eclipse / NetBeans
1. Import thư mục `lab10-login-role-security` dưới dạng **Maven Project**.
2. Thêm cấu hình **Tomcat Server (Local)** (phiên bản Tomcat 10.1.x+).
3. Tại tab *Deployment*, thêm artifact `lab10-login-role-security:war` với Application Context là `/lab10-login-role-security`.
4. Nhấn **Run / Debug**.

---

## 7. Cấu hình Cơ sở dữ liệu (Tùy chọn MySQL)
Ứng dụng đã được cấu hình mặc định sử dụng **H2 Database In-Memory** để đảm bảo chạy mượt mà ngay trên mọi máy tính chấm bài mà không cần cài đặt SQL Server/MySQL.

Nếu muốn chuyển sang kết nối **MySQL 8.x**:
1. Tạo database và chạy script trong thư mục `database/schema.sql` và `database/data.sql`.
2. Mở file `src/main/resources/META-INF/persistence.xml`.
3. Bỏ chú thích (uncomment) phần cấu hình MySQL:
   ```xml
   <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
   <property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/lab10_db?useSSL=false&amp;serverTimezone=UTC&amp;allowPublicKeyRetrieval=true&amp;characterEncoding=UTF-8"/>
   <property name="jakarta.persistence.jdbc.user" value="root"/>
   <property name="jakarta.persistence.jdbc.password" value="123456"/>
   ```
4. Comment lại phần cấu hình H2 và khởi động lại server.
