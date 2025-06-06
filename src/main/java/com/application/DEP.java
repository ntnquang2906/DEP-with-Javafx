/*-----------------------------------
Student name: Tien Nhat Quang Nguyen
Student number: 7722242
Subject code: CSIT213
-----------------------------------*/

package com.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import java.util.*;
import com.model.*;

public class DEP extends Application {
    private ArrayList<Department> departments;
    private ArrayList<Employee> employees;
    private ArrayList<Project> projects;
    private ArrayList<WorksOn> worksOnList;

    private ListView<Integer> deptList = new ListView<>();
    private ListView<Integer> empList = new ListView<>();
    private ListView<Integer> projList = new ListView<>();
    private ListView<String> worksList = new ListView<>();

    private TextField messageField = new TextField();

    private Map<String, TextField> deptFields = new HashMap<>();
    private Map<String, TextField> empFields = new HashMap<>();
    private Map<String, TextField> projFields = new HashMap<>();
    private Map<String, TextField> worksFields = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        loadData();

        GridPane deptPane = createGridPane("Department information", deptList, deptFields, "Department Number",
                "Name", "Manager Number", "Budget", "Start date");
        GridPane empPane = createGridPane("Employee information", empList, empFields, "Employee Number",
                "Name", "DOB", "Address", "Gender", "Salary", "Supervisor Number", "Department", "Skill/Language");
        GridPane projPane = createGridPane("Project information", projList, projFields, "Project Number",
                "Title", "Sponsor", "Department", "Budget");
        GridPane workPane = createGridPane("Works on information", worksList, worksFields, "Works on",
                "Employee number", "Project number", "Hours");

        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button saveButton = new Button("Save");

        HBox buttonRow = new HBox(10, addButton, deleteButton, saveButton);
        buttonRow.setAlignment(Pos.CENTER);

        VBox buttonBox = new VBox(buttonRow);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        workPane.add(buttonBox, 1, worksFields.size() + 2);

        workPane.add(new Label("Message"), 0, worksFields.size() + 3);
        workPane.add(messageField, 1, worksFields.size() + 3);
        messageField.setEditable(false);

        HBox infoRow = new HBox(20, deptPane, empPane, projPane, workPane);
        infoRow.setPadding(new Insets(10));
        infoRow.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(deptPane, Priority.ALWAYS);
        HBox.setHgrow(empPane, Priority.ALWAYS);
        HBox.setHgrow(projPane, Priority.ALWAYS);
        HBox.setHgrow(workPane, Priority.ALWAYS);

        PieChart chart = new PieChart();
        for (Department d : departments) {
            chart.getData().add(new PieChart.Data(d.getDeptNumber() + " " + d.getDeptName(), d.getBudget()));
        }
        chart.setLegendSide(javafx.geometry.Side.BOTTOM);
        chart.setLabelsVisible(true);

        VBox root = new VBox(20, infoRow, chart);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #f0f0f0;");

        setupListViews();
        setupEventHandlers(addButton, deleteButton, saveButton);
        setupSelectionHandlers();

        Scene scene = new Scene(root, 1350, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Departments, employees and projects management system");
        primaryStage.show();
    }

    private void loadData() {
        departments = DataIO.loadDepartments("departments.txt");
        employees = DataIO.loadEmployees("employees.txt");
        projects = DataIO.loadProjects("projects.txt");
        worksOnList = DataIO.loadWorksOn("workson.txt");
        System.out.println("? Departments loaded: " + departments.size());
        System.out.println("? Employees loaded: " + employees.size());
        System.out.println("? Projects loaded: " + projects.size());
        System.out.println("? WorksOn loaded: " + worksOnList.size());
    }

    private GridPane createGridPane(String headerText, ListView<?> listView, Map<String, TextField> fieldMap, String firstLabel, String... labels) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(5);
        gridPane.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-radius: 5;");

        Label header = new Label(headerText);
        header.setStyle("-fx-font-size: 13;");
        GridPane.setColumnSpan(header, 2);
        GridPane.setHalignment(header, javafx.geometry.HPos.CENTER);
        gridPane.add(header, 0, 0);

        Label firstFieldLabel = new Label(firstLabel);
        gridPane.add(firstFieldLabel, 0, 1);
        listView.setPrefHeight(100);
        gridPane.add(listView, 1, 1);

        for (int i = 0; i < labels.length; i++) {
            String label = labels[i];
            gridPane.add(new Label(label), 0, i + 2);
            TextField field = new TextField();
            field.setEditable(false);
            fieldMap.put(label, field);
            gridPane.add(field, 1, i + 2);
        }

        return gridPane;
    }

    private void setupListViews() {
        for (Department d : departments) deptList.getItems().add(d.getDeptNumber());
        for (Employee e : employees) empList.getItems().add(e.getEmpNumber());
        for (Project p : projects) projList.getItems().add(p.getProjNumber());
        for (WorksOn w : worksOnList) worksList.getItems().add(w.toString());
    }

    private void setupEventHandlers(Button add, Button delete, Button save) {
        add.setOnAction(e -> messageField.setText("Add button clicked"));
        delete.setOnAction(e -> messageField.setText("Delete button clicked"));
        save.setOnAction(e -> messageField.setText("Save button clicked"));
    }

    private void setupSelectionHandlers() {
        deptList.setOnMouseClicked(e -> {
            Integer selected = deptList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                for (Department d : departments) {
                    if (d.getDeptNumber() == selected) {
                        deptFields.get("Name").setText(d.getDeptName());
                        deptFields.get("Manager Number").setText(String.valueOf(d.getManagerNumber()));
                        deptFields.get("Budget").setText(String.valueOf(d.getBudget()));
                        deptFields.get("Start date").setText(d.getStartDate());
                        break;
                    }
                }
            }
        });

        empList.setOnMouseClicked(e -> {
            Integer selected = empList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                for (Employee emp : employees) {
                    if (emp.getEmpNumber() == selected) {
                        empFields.get("Name").setText(emp.getName());
                        empFields.get("DOB").setText(emp.getDob());
                        empFields.get("Address").setText(emp.getAddress());
                        empFields.get("Gender").setText(emp.getGender());
                        empFields.get("Salary").setText(String.valueOf(emp.getSalary()));
                        empFields.get("Supervisor Number").setText(String.valueOf(emp.getSupervisorNumber()));
                        empFields.get("Department").setText(String.valueOf(emp.getDeptNumber()));
                        if (emp instanceof Developer) {
                            empFields.get("Skill/Language").setText(((Developer) emp).getProgrammingLanguages());
                        } else if (emp instanceof Admin) {
                            empFields.get("Skill/Language").setText(((Admin) emp).getSkills());
                        }
                        break;
                    }
                }
            }
        });

        projList.setOnMouseClicked(e -> {
            Integer selected = projList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                for (Project p : projects) {
                    if (p.getProjNumber() == selected) {
                        projFields.get("Title").setText(p.getTitle());
                        projFields.get("Sponsor").setText(p.getSponsor());
                        projFields.get("Department").setText(String.valueOf(p.getDeptNumber()));
                        projFields.get("Budget").setText(String.valueOf(p.getBudget()));
                        break;
                    }
                }
            }
        });

        worksList.setOnMouseClicked(e -> {
            int selectedIndex = worksList.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                WorksOn selected = worksOnList.get(selectedIndex);
                worksFields.get("Employee number").setText(String.valueOf(selected.getEmpNumber()));
                worksFields.get("Project number").setText(String.valueOf(selected.getProjNumber()));
                worksFields.get("Hours").setText(String.valueOf(selected.getHours()));
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}