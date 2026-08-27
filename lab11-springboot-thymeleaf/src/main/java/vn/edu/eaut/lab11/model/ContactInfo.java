package vn.edu.eaut.lab11.model;

import java.io.Serializable;

/**
 * Model đại diện thông tin liên hệ Khoa / Bộ môn (Bài 6 - Lab 11)
 */
public class ContactInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String facultyName;
    private String university;
    private String address;
    private String email;
    private String phone;
    private String hotline;
    private String website;
    private String workingHours;
    private String departmentHead;

    public ContactInfo() {
    }

    public ContactInfo(String facultyName, String university, String address, String email, 
                       String phone, String hotline, String website, String workingHours, String departmentHead) {
        this.facultyName = facultyName;
        this.university = university;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.hotline = hotline;
        this.website = website;
        this.workingHours = workingHours;
        this.departmentHead = departmentHead;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(String departmentHead) {
        this.departmentHead = departmentHead;
    }
}
