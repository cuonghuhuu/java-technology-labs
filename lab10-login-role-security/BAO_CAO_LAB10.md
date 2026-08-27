# BÁO CÁO TỔNG HỢP KẾT QUẢ THỰC HÀNH LAB 10
## HỌC PHẦN: CÔNG NGHỆ JAVA (IT3242)
### BÀI LAB: THÊM LOGIN, ROLE, BẢO VỆ URL VÀ HOÀN THIỆN ỨNG DỤNG

---

## THÔNG TIN CHUNG
- **Học phần:** Công nghệ Java (IT3242)
- **Tên bài thực hành:** Lab 10 - Thêm login, role, bảo vệ URL và hoàn thiện ứng dụng đa lớp
- **Giảng viên hướng dẫn:** Bộ môn Công nghệ Phần mềm - Khoa CNTT
- **Thời lượng:** 01 buổi thực hành
- **Sản phẩm bàn giao:** Mã nguồn Maven Web Project + Database Scripts + Báo cáo kết quả + Checklist kiểm thử

---

## 1. MỤC TIÊU CẦN ĐẠT
1. **Cơ chế Đăng nhập / Đăng xuất:** Sử dụng dữ liệu tài khoản lưu trữ trong CSDL (thông qua Hibernate JPA), hỗ trợ mã hóa mật khẩu an toàn với BCrypt và cơ chế ghi nhớ URL redirect sau khi đăng nhập.
2. **Quản lý Phiên làm việc (Session Management):** Lưu trữ thông tin đối tượng người dùng `currentUser` vào `HttpSession`, tự động hủy session khi đăng xuất hoặc timeout.
3. **Phân quyền theo vai trò (Role-Based Access Control - RBAC):** Thiết lập 3 vai trò phân cấp rõ ràng: `ADMIN`, `STAFF`, `USER` (và trạng thái `GUEST`).
4. **Bảo vệ URL bằng Bộ lọc (Filter Security):** Xây dựng `AuthenticationFilter` và `AuthorizationFilter` để ngăn chặn tuyệt đối các hành vi truy cập trái phép hoặc leo thang đặc quyền.
5. **Giao diện Menu Động:** Thanh điều hướng (Navbar) tự động ẩn/hiển thị các liên kết quản trị, nghiệp vụ và cá nhân dựa trên vai trò của người dùng trong session.
6. **Xử lý Trang lỗi Thân thiện:** Thiết lập các trang lỗi tùy chỉnh `403.jsp` (Truy cập bị từ chối), `404.jsp` (Không tìm thấy trang), `500.jsp` (Lỗi máy chủ) và liên kết cấu hình trong `web.xml`.
7. **Hoàn thiện Ứng dụng Đa Lớp (Multi-Tier):** Tích hợp tối thiểu 3 module nghiệp vụ JPA (`SinhVien`, `Sach`, `SanPham`) + 1 module tài khoản (`User`), có đầy đủ chức năng CRUD, tìm kiếm, kiểm tra hợp lệ dữ liệu (Validation), hiển thị thông báo lỗi/thành công và quản lý Transaction an toàn.
8. **Nhật ký Hoạt động (Audit Logging):** Ghi lại chi tiết mọi sự kiện đăng nhập, đăng xuất, đổi mật khẩu và các thao tác thêm/sửa/xóa dữ liệu quan trọng vào bảng `activity_logs`.

---

## 2. KIẾN TRÚC HỆ THỐNG VÀ CÔNG NGHỆ

### 2.1. Kiến trúc Đa Tầng (Multi-Tier Architecture - Model 2 MVC)
Hệ thống được thiết kế phân tầng độc lập nhằm đảm bảo tính module hóa, dễ bảo trì và mở rộng:

