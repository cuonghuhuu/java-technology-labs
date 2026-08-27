package com.javalab.tuyen.model;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String studentId;
    private String fullName;
    private double attendanceScore; // Điểm chuyên cần (10%)
    private double midtermScore;    // Điểm giữa kỳ (30%)
    private double finalScore;      // Điểm cuối kỳ (60%)
    private double totalScore;      // Điểm tổng kết
    private String gradeLetter;     // Xếp loại (A, B, C, D, F)

    public Student() {
    }

    public Student(String studentId, String fullName, double attendanceScore, double midtermScore, double finalScore) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.attendanceScore = attendanceScore;
        this.midtermScore = midtermScore;
        this.finalScore = finalScore;
        calculateGrade();
    }

    public void calculateGrade() {
        // Điểm tổng kết = chuyên cần x 10% + giữa kỳ x 30% + cuối kỳ x 60%
        this.totalScore = (this.attendanceScore * 0.10) + (this.midtermScore * 0.30) + (this.finalScore * 0.60);
        
        // Xếp loại: A: từ 8.5; B: từ 7.0; C: từ 5.5; D: từ 4.0; F: dưới 4.0
        if (this.totalScore >= 8.5) {
            this.gradeLetter = "A";
        } else if (this.totalScore >= 7.0) {
            this.gradeLetter = "B";
        } else if (this.totalScore >= 5.5) {
            this.gradeLetter = "C";
        } else if (this.totalScore >= 4.0) {
            this.gradeLetter = "D";
        } else {
            this.gradeLetter = "F";
        }
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getAttendanceScore() {
        return attendanceScore;
    }

    public void setAttendanceScore(double attendanceScore) {
        this.attendanceScore = attendanceScore;
    }

    public double getMidtermScore() {
        return midtermScore;
    }

    public void setMidtermScore(double midtermScore) {
        this.midtermScore = midtermScore;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public String getGradeLetter() {
        return gradeLetter;
    }

    public String getTotalScoreFormatted() {
        return String.format("%.2f", totalScore);
    }

    public String getSummaryString() {
        return String.format("%s - %s - %.2f - %s", studentId, fullName, totalScore, gradeLetter);
    }
}
