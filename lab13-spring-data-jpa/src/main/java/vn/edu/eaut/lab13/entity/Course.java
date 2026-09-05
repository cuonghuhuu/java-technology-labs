package vn.edu.eaut.lab13.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Bài 8: Entity Course gồm mã môn, tên môn, số tín chỉ.
 */
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã môn học không được để trống")
    @Size(min = 2, max = 20, message = "Mã môn học phải từ 2 đến 20 ký tự")
    @Column(name = "course_code", nullable = false, unique = true, length = 20)
    private String courseCode;

    @NotBlank(message = "Tên môn học không được để trống")
    @Size(min = 2, max = 150, message = "Tên môn học phải từ 2 đến 150 ký tự")
    @Column(name = "course_name", nullable = false, length = 150)
    private String courseName;

    @NotNull(message = "Số tín chỉ không được để trống")
    @Min(value = 1, message = "Số tín chỉ tối thiểu là 1")
    @Max(value = 10, message = "Số tín chỉ tối đa là 10")
    @Column(name = "credits", nullable = false)
    private Integer credits;

    @Column(name = "description", length = 500)
    private String description;

    public Course() {
    }

    public Course(String courseCode, String courseName, Integer credits, String description) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.description = description;
    }

    public Course(Long id, String courseCode, String courseName, Integer credits, String description) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode != null ? courseCode.trim() : null;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName != null ? courseName.trim() : null;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description.trim() : null;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credits=" + credits +
                ", description='" + description + '\'' +
                '}';
    }
}
