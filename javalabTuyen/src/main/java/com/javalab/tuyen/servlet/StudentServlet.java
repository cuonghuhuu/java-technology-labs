package com.javalab.tuyen.servlet;

import com.javalab.tuyen.model.Student;
import com.javalab.tuyen.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "StudentServlet", urlPatterns = {"/student", "/calculate"})
public class StudentServlet extends HttpServlet {

    private StudentService studentService;

    @Override
    public void init() throws ServletException {
        super.init();
        studentService = new StudentService();
        getServletContext().setAttribute("studentService", studentService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if ("clear".equals(action)) {
            studentService.clearAll();
            request.setAttribute("successMessage", "Đã xóa toàn bộ danh sách sinh viên.");
        }

        request.setAttribute("students", studentService.getAllStudents());
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String studentId = request.getParameter("studentId");
        String fullName = request.getParameter("fullName");
        String attendanceStr = request.getParameter("attendanceScore");
        String midtermStr = request.getParameter("midtermScore");
        String finalStr = request.getParameter("finalScore");

        // 1. Kiểm tra dữ liệu (Validation)
        String errorMessage = StudentService.validateStudentInput(
                studentId, fullName, attendanceStr, midtermStr, finalStr);

        if (errorMessage != null) {
            // Có lỗi -> Giữ lại thông tin đã nhập và hiển thị thông báo lỗi
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("studentId", studentId);
            request.setAttribute("fullName", fullName);
            request.setAttribute("attendanceScore", attendanceStr);
            request.setAttribute("midtermScore", midtermStr);
            request.setAttribute("finalScore", finalStr);
            request.setAttribute("students", studentService.getAllStudents());

            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        // 2. Chuyển đổi điểm & 3. Tính điểm tổng kết + Xếp loại
        double attendance = Double.parseDouble(attendanceStr);
        double midterm = Double.parseDouble(midtermStr);
        double finalSc = Double.parseDouble(finalStr);

        Student student = new Student(studentId.trim(), fullName.trim(), attendance, midterm, finalSc);

        // Lưu vào danh sách sinh viên
        studentService.addStudent(student);

        // 4. Hiển thị kết quả
        request.setAttribute("currentStudent", student);
        request.setAttribute("students", studentService.getAllStudents());
        request.setAttribute("successMessage", "Tính điểm và xếp loại thành công cho sinh viên: " + student.getSummaryString());

        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }
}