```
+-----------------------------------------------------------------------------------+
|                            TẦNG TRÌNH DIỄN (VIEW LAYER)                           |
|       JSP, JSTL, Bootstrap 5.3, Bootstrap Icons, JavaScript, Responsive UI        |
+-----------------------------------------------------------------------------------+
                                         ▲
                                         │ (Request / Response)
                                         ▼
+-----------------------------------------------------------------------------------+
|                        TẦNG BẢO MẬT & ĐIỀU HƯỚNG (FILTER LAYER)                   |
| CharacterEncodingFilter -> AuthenticationFilter -> AuthorizationFilter -> NoCache |
+-----------------------------------------------------------------------------------+
                                         ▲
                                         │
                                         ▼
+-----------------------------------------------------------------------------------+
|                          TẦNG ĐIỀU KHIỂN (CONTROLLER / SERVLET)                   |
| AuthController, DashboardController, UserController, SinhVienController, ...     |
+-----------------------------------------------------------------------------------+
                                         ▲
                                         │
                                         ▼
+-----------------------------------------------------------------------------------+
|                       TẦNG NGHIỆP VỤ & VALIDATE (SERVICE LAYER)                   |
| AuthService, UserService, SinhVienService, SachService, SanPhamService, ...       |
+-----------------------------------------------------------------------------------+
                                         ▲
                                         │
                                         ▼
+-----------------------------------------------------------------------------------+
|                    TẦNG TRUY XUẤT DỮ LIỆU (REPOSITORY / DAO LAYER)                |
| UserRepository, SinhVienRepository, SachRepository, SanPhamRepository, JPAUtil   |
+-----------------------------------------------------------------------------------+
                                         ▲
                                         │ (Hibernate ORM / JPQL / Transactions)
                                         ▼
+-----------------------------------------------------------------------------------+
|                             TẦNG CƠ SỞ DỮ LIỆU (DATABASE)                         |
|                   H2 Embedded In-Memory / File  hoặc  MySQL 8.x                   |
+-----------------------------------------------------------------------------------+
```

---

## 3. THIẾT KẾ VAI TRÒ & QUY TẮC BẢO VỆ URL (RBAC MATRIX)

### 3.1. Bảng phân quyền vai trò
- **ADMIN:** Toàn quyền quản trị: Quản lý người dùng (CRUD, phân vai trò, khóa/mở tài khoản, đặt lại mật khẩu), xem nhật ký hoạt động hệ thống (Audit logs), quản lý Sinh viên, Sách, Sản phẩm và xem Dashboard.
- **STAFF:** Thực hiện các nghiệp vụ quản lý dữ liệu: Thêm, sửa, xóa, tìm kiếm Sinh viên, Sách, Sản phẩm; xem Dashboard; cập nhật hồ sơ cá nhân và đổi mật khẩu. Không được truy cập module quản trị tài khoản Admin.
- **USER:** Người dùng đã đăng nhập: Xem trang tổng quan Dashboard, xem và cập nhật thông tin hồ sơ cá nhân, đổi mật khẩu. Không có quyền truy cập vào các module nghiệp vụ và quản trị.
- **GUEST:** Khách vãng lai chưa đăng nhập: Chỉ được xem trang chủ (`index.jsp`), trang đăng nhập (`login.jsp`) và các tài nguyên tĩnh (`/assets/*`). Khi truy cập bất kỳ trang bảo mật nào sẽ bị chuyển hướng về trang đăng nhập.

### 3.2. Quy tắc URL Security Matrix

| URL Pattern | Yêu cầu xác thực | Vai trò cho phép | Hành vi xử lý khi vi phạm |
|---|---|---|---|
| `/index.jsp`, `/login.jsp`, `/auth` | Không | Public | Cho phép truy cập bình thường |
| `/assets/*` (CSS, JS, Fonts) | Không | Public | Cho phép truy cập bình thường |
| `/dashboard` | Có | `ADMIN`, `STAFF`, `USER` | Chưa đăng nhập -> Chuyển về `/login.jsp?redirect=...` |
| `/user/*` (`/user/profile`, `/user/change-password`) | Có | `ADMIN`, `STAFF`, `USER` | Chưa đăng nhập -> Chuyển về `/login.jsp` |
| `/staff/*` (`/staff/sinh-vien`, `/staff/sach`, `/staff/san-pham`) | Có | `ADMIN`, `STAFF` | Chưa login -> `/login.jsp`<br>User thường -> Chuyển về `/error/403.jsp` |
| `/admin/*` (`/admin/users`, `/admin/logs`) | Có | `ADMIN` | Chưa login -> `/login.jsp`<br>Staff/User -> Chuyển về `/error/403.jsp` |

