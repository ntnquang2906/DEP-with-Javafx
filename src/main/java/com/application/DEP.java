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

import java.io.PrintWriter;
import java.util.*;
import com.model.*;

// Main JavaFX application class for managing departments, employees, projects, and work assignments.
public class DEP extends Application {

    // Data lists loaded from files
    private ArrayList<Department> departments;
    private ArrayList<Employee> employees;
    private ArrayList<Project> projects;
    private ArrayList<WorksOn> worksOnList;

    // ListViews to display selectable records
    private ListView<Integer> deptList = new ListView<>();
    private ListView<Integer> empList = new ListView<>();
    private ListView<Integer> projList = new ListView<>();
    private ListView<String> worksList = new ListView<>();

    // Text field for showing system messages
    private TextField messageField = new TextField();

    // Field maps for each section
    private Map<String, TextField> deptFields = new HashMap<>();
    private Map<String, TextField> empFields = new HashMap<>();
    private Map<String, TextField> projFields = new HashMap<>();
    private Map<String, TextField> worksFields = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        // Load data from text files
        loadData();

        // Create all information panes
        GridPane deptPane = createGridPane("Department information", deptList, deptFields,
                "Department Number", "Name", "Manager Number", "Budget", "Start date");

        GridPane empPane = createGridPane("Employee information", empList, empFields,
                "Employee Number", "Name", "DOB", "Address", "Gender", "Salary",
                "Supervisor Number", "Department", "Skill/Language");

        GridPane projPane = createGridPane("Project information", projList, projFields,
                "Project Number", "Title", "Sponsor", "Department", "Budget");

        GridPane workPane = createGridPane("Works on information", worksList, worksFields,
                "Works on", "Employee number", "Project number", "Hours");

        // Buttons for add, delete, save
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button saveButton = new Button("Save");

        HBox buttonRow = new HBox(10, addButton, deleteButton, saveButton);
        buttonRow.setAlignment(Pos.CENTER);
        workPane.add(buttonRow, 1, worksFields.size() + 2);

        // Message field for status updates
        workPane.add(new Label("Message"), 0, worksFields.size() + 3);
        messageField.setPrefWidth(300);
        messageField.setEditable(false);
        workPane.add(messageField, 1, worksFields.size() + 3);

        // Layout for all panes
        HBox infoRow = new HBox(20, deptPane, empPane, projPane, workPane);
        infoRow.setPadding(new Insets(10));
        infoRow.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(deptPane, Priority.ALWAYS);
        HBox.setHgrow(empPane, Priority.ALWAYS);
        HBox.setHgrow(projPane, Priority.ALWAYS);
        HBox.setHgrow(workPane, Priority.ALWAYS);

        // Create pie chart showing budget by department
        PieChart chart = new PieChart();
        for (Department d : departments) {
            chart.getData().add(new PieChart.Data(d.getDeptNumber() + " " + d.getDeptName(), d.getBudget()));
        }
        chart.setLegendSide(javafx.geometry.Side.BOTTOM);
        chart.setLabelsVisible(true);

        // Combine everything into the main layout
        VBox root = new VBox(20, infoRow, chart);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #f0f0f0;");

        // Setup event logic
        setupListViews();
        setupEventHandlers(addButton, deleteButton, saveButton);
        setupSelectionHandlers();

        // Set scene and show window
        Scene scene = new Scene(root, 1350, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Departments, employees and projects management system");
        primaryStage.show();
    }

    // Load all data from text files using DataIO
    private void loadData() {
        departments = DataIO.loadDepartments("departments.txt");
        employees = DataIO.loadEmployees("employees.txt");
        projects = DataIO.loadProjects("projects.txt");
        worksOnList = DataIO.loadWorksOn("workson.txt");
    }

    // Creates a grid pane for displaying a list and corresponding text fields
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

    // Fill ListViews with initial data
    private void setupListViews() {
        for (Department d : departments) deptList.getItems().add(d.getDeptNumber());
        for (Employee e : employees) empList.getItems().add(e.getEmpNumber());
        for (Project p : projects) projList.getItems().add(p.getProjNumber());
        for (WorksOn w : worksOnList) worksList.getItems().add(w.toString());
    }

    // Event handling for Add, Delete, Save buttons
    private void setupEventHandlers(Button add, Button delete, Button save) {
        // create a new works-on record
        add.setOnAction(e -> {
            Integer selectedEmp = empList.getSelectionModel().getSelectedItem();
            Integer selectedProj = projList.getSelectionModel().getSelectedItem();

            if (selectedEmp == null || selectedProj == null) {
                messageField.setText("Please select an employee and a project.");
                return;
            }

            // Only Developers can be assigned to projects
            Employee selectedEmployee = null;
            for (Employee emp : employees) {
                if (emp.getEmpNumber() == selectedEmp) {
                    selectedEmployee = emp;
                    break;
                }
            }

            if (!(selectedEmployee instanceof Developer)) {
                messageField.setText("Please select a developer");
                return;
            }

            // Prevent duplicate assignments
            for (WorksOn w : worksOnList) {
                if (w.getEmpNumber() == selectedEmp && w.getProjNumber() == selectedProj) {
                    messageField.setText("Employee " + selectedEmp + " has already worked on Project " + selectedProj);
                    return;
                }
            }

            // Prompt user for working hours
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Input Working Hours");
            dialog.setHeaderText("Enter hours worked on Project " + selectedProj);
            dialog.setContentText("Hours:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    double hours = Double.parseDouble(result.get());
                    WorksOn newWork = new WorksOn(selectedEmp, selectedProj, hours);
                    worksOnList.add(newWork);
                    worksList.getItems().add(newWork.toString());
                    messageField.setText("Added works-on record for Employee " + selectedEmp + " on Project " + selectedProj);
                } catch (NumberFormatException ex) {
                    messageField.setText("Invalid number format for hours.");
                }
            }
        });

        // remove selected works-on record
        delete.setOnAction(e -> {
            int selectedIndex = worksList.getSelectionModel().getSelectedIndex();
            if (selectedIndex < 0) {
                messageField.setText("Please select a works-on record to delete.");
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the selected works-on record?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                worksOnList.remove(selectedIndex);
                worksList.getItems().remove(selectedIndex);
                messageField.setText("The selected works-on record has been deleted.");
            }
        });

        // save works-on records to file
        save.setOnAction(e -> {
            try (PrintWriter writer = new PrintWriter("workson.txt")) {
                for (WorksOn w : worksOnList) {
                    writer.println(w.getEmpNumber() + ", " + w.getProjNumber() + ", " + w.getHours());
                }
                messageField.setText(worksOnList.size() + " works-on records saved to file.");
            } catch (Exception ex) {
                messageField.setText("Error saving to file: " + ex.getMessage());
            }
        });
    }

    // Handles what happens when list items are clicked
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

    // Launch application
    public static void main(String[] args) {
        launch(args);
    }
}