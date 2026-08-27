package vn.edu.eaut.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.validation.constraints.*;
import java.io.Serializable;

@Named("studentBean")
@RequestScoped
public class StudentBean implements Serializable {

    @NotBlank(message = "Mã sinh viên không được để trống")
    @Pattern(regexp = "^SV\\d{3,}$", message = "Mã sinh viên phải có dạng SVxxx (ví dụ: SV001)")
    private String studentId;

    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 2, max = 50, message = "Họ tên phải từ 2 đến 50 ký tự")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotNull(message = "Điểm TB không được để trống")
    @DecimalMin(value = "0.0", message = "Điểm TB phải từ 0.0 đến 10.0")
    @DecimalMax(value = "10.0", message = "Điểm TB phải từ 0.0 đến 10.0")
    private Double gpa;

    private String className;

    public String submit() {
        return "result?faces-redirect=true";
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Double getGpa() { return gpa; }
    public void setGpa(Double gpa) { this.gpa = gpa; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
}
