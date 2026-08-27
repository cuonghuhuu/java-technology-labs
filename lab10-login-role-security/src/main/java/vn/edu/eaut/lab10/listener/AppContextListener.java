package vn.edu.eaut.lab10.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.edu.eaut.lab10.model.*;
import vn.edu.eaut.lab10.repository.UserRepository;
import vn.edu.eaut.lab10.service.*;
import vn.edu.eaut.lab10.util.JPAUtil;
import vn.edu.eaut.lab10.util.PasswordUtil;

import java.time.LocalDate;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== Starting Lab 10 Application & Initializing Database ===");
        try {
            // Test JPA connection
            JPAUtil.getEntityManagerFactory();

            // Seed Initial Users (Bai 6)
            UserRepository userRepository = new UserRepository();
            if (userRepository.count() == 0) {
                System.out.println("=== Seeding Initial Users (ADMIN, STAFF, USER) ===");
                User admin = new User("admin@eaut.edu.vn", PasswordUtil.hashPassword("123456"), "Quản Trị Viên (Admin)", Role.ADMIN, true);
                User staff = new User("staff@eaut.edu.vn", PasswordUtil.hashPassword("123456"), "Nhân Viên Nghiệp Vụ (Staff)", Role.STAFF, true);
                User user = new User("user@eaut.edu.vn", PasswordUtil.hashPassword("123456"), "Người Dùng Thường (User)", Role.USER, true);

                userRepository.save(admin);
                userRepository.save(staff);
                userRepository.save(user);
            }

            // Seed Initial Students
            SinhVienService svService = new SinhVienService();
            if (svService.count() == 0) {
                System.out.println("=== Seeding Initial Students ===");
                svService.save(new SinhVien("SV001", "Nguyễn Văn An", "an.nv@eaut.edu.vn", "DCCNTT15.10.1", 3.65, LocalDate.of(2003, 5, 12)));
                svService.save(new SinhVien("SV002", "Trần Thị Bình", "binh.tt@eaut.edu.vn", "DCCNTT15.10.2", 3.40, LocalDate.of(2003, 8, 20)));
                svService.save(new SinhVien("SV003", "Lê Văn Cường", "cuong.lv@eaut.edu.vn", "DCCNTT15.10.1", 3.85, LocalDate.of(2003, 11, 5)));
                svService.save(new SinhVien("SV004", "Phạm Thu Hà", "ha.pt@eaut.edu.vn", "DCCNTT15.10.3", 3.20, LocalDate.of(2003, 2, 18)));
                svService.save(new SinhVien("SV005", "Hoàng Minh Đức", "duc.hm@eaut.edu.vn", "DCCNTT15.10.2", 2.95, LocalDate.of(2003, 9, 30)));
            }

            // Seed Initial Books
            SachService sachService = new SachService();
            if (sachService.count() == 0) {
                System.out.println("=== Seeding Initial Books ===");
                sachService.save(new Sach("BK001", "Lập trình Java Căn bản & Nâng cao", "Nguyễn Vũ", "Công nghệ thông tin", 2023, 150000.0, 35));
                sachService.save(new Sach("BK002", "Phát triển Web với Jakarta EE & Spring", "Trần Nam", "Công nghệ thông tin", 2024, 185000.0, 20));
                sachService.save(new Sach("BK003", "Thiết kế Kiến trúc Phần mềm Microservices", "Lê Hùng", "Kiến trúc phần mềm", 2022, 220000.0, 15));
                sachService.save(new Sach("BK004", "Cấu trúc Dữ liệu và Giải thuật với Java", "Đỗ Hưng", "Khoa học máy tính", 2021, 130000.0, 50));
                sachService.save(new Sach("BK005", "Học sâu và Trí tuệ Nhân tạo thực chiến", "Phạm Long", "Trí tuệ nhân tạo", 2024, 260000.0, 18));
            }

            // Seed Initial Products
            SanPhamService spService = new SanPhamService();
            if (spService.count() == 0) {
                System.out.println("=== Seeding Initial Products ===");
                spService.save(new SanPham("SP001", "Laptop Dell XPS 15 9530", "Máy tính xách tay", 38500000.0, 12, "Core i7 13700H, RAM 32GB, SSD 1TB, RTX 4060"));
                spService.save(new SanPham("SP002", "MacBook Pro 14 M3 Pro", "Máy tính xách tay", 49900000.0, 8, "Apple M3 Pro 18GB Unified Memory, SSD 512GB"));
                spService.save(new SanPham("SP003", "Màn hình Dell UltraSharp 27 4K", "Màn hình máy tính", 12800000.0, 25, "27 inch IPS 4K HDR400, USB-C 90W Hub"));
                spService.save(new SanPham("SP004", "Bàn phím cơ Keychron Q1 Pro", "Phụ kiện", 4200000.0, 30, "Custom Wireless Mechanical Keyboard, QMK/VIA"));
                spService.save(new SanPham("SP005", "Chuột không dây Logitech MX Master 3S", "Phụ kiện", 2450000.0, 45, "Cảm biến 8K DPI, cuộn vô cực MagSpeed"));
            }

            // Initial log
            ActivityLogService logService = new ActivityLogService();
            logService.log("SYSTEM", "SYSTEM", "SERVER_START", "Ứng dụng Lab 10 đã khởi động thành công", "127.0.0.1", "SUCCESS");

            System.out.println("=== Lab 10 Initialized Successfully ===");
        } catch (Exception e) {
            System.err.println("=== Error initializing Lab 10: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("=== Shutting down Lab 10 & closing EntityManagerFactory ===");
        JPAUtil.close();
    }
}
