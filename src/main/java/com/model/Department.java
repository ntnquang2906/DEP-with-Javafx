/*-----------------------------------
Student name: Tien Nhat Quang Nguyen
Student number: 7722242
Subject code: CSIT213
-----------------------------------*/

public class Department {
    private int deptNumber;
    private String deptName;
    private int managerNumber;
    private double budget;
    private String startDate;

    public Department(int deptNumber, String deptName, int managerNumber, double budget, String startDate) {
        this.deptNumber = deptNumber;
        this.deptName = deptName;
        this.managerNumber = managerNumber;
        this.budget = budget;
        this.startDate = startDate;
    }

    public int getDeptNumber() {
        return deptNumber;
    }

    public String getDeptName() {
        return deptName;
    }

    public int getManagerNumber() {
        return managerNumber;
    }

    public double getBudget() {
        return budget;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setDeptNumber(int deptNumber) {
        this.deptNumber = deptNumber;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setManagerNumber(int managerNumber) {
        this.managerNumber = managerNumber;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return deptNumber + ", " + deptName + ", " + managerNumber + ", " + String.format("%.2f", budget) + ", " + startDate;
    }
}