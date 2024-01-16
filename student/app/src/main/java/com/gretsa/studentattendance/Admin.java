package com.gretsa.studentattendance;

public class Admin {
    private String adminId;
    private String fullName;
    private String employeeNumber;
    private String department;

    public Admin() {
        // Default constructor required for calls to DataSnapshot.getValue(Admin.class)
    }

    public Admin(String adminId, String fullName, String employeeNumber, String department) {
        this.adminId = adminId;
        this.fullName = fullName;
        this.employeeNumber = employeeNumber;
        this.department = department;
    }

    // Getters and setters...

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
