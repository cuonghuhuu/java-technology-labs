package vn.edu.eaut.lab12.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Student {

    private Long id;

    @NotBlank(message = "Mã sinh viên không được để trống")
    @Size(min = 5, message = "Mã sinh viên tối thiểu 5 ký tự")
    private String studentCode;

    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Lớp không được để trống")
    private String className;

    // Constructors
    public Student() {
    }

    public Student(Long id, String studentCode, String fullName, String email, String className) {
        this.id = id;
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.email = email;
        this.className = className;
    }

    public Student(String studentCode, String fullName, String email, String className) {
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.email = email;
        this.className = className;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentCode='" + studentCode + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
