/*-----------------------------------
Student name: Tien Nhat Quang Nguyen
Student number: 7722242
Subject code: CSIT213
-----------------------------------*/

package com.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

 //The DataIO class provides static methods for loading data from files
public class DataIO {

    // Base path where the input data files are stored
    private static final String RESOURCE_PATH = "src/main/resources/";

    //Loads department data from a file into an ArrayList of Department objects.
    public static ArrayList<Department> loadDepartments(String fileName) {
        ArrayList<Department> departments = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(RESOURCE_PATH + fileName))) {
            scanner.useDelimiter(", |\\r\\n|\\n"); 
            while (scanner.hasNext()) {
                int deptNumber = Integer.parseInt(scanner.next());
                String deptName = scanner.next();
                int managerNumber = Integer.parseInt(scanner.next());
                double budget = Double.parseDouble(scanner.next());
                String startDate = scanner.next();
                departments.add(new Department(deptNumber, deptName, managerNumber, budget, startDate));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error loading departments from " + fileName + ": " + e.getMessage());
        }
        return departments;
    }

    //Loads employee data from a file and creates Admin or Developer objects based on type.
    public static ArrayList<Employee> loadEmployees(String fileName) {
        ArrayList<Employee> employees = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(RESOURCE_PATH + fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] parts = line.split(", ");

                if (parts.length < 10) continue; // Skip invalid/incomplete lines

                // Parse common fields
                String type = parts[0];
                int empNumber = Integer.parseInt(parts[1]);
                String name = parts[2];
                String dob = parts[3];
                String address = parts[4];
                String gender = parts[5];
                double salary = Double.parseDouble(parts[6]);
                int supervisorNumber = Integer.parseInt(parts[7]);
                int deptNumber = Integer.parseInt(parts[8]);
                String extra = parts[9]; // Skill (Developer) or Title (Admin)

                // Create appropriate subclass based on type
                if (type.equals("A")) {
                    employees.add(new Admin(empNumber, name, dob, address, gender, salary, supervisorNumber, deptNumber, extra));
                } else if (type.equals("D")) {
                    employees.add(new Developer(empNumber, name, dob, address, gender, salary, supervisorNumber, deptNumber, extra));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error loading employees from " + fileName + ": " + e.getMessage());
        }
        return employees;
    }

    //Loads project data from a file into an ArrayList of Project objects.
    public static ArrayList<Project> loadProjects(String fileName) {
        ArrayList<Project> projects = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(RESOURCE_PATH + fileName))) {
            scanner.useDelimiter(", |\\r\\n|\\n");
            while (scanner.hasNext()) {
                int projNumber = Integer.parseInt(scanner.next());
                String title = scanner.next();
                String sponsor = scanner.next();
                int deptNumber = Integer.parseInt(scanner.next());
                double budget = Double.parseDouble(scanner.next());
                projects.add(new Project(projNumber, title, sponsor, deptNumber, budget));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error loading projects from " + fileName + ": " + e.getMessage());
        }
        return projects;
    }

    //Loads works-on relationships from a file into an ArrayList of WorksOn objects.
    public static ArrayList<WorksOn> loadWorksOn(String fileName) {
        ArrayList<WorksOn> worksOnList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(RESOURCE_PATH + fileName))) {
            scanner.useDelimiter(", |\\r\\n|\\n");
            while (scanner.hasNext()) {
                int empNumber = Integer.parseInt(scanner.next());
                int projNumber = Integer.parseInt(scanner.next());
                double hours = Double.parseDouble(scanner.next());
                worksOnList.add(new WorksOn(empNumber, projNumber, hours));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error loading works-on records from " + fileName + ": " + e.getMessage());
        }
        return worksOnList;
    }
}