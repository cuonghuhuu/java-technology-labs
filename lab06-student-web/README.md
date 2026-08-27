x# Lab 6 - Student Web

Ứng dụng quản lý sinh viên cơ bản theo MVC, phục vụ Lab 6 Công nghệ Java IT3242.

## Công nghệ

- Java 17
- Jakarta Servlet 6.0
- JSP và JSTL 3
- Apache Tomcat 10.1
- Maven

## Kiến trúc

- **Model:** `Student`
- **Store:** `StudentStore` (dữ liệu trong bộ nhớ)
- **Controller:** các Servlet
- **View:** JSP + JSTL/EL
- **Filter:** `AuthFilter`, `AccessLogFilter`
- **Listener:** `AppContextListener`, `SessionLogListener`

## Build

```powershell
mvn clean package
```

WAR được tạo tại `target/lab06-student-web.war`.

## Deploy Tomcat

Copy `target/lab06-student-web.war` vào thư mục `webapps` của Apache Tomcat 10.1, ví dụ:

```powershell
Copy-Item ".\target\lab06-student-web.war" "D:\Tools\apache-tomcat-10.1.57\webapps\" -Force
```

Mở: `http://localhost:8080/lab06-student-web/`

## Tài khoản demo

- `admin / 123456` → `ADMIN`
- `user / 123456` → `USER` (tài khoản demo bổ sung để minh họa phân quyền Bài 9)

## Chức năng

- HelloServlet
- CRUD sinh viên trong bộ nhớ, tìm kiếm theo họ tên
- Login, logout, HttpSession
- Phân quyền ADMIN/USER ở server-side
- Dashboard tổng số sinh viên và thống kê theo lớp
- AuthFilter và AccessLogFilter
- Application/Session Listener
- JSP dùng JSTL/EL, không dùng Java scriptlet để render dữ liệu

## Lưu ý dữ liệu

`StudentStore` giữ một danh sách duy nhất trong bộ nhớ. Listener khởi tạo năm sinh viên mẫu `SV001` đến `SV005` khi ứng dụng khởi động. Restart hoặc redeploy có thể đưa dữ liệu trở lại dữ liệu mẫu.
