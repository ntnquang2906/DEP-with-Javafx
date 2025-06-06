/*-----------------------------------
Student name: Tien Nhat Quang Nguyen
Student number: 7722242
Subject code: CSIT213
-----------------------------------*/

package com.model;

//Abstract class Employee – base class for Admin and Developer.
public abstract class Employee {
    // Common employee fields
    private int empNumber;
    private String name;
    private String dob;
    private String address;
    private String gender;
    private double salary;
    private int supervisorNumber;
    private int deptNumber;

    // Constructor to initialize all common fields
    public Employee(int empNumber, String name, String dob, String address, String gender,
                    double salary, int supervisorNumber, int deptNumber) {
        this.empNumber = empNumber;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.gender = gender;
        this.salary = salary;
        this.supervisorNumber = supervisorNumber;
        this.deptNumber = deptNumber;
    }

    public int getEmpNumber() {
        return empNumber;
    }

    public void setEmpNumber(int empNumber) {
        this.empNumber = empNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getSupervisorNumber() {
        return supervisorNumber;
    }

    public void setSupervisorNumber(int supervisorNumber) {
        this.supervisorNumber = supervisorNumber;
    }

    public int getDeptNumber() {
        return deptNumber;
    }

    public void setDeptNumber(int deptNumber) {
        this.deptNumber = deptNumber;
    }

    public abstract String toString();
}