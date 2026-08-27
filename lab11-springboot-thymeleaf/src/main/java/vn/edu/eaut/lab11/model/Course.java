package vn.edu.eaut.lab11.model;

import java.io.Serializable;

/**
 * Model đại diện cho một Khóa học / Học phần (Bài 8 - Lab 11)
 */
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    private String courseCode;
    private String courseName;
    private int credits;
    private String lecturer;
    private String description;

    public Course() {
    }

    public Course(String courseCode, String courseName, int credits, String lecturer, String description) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.lecturer = lecturer;
        this.description = description;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credits=" + credits +
                ", lecturer='" + lecturer + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
