package com.javalab.tuyen.service;

import com.javalab.tuyen.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final List<Student> studentList = new ArrayList<>();

    public StudentService() {
        // Mẫu dữ liệu ban đầu
        Student demo1 = new Student("SV001", "Nguyễn Văn An", 8.0, 7.0, 9.0);
        Student demo2 = new Student("SV002", "Trần Thị Bích", 9.0, 8.5, 9.5);
        studentList.add(demo1);
        studentList.add(demo2);
    }

    public static String validateStudentInput(String studentId, String fullName, String attendanceStr, String midtermStr, String finalStr) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return "Mã sinh viên không được để trống!";
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Họ tên sinh viên không được để trống!";
        }

        double attendance, midterm, finalSc;

        try {
            attendance = Double.parseDouble(attendanceStr);
        } catch (NumberFormatException e) {
            return "Điểm chuyên cần phải là số hợp lệ (ví dụ: 8 hoặc 8.5)!";
        }

        try {
            midterm = Double.parseDouble(midtermStr);
        } catch (NumberFormatException e) {
            return "Điểm giữa kỳ phải là số hợp lệ (ví dụ: 7 hoặc 7.5)!";
        }

        try {
            finalSc = Double.parseDouble(finalStr);
        } catch (NumberFormatException e) {
            return "Điểm cuối kỳ phải là số hợp lệ (ví dụ: 9 hoặc 9.0)!";
        }

        if (attendance < 0 || attendance > 10) {
            return "Điểm chuyên cần " + attendance + " không hợp lệ! (Phải từ 0 đến 10)";
        }

        if (midterm < 0 || midterm > 10) {
            return "Điểm giữa kỳ " + midterm + " không hợp lệ! (Phải từ 0 đến 10)";
        }

        if (finalSc < 0 || finalSc > 10) {
            return "Điểm cuối kỳ " + finalSc + " không hợp lệ! (Phải từ 0 đến 10)";
        }

        return null; // Không có lỗi
    }

    public synchronized void addStudent(Student student) {
        studentList.add(student);
    }

    public synchronized List<Student> getAllStudents() {
        return new ArrayList<>(studentList);
    }

    public synchronized void clearAll() {
        studentList.clear();
    }
}
