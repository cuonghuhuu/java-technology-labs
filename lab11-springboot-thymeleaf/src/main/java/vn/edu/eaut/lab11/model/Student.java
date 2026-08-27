package vn.edu.eaut.lab11.model;

import java.io.Serializable;

/**
 * Model đại diện cho một Sinh viên (Bài 3 - Lab 11)
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private String studentCode;
    private String fullName;
    private String email;
    private String className;

    public Student() {
    }

    public Student(String studentCode, String fullName, String email, String className) {
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.email = email;
        this.className = className;
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
                "studentCode='" + studentCode + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
