/*-----------------------------------
Student name: Tien Nhat Quang Nguyen
Student number: 7722242
Subject code: CSIT213
-----------------------------------*/

package com.model;

public class Developer extends Employee {
    private String programmingLanguages;

    public Developer(int empNumber, String name, String dob, String address, String gender, double salary,
                     int supervisorNumber, int deptNumber, String programmingLanguages) {
        super(empNumber, name, dob, address, gender, salary, supervisorNumber, deptNumber);
        this.programmingLanguages = programmingLanguages;
    }

    public String getProgrammingLanguages() {
        return programmingLanguages;
    }

    public String getLanguage() {
        return programmingLanguages;
    }

    public void setProgrammingLanguages(String programmingLanguages) {
        this.programmingLanguages = programmingLanguages;
    }

    @Override
    public String toString() {
        return "D, " + getEmpNumber() + ", " + getName() + ", " + getDob() + ", " + getAddress() + ", "
                + getGender() + ", " + String.format("%.1f", getSalary()) + ", " + getSupervisorNumber()
                + ", " + getDeptNumber()
                + (programmingLanguages.trim().isEmpty() ? "" : ", " + programmingLanguages.trim());
    }
}