---

## 4. CHI TIẾT CÁC BÀI THỰC HÀNH ĐÃ HOÀN THÀNH

### Bài 1: Tạo Entity User và Role
- Xây dựng enum `Role` với các giá trị `ADMIN`, `STAFF`, `USER`.
- Xây dựng Entity `User` với các trường: `id` (PK, Auto Increment), `email` (Unique), `password` (Hashed), `fullName`, `role` (`@Enumerated(EnumType.STRING)`), `active` (boolean), `createdAt` (LocalDateTime).

### Bài 2: Tạo UserRepository và AuthService
- `UserRepository`: Truy vấn CSDL bằng Hibernate JPA và JPQL (`findByEmail`, `findById`, `findAll`, `search`, `existsByEmail`, `save`, `update`, `delete`).
- `AuthService`: Kiểm tra đăng nhập với xác thực mật khẩu BCrypt, kiểm tra trạng thái kích hoạt tài khoản (`active`), hỗ trợ cập nhật hồ sơ và đổi mật khẩu an toàn.

### Bài 3: Xây dựng AuthController (Đăng nhập / Đăng xuất)
- Map Servlet tại `@WebServlet("/auth")`.
- Phương thức `doPost`: Nhận email/mật khẩu, gọi `AuthService.login()`, nếu sai báo lỗi tại `login.jsp`, nếu đúng lưu `currentUser` vào `HttpSession` và chuyển hướng tới trang đích hoặc `/dashboard`.
- Phương thức `doGet`: Nhận tham số `action=logout`, hủy session bằng `session.invalidate()` và chuyển hướng về `/login.jsp?logout=true`.

### Bài 4 & Bài 5: Xây dựng Bộ lọc AuthenticationFilter & AuthorizationFilter
- `AuthenticationFilter`: Chặn tất cả các request tới `/admin/*`, `/staff/*`, `/user/*`, `/dashboard`. Nếu session không tồn tại hoặc `currentUser == null`, lưu URL hiện tại vào tham số `redirect` và chuyển hướng về `login.jsp`.
- `AuthorizationFilter`: Kiểm tra vai trò của người dùng đã đăng nhập:
  - Nếu truy cập `/admin/*` mà vai trò không phải `ADMIN` -> Chuyển hướng tới `/error/403.jsp`.
  - Nếu truy cập `/staff/*` mà vai trò không phải `ADMIN` hoặc `STAFF` -> Chuyển hướng tới `/error/403.jsp`.
- `NoCacheFilter`: Thiết lập HTTP Headers `Cache-Control: no-cache, no-store, must-revalidate` nhằm ngăn chặn tính năng lưu cache của trình duyệt, tránh việc nhấn nút "Back" sau khi đăng xuất vẫn thấy dữ liệu riêng tư.

### Bài 6: Tự động khởi tạo dữ liệu tài khoản mẫu
- Xây dựng `AppContextListener` kích hoạt ngay khi Servlet Container khởi động.
- Tự động kiểm tra bảng `users`, nếu chưa có dữ liệu sẽ tự động seed 3 tài khoản mẫu ban đầu:
  1. `admin@eaut.edu.vn` (Mật khẩu: `123456`, Vai trò: `ADMIN`)
  2. `staff@eaut.edu.vn` (Mật khẩu: `123456`, Vai trò: `STAFF`)
  3. `user@eaut.edu.vn` (Mật khẩu: `123456`, Vai trò: `USER`)
