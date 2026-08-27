package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab6.model.Student;
import vn.edu.eaut.lab6.store.StudentStore;

import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private final StudentStore studentStore = StudentStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = value(request.getParameter("action"));
        if ("new".equals(action)) {
            showForm(request, response, new Student(), "add");
            return;
        }
        if ("edit".equals(action)) {
            Student student = studentStore.findById(request.getParameter("id"));
            if (student == null) {
                response.sendRedirect(request.getContextPath() + "/students");
            } else {
                showForm(request, response, student, "update");
            }
            return;
        }
        if ("delete".equals(action)) {
            studentStore.deleteById(request.getParameter("id"));
            response.sendRedirect(request.getContextPath() + "/students");
            return;
        }

        String keyword = value(request.getParameter("keyword"));
        request.setAttribute("students", studentStore.searchByName(keyword));
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("/student-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = value(request.getParameter("action"));
        Student student = new Student(value(request.getParameter("id")), value(request.getParameter("name")),
                value(request.getParameter("className")), value(request.getParameter("email")));
        String error = validate(student, "add".equals(action));
        if (error != null) {
            request.setAttribute("error", error);
            showForm(request, response, student, action);
            return;
        }

        boolean successful = "update".equals(action) ? studentStore.update(student) : studentStore.add(student);
        if (!successful) {
            request.setAttribute("error", "Không thể lưu sinh viên. Mã sinh viên có thể đã tồn tại.");
            showForm(request, response, student, action);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/students");
    }

    private String validate(Student student, boolean adding) {
        if (student.getId().isEmpty()) return "Mã sinh viên không được để trống.";
        if (adding && studentStore.findById(student.getId()) != null) return "Mã sinh viên đã tồn tại.";
        if (student.getName().isEmpty()) return "Họ tên không được để trống.";
        if (student.getClassName().isEmpty()) return "Lớp không được để trống.";
        if (student.getEmail().isEmpty() || !EMAIL_PATTERN.matcher(student.getEmail()).matches()) {
            return "Email không hợp lệ.";
        }
        return null;
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response, Student student, String action)
            throws ServletException, IOException {
        request.setAttribute("student", student);
        request.setAttribute("formAction", "update".equals(action) ? "update" : "add");
        request.getRequestDispatcher("/student-form.jsp").forward(request, response);
    }

    private String value(String input) {
        return input == null ? "" : input.trim();
    }
}
