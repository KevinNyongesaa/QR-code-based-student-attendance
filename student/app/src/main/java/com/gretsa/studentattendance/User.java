package com.gretsa.studentattendance;

public class User {
    private String course;
    private String fullName;
    private String regNumber;
    private String email;

    // Default constructor (required for Firebase)
    public User() {
    }

    public User(String course, String fullName, String regNumber) {
        this.course = course;
        this.fullName = fullName;
        this.regNumber = regNumber;
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
}