- Đồng thời nạp sẵn dữ liệu cho 3 module nghiệp vụ (`SinhVien`, `Sach`, `SanPham`).

### Bài 7: Quản lý người dùng dành cho ADMIN (`/admin/users`)
- Chức năng CRUD tài khoản hoàn chỉnh:
  - Danh sách người dùng với bộ lọc theo từ khóa (email/tên) và theo vai trò (`ADMIN`, `STAFF`, `USER`).
  - Thêm tài khoản mới có validate dữ liệu (email không trùng, mật khẩu >= 6 ký tự).
  - Chỉnh sửa thông tin họ tên, email, thay đổi vai trò hệ thống.
  - Bật/tắt trạng thái hoạt động (Khóa / Mở khóa tài khoản) có cơ chế bảo vệ: Không cho phép Admin tự khóa tài khoản của chính mình.
  - Đặt lại mật khẩu (Reset password) qua hộp thoại Modal tiện lợi.
  - Xóa tài khoản có xác nhận và ngăn chặn Admin tự xóa chính mình.

### Bài 8: Trang hồ sơ cá nhân (`/user/profile`)
- Cho phép người dùng đã đăng nhập xem thông tin ID, vai trò, ngày tạo.
- Cho phép chỉnh sửa họ tên và email cá nhân (kiểm tra không trùng email với tài khoản khác trong DB).

### Bài 9: Trang đổi mật khẩu (`/user/change-password`)
- Giao diện nhập mật khẩu cũ, mật khẩu mới và xác nhận mật khẩu mới.
- Kiểm tra tính chính xác của mật khẩu cũ, độ dài mật khẩu mới (tối thiểu 6 ký tự) và khớp xác nhận trước khi hash BCrypt và lưu vào CSDL.

### Bài 10: Hiển thị thanh menu điều hướng động theo vai trò (Role-based Navigation)
- File fragment `navbar.jspf` sử dụng thẻ JSTL `<c:if>` và `<c:choose>` để render giao diện tùy theo `sessionScope.currentUser.role`:
  - `GUEST`: Hiển thị nút Đăng nhập.
  - `USER`: Hiển thị Dashboard, Menu cá nhân (Hồ sơ, Đổi mật khẩu, Đăng xuất).
  - `STAFF`: Hiển thị thêm Dropdown "Nghiệp vụ" (Quản lý Sinh viên, Sách, Sản phẩm).
  - `ADMIN`: Hiển thị toàn bộ menu Nghiệp vụ + Dropdown "Quản trị hệ thống" (Quản lý người dùng, Nhật ký hoạt động Logs).

### Bài 11: Xây dựng các trang thông báo lỗi 403, 404, 500
- `/error/403.jsp`: Hiển thị giao diện thông báo lỗi quyền truy cập, hiển thị rõ vai trò hiện tại của người dùng và các vai trò hợp lệ, kèm nút quay về Dashboard.
- `/error/404.jsp`: Giao diện thân thiện khi đường dẫn không tồn tại.
- `/error/500.jsp`: Giao diện thông báo lỗi hệ thống máy chủ.
- Cấu hình toàn bộ mã lỗi và exception handler trong file `web.xml`.

### Bài 12: Ghi nhận nhật ký hoạt động (Audit Logging)
- Entity `ActivityLog` và Repository/Service ghi lại các sự kiện: `LOGIN_SUCCESS`, `LOGIN_FAILED`, `LOGOUT`, `CREATE_USER`, `UPDATE_USER`, `TOGGLE_USER_STATUS`, `RESET_PASSWORD`, `UPDATE_PROFILE`, `CHANGE_PASSWORD`, `CREATE_STUDENT`, `UPDATE_STUDENT`, `DELETE_STUDENT`, `CREATE_BOOK`, `CREATE_PRODUCT`, `SERVER_START`.
- Trang `/admin/logs` cho phép Admin tra cứu và tìm kiếm nhật ký hoạt động theo thời gian thực.

