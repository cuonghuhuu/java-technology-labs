package vn.edu.eaut.lab13.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã sinh viên không được để trống")
    @Size(min = 3, max = 20, message = "Mã sinh viên phải từ 3 đến 20 ký tự")
    @Column(name = "student_code", nullable = false, unique = true, length = 20)
    private String studentCode;

    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ và tên phải từ 2 đến 100 ký tự")
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Column(name = "email", length = 100)
    private String email;

    @NotBlank(message = "Lớp không được để trống")
    @Column(name = "class_name", length = 50)
    private String className;

    public Student() {
    }

    public Student(String studentCode, String fullName, String email, String className) {
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.email = email;
        this.className = className;
    }

    public Student(Long id, String studentCode, String fullName, String email, String className) {
        this.id = id;
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
        this.studentCode = studentCode != null ? studentCode.trim() : null;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName != null ? fullName.trim() : null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email.trim() : null;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className != null ? className.trim() : null;
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
