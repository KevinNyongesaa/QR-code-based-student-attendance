package com.gretsa.studentattendance;

public class Attendance {
    private String time;
    private String registrationNumber;

    public Attendance() {
        // Default constructor required for Firestore
    }

    public Attendance(String time, String registrationNumber) {
        this.time = time;
        this.registrationNumber = registrationNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