### Bài 13 & Mục 7: Hoàn thiện 3 Module Nghiệp vụ JPA & Giao diện toàn diện
1. **Module 1 - Sinh Viên (`SinhVien`):**
   - Các trường: `id`, `maSinhVien` (Unique), `hoTen`, `email`, `lop`, `gpa`, `ngaySinh`.
   - Tự động tính toán xếp loại học lực (`Xuất sắc`, `Giỏi`, `Khá`, `Trung bình`, `Yếu`) theo thang điểm GPA 4.0.
   - Validate mã SV không trùng, định dạng email, GPA từ 0.0 - 4.0.
2. **Module 2 - Sách Thư Viện (`Sach`):**
   - Các trường: `id`, `maSach` (Unique), `tenSach`, `tacGia`, `theLoai`, `namXuatBan`, `gia`, `soLuong`.
   - Validate mã sách, kiểm tra đơn giá và số lượng tồn kho không âm.
3. **Module 3 - Sản Phẩm Kho Hàng (`SanPham`):**
   - Các trường: `id`, `maSanPham` (Unique), `tenSanPham`, `danhMuc`, `gia`, `soLuong`, `moTa`.
   - Validate mã sản phẩm, định dạng giá tiền VNĐ và kiểm soát số lượng kho.

---

## 5. KẾT QUẢ ĐẠT ĐƯỢC & CHECKLIST KIỂM THỬ

| STT | Tiêu chí kiểm thử | Kết quả thực tế | Trạng thái |
|---|---|---|---|
| 1 | Biên dịch & đóng gói Maven (`mvn clean package`) | Thành công (`BUILD SUCCESS`), sinh file `.war` chuẩn | ĐẠT |
| 2 | Chạy trên Tomcat 10 / Cargo (`mvn cargo:run`) | Khởi động mượt mà tại cổng 8080 | ĐẠT |
| 3 | Tự động tạo CSDL & nạp dữ liệu mẫu ban đầu | `AppContextListener` tự động nạp 3 user + sinh viên, sách, sản phẩm | ĐẠT |
| 4 | Đăng nhập tài khoản `ADMIN` | Đăng nhập thành công, thấy đủ menu Quản trị & Nghiệp vụ | ĐẠT |
| 5 | Đăng nhập tài khoản `STAFF` | Đăng nhập thành công, thấy menu Nghiệp vụ, không thấy menu Admin | ĐẠT |
| 6 | Đăng nhập tài khoản `USER` | Đăng nhập thành công, chỉ thấy Dashboard và Hồ sơ cá nhân | ĐẠT |
| 7 | Chặn truy cập trái quyền vào `/admin/*` | User thường và Staff truy cập `/admin/users` bị chuyển sang `403.jsp` | ĐẠT |
| 8 | Chặn truy cập trái quyền vào `/staff/*` | User thường chưa cấp quyền bị chuyển sang `403.jsp` | ĐẠT |
| 9 | Chưa đăng nhập truy cập trang bảo mật | Bị Filter chặn và chuyển hướng về `login.jsp` kèm URL redirect | ĐẠT |
| 10 | Đăng xuất và thử nút Back trên trình duyệt | `NoCacheFilter` chặn hiển thị cache, yêu cầu đăng nhập lại | ĐẠT |
| 11 | CRUD 3 module nghiệp vụ (Sinh viên, Sách, Sản phẩm) | Thêm, sửa, xóa, tìm kiếm hoạt động chính xác với JPA | ĐẠT |
| 12 | Validate form & thông báo lỗi/thành công | Hiển thị thông báo `alert` đẹp mắt, báo lỗi đỏ cạnh input | ĐẠT |
| 13 | Quản lý người dùng (Admin User Management) | Tạo mới, sửa, đổi vai trò, khóa/mở tài khoản, reset pass hoàn hảo | ĐẠT |
| 14 | Đổi mật khẩu & Cập nhật hồ sơ | Kiểm tra mật khẩu cũ, hash BCrypt mật khẩu mới thành công | ĐẠT |
| 15 | Ghi nhận Audit Log | Mọi hành động đều được lưu vào bảng `activity_logs` | ĐẠT |

