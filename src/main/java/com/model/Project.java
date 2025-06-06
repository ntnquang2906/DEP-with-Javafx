/*-----------------------------------
Student name: Tien Nhat Quang Nguyen
Student number: 7722242
Subject code: CSIT213
-----------------------------------*/

package com.model;

public class Project {
    private int projNumber;
    private String title;
    private String sponsor;
    private int deptNumber;
    private double budget;

    public Project(int projNumber, String title, String sponsor, int deptNumber, double budget) {
        this.projNumber = projNumber;
        this.title = title;
        this.sponsor = sponsor;
        this.deptNumber = deptNumber;
        this.budget = budget;
    }

    public int getProjNumber() {
        return projNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getSponsor() {
        return sponsor;
    }

    public int getDeptNumber() {
        return deptNumber;
    }

    public double getBudget() {
        return budget;
    }

    public void setProjNumber(int projNumber) {
        this.projNumber = projNumber;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public void setDeptNumber(int deptNumber) {
        this.deptNumber = deptNumber;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return projNumber + ", " + title + ", " + sponsor + ", " + deptNumber + ", " + String.format("%.0f", budget);
    }
}