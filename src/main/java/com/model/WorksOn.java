/*-----------------------------------
Student name: Tien Nhat Quang Nguyen
Student number: 7722242
Subject code: CSIT213
-----------------------------------*/

public class WorksOn {
    private int empNumber;
    private int projNumber;
    private double hours;

    public WorksOn(int empNumber, int projNumber, double hours) {
        this.empNumber = empNumber;
        this.projNumber = projNumber;
        this.hours = hours;
    }

    public int getEmpNumber() {
        return empNumber;
    }

    public int getProjNumber() {
        return projNumber;
    }

    public double getHours() {
        return hours;
    }

    public void setEmpNumber(int empNumber) {
        this.empNumber = empNumber;
    }

    public void setProjNumber(int projNumber) {
        this.projNumber = projNumber;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    // Used for saving to file
    @Override
    public String toString() {
        return empNumber + ", " + projNumber + ", " + String.format("%.0f", hours);
    }

    // Optional method to show user-friendly format in ListView
    public String toDisplayString() {
        return "Employee " + empNumber + " works on Project " + projNumber + " for " + hours + " hours";
    }
} 