---

## 6. TRẢ LỜI CÂU HỎI CỦNG CỐ KIẾN THỨC (MỤC 11)

### Câu 1: Authentication và Authorization khác nhau như thế nào?
- **Authentication (Xác thực):** Là quá trình xác minh danh tính của người dùng — trả lời cho câu hỏi *"Bạn là ai?"*. (Ví dụ: Kiểm tra email và mật khẩu tại `AuthController`).
- **Authorization (Phân quyền):** Là quá trình xác định quyền hạn của người dùng đã được xác thực — trả lời cho câu hỏi *"Bạn được phép làm những gì trong hệ thống?"*. (Ví dụ: `AuthorizationFilter` kiểm tra xem người dùng có vai trò `ADMIN` để vào trang `/admin/*` hay không).

### Câu 2: Vì sao cần dùng Filter để bảo vệ URL thay vì chỉ ẩn menu?
- Việc ẩn menu trên giao diện chỉ mang tính chất hỗ trợ trải nghiệm người dùng (UI/UX). Nếu chỉ ẩn menu, người dùng có hiểu biết kỹ thuật hoàn toàn có thể gõ trực tiếp đường dẫn URL trên thanh địa chỉ của trình duyệt hoặc gửi request bằng các công cụ như Postman/cURL để truy cập và can thiệp dữ liệu trái phép.
- Sử dụng **Filter** phía máy chủ (Server-side) đảm bảo mọi HTTP request đều bị chặn lại và kiểm tra quyền trước khi đến được Servlet/Controller xử lý, tạo nên lớp bảo vệ tuyệt đối an toàn.

### Câu 3: Dữ liệu người dùng nên lưu gì trong session và không nên lưu gì?
- **Nên lưu trong Session:** Các thông tin định danh và phân quyền gọn nhẹ cần dùng xuyên suốt các request như: `userId`, `email`, `fullName`, `role`, `loginTime`.
- **Không nên lưu trong Session:** Mật khẩu (kể cả mật khẩu đã hash), các đối tượng dữ liệu quá lớn (như danh sách hàng nghìn sinh viên/sản phẩm), các thông tin nhạy cảm về thẻ tín dụng/tài khoản ngân hàng nhằm tránh tốn bộ nhớ máy chủ và nguy cơ rò rỉ bảo mật khi session bị chiếm đoạt.

### Câu 4: Khi người dùng không đủ quyền, ứng dụng nên xử lý như thế nào?
- Hệ thống cần chặn request ngay tại `AuthorizationFilter`, không cho phép tiếp tục chuỗi xử lý (`FilterChain`).
- Chuyển hướng người dùng tới trang thông báo lỗi **HTTP 403 Forbidden** với giao diện thân thiện, giải thích lý do không có quyền truy cập, hiển thị vai trò hiện tại và cung cấp nút điều hướng quay về trang chủ/Dashboard hoặc liên kết đăng nhập bằng tài khoản khác.
- Tuyệt đối không để lộ thông tin nhạy cảm của hệ thống hay stack trace chi tiết.

### Câu 5: Vì sao mật khẩu không nên lưu dạng plain text trong hệ thống thật?
- Nếu lưu plain text (văn bản thô), khi cơ sở dữ liệu bị tấn công (SQL Injection, rò rỉ file backup, nhân viên nội bộ xem lén), toàn bộ mật khẩu của người dùng sẽ bị lộ ngay lập tức.
- Người dùng thường có thói quen dùng chung một mật khẩu cho nhiều dịch vụ khác nhau, gây ra hậu quả dây chuyền nghiêm trọng.
- Do đó, mật khẩu bắt buộc phải được băm một chiều (One-way Hashing) bằng các thuật toán mạnh kết hợp Salt ngẫu nhiên như **BCrypt**, **Argon2**, hoặc **PBKDF2**.

