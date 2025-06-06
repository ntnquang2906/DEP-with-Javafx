/*-----------------------------------
Student name: Tien Nhat Quang Nguyen
Student number: 7722242
Subject code: CSIT213
-----------------------------------*/

package com.model;

public class Admin extends Employee {
    private String skills;

    public Admin(int empNumber, String name, String dob, String address, String gender, double salary, int supervisorNumber, int deptNumber, String skills) {
        super(empNumber, name, dob, address, gender, salary, supervisorNumber, deptNumber);
        this.skills = skills;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    // 👇 Add this to match the expected call in DEP.java
    public String getFunction() {
        return skills;
    }

    @Override
    public String toString() {
        return "A, " + getEmpNumber() + ", " + getName() + ", " + getDob() + ", " + getAddress() + ", " + getGender() + ", " + String.format("%.1f", getSalary()) + ", " + getSupervisorNumber() + ", " + getDeptNumber() + (skills.trim().isEmpty() ? "" : ", " + skills.trim());
    }
}
