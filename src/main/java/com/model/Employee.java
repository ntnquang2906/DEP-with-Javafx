/*-----------------------------------
Student name: Tien Nhat Quang Nguyen
Student number: 7722242
Subject code: CSIT213
-----------------------------------*/

public abstract class Employee {
    private int empNumber;
    private String name;
    private String dob;
    private String address;
    private String gender;
    private double salary;
    private int supervisorNumber;
    private int deptNumber;

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

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public double getSalary() {
        return salary;
    }

    public int getSupervisorNumber() {
        return supervisorNumber;
    }

    public int getDeptNumber() {
        return deptNumber;
    }

    public void setEmpNumber(int empNumber) {
        this.empNumber = empNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setSupervisorNumber(int supervisorNumber) {
        this.supervisorNumber = supervisorNumber;
    }

    public void setDeptNumber(int deptNumber) {
        this.deptNumber = deptNumber;
    }

    public abstract String toString();
}