### Câu 6: Các URL public và private trong ứng dụng cần được phân loại thế nào?
- **URL Public:** Các tài nguyên mà bất kỳ ai (kể cả khách chưa đăng nhập) đều có thể truy cập: `/index.jsp`, `/login.jsp`, `/auth`, các file tĩnh `/assets/css/*`, `/assets/js/*`, hình ảnh, tài liệu giới thiệu.
- **URL Private:** Các tài nguyên nghiệp vụ và quản trị yêu cầu phải đăng nhập:
  - Phân vùng chung: `/dashboard`, `/user/profile`, `/user/change-password` (Dành cho tất cả user đã đăng nhập).
  - Phân vùng nghiệp vụ: `/staff/*` (Dành cho `STAFF` và `ADMIN`).
  - Phân vùng quản trị cao cấp: `/admin/*` (Dành riêng cho `ADMIN`).

### Câu 7: Transaction có vai trò gì khi quản lý người dùng và đổi mật khẩu?
- **Tính toàn vẹn (ACID):** Đảm bảo các thao tác cập nhật dữ liệu được thực thi trọn vẹn (All-or-Nothing).
- Khi đổi mật khẩu hoặc cập nhật tài khoản, nếu có lỗi xảy ra giữa chừng (như lỗi kết nối DB, lỗi mã hóa), Transaction sẽ tự động `Rollback` để đưa CSDL về trạng thái an toàn ban đầu, ngăn chặn việc dữ liệu bị lỗi hoặc sai lệch trạng thái.

### Câu 8: Ứng dụng Lab 10 đã tổng hợp những nội dung nào từ Lab 6 đến Lab 9?
- **Từ Lab 6:** Cấu trúc Maven Web Project, Servlet cơ bản, vòng đời Web Application và file cấu hình `web.xml`.
- **Từ Lab 7:** Kiến trúc MVC Model 2 (Model - View - Controller), CRUD dữ liệu và kỹ thuật lọc `Filter`, `Listener`.
- **Từ Lab 8:** Kỹ thuật kiểm tra tính hợp lệ dữ liệu (Server-side Validation), quản lý và hiển thị thông báo lỗi/thành công (Messages/Alerts).
- **Từ Lab 9:** Kết nối cơ sở dữ liệu với **Hibernate JPA (Java Persistence API)**, quản lý `EntityManager`, thiết kế Entity quan hệ, thực thi câu lệnh JPQL và quản lý Transaction.
- **Tại Lab 10:** Ghép nối hoàn chỉnh toàn bộ các thành phần trên thành một hệ thống ứng dụng quản lý đa lớp thống nhất, bảo mật với phân quyền vai trò (RBAC) và bảo vệ URL toàn diện.

---

## 7. TỰ ĐÁNH GIÁ VÀ KẾT LUẬN
- **Mức độ hoàn thành:** Hoàn thành 100% tất cả các yêu cầu bắt buộc (Bài 1 - 5), các bài tự làm mở rộng (Bài 6 - 13) và bài tập tổng hợp cuối bài Lab (Mục 7).
- **Điểm nổi bật của sản phẩm:**
  - Kiến trúc phân lớp chuẩn mực, mã nguồn rõ ràng, clean code và tối ưu hóa xử lý lỗi.
  - Tự động cấu hình và nạp dữ liệu chạy ngay (Zero Configuration) với H2 Embedded, đồng thời tương thích hoàn hảo với MySQL 8.x.
  - Giao diện Bootstrap 5 hiện đại, thân thiện trên cả máy tính và thiết bị di động.
  - Có đầy đủ tính năng Audit Logs và các trang lỗi tùy chỉnh chuyên nghiệp.
