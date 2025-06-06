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

        GridPane deptPane = createGridPane("Department", deptList, deptFields,
                "Department Number", "Name", "Manager", "Budget", "Start date");
        GridPane empPane = createGridPane("Employee", empList, empFields,
                "Employee Number", "Name", "DOB", "Address", "Gender", "Salary", "Supervisor", "Department", "Skill/Language");
        GridPane projPane = createGridPane("Project", projList, projFields,
                "Project Number", "Title", "Sponsor", "Department", "Budget");
        GridPane workPane = createGridPane("Works on", worksList, worksFields,
                "Works on", "Employee number", "Project number", "Hours");

        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button saveButton = new Button("Save");

        HBox buttonRow = new HBox(10, addButton, deleteButton, saveButton);
        buttonRow.setAlignment(Pos.CENTER);
        workPane.add(buttonRow, 1, 6);

        workPane.add(new Label("Message"), 0, 7);
        workPane.add(messageField, 1, 7);
        messageField.setEditable(false);

        HBox infoRow = new HBox(20, deptPane, empPane, projPane, workPane);
        infoRow.setPadding(new Insets(10));
        infoRow.setAlignment(Pos.TOP_LEFT);
        infoRow.setPrefWidth(Double.MAX_VALUE);

        HBox.setHgrow(deptPane, Priority.ALWAYS);
        HBox.setHgrow(empPane, Priority.ALWAYS);
        HBox.setHgrow(projPane, Priority.ALWAYS);
        HBox.setHgrow(workPane, Priority.ALWAYS);

        deptPane.setMaxWidth(Double.MAX_VALUE);
        empPane.setMaxWidth(Double.MAX_VALUE);
        projPane.setMaxWidth(Double.MAX_VALUE);
        workPane.setMaxWidth(Double.MAX_VALUE);

        PieChart chart = new PieChart();
        for (Department d : departments) {
            String label = d.getDeptNumber() + " " + d.getDeptName();
            chart.getData().add(new PieChart.Data(label, d.getBudget()));
        }
        chart.setLegendSide(javafx.geometry.Side.BOTTOM);
        chart.setLabelsVisible(true);

        VBox root = new VBox(20, infoRow, chart);
        root.setPadding(new Insets(10));

        setupListViews();
        setupEventHandlers(addButton, deleteButton, saveButton);
        setupSelectionHandlers();

        Scene scene = new Scene(root, 1350, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Departments, employees and projects management system");
        primaryStage.show();
    }

    private GridPane createGridPane(String title, ListView<?> listView, Map<String, TextField> fieldMap, String... labels) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);
        grid.setPadding(new Insets(5));
        grid.setStyle("-fx-background-color: white;");
grid.setAlignment(Pos.TOP_CENTER);

        Label header = new Label(title + " information");
        
        header.setMaxWidth(Double.MAX_VALUE);
        header.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(header, 2);
        grid.add(header, 0, 0);

        int row = 1;
        for (String label : labels) {
            Label lbl = new Label(label);
            lbl.setMinWidth(120);
            lbl.setAlignment(Pos.BASELINE_LEFT);
            grid.add(lbl, 0, row);

            if ((title.equals("Department") && label.equals("Department Number")) ||
                (title.equals("Employee") && label.equals("Employee Number")) ||
                (title.equals("Project") && label.equals("Project Number")) ||
                (title.equals("Works on") && label.equals("Works on"))) {
                ScrollPane scroll = new ScrollPane(listView);
                scroll.setFitToHeight(true);
                scroll.setFitToWidth(true);
                scroll.setPrefViewportHeight(100);
                scroll.setPrefViewportWidth(180);
                grid.add(scroll, 1, row);
            } else {
                TextField tf = new TextField();
                tf.setEditable(false);
                fieldMap.put(label, tf);
                grid.add(tf, 1, row);
            }
            row++;
        }
        return grid;
    }

    private void setupListViews() {
        for (Department d : departments) deptList.getItems().add(d.getDeptNumber());
        for (Employee e : employees) empList.getItems().add(e.getEmpNumber());
        for (Project p : projects) projList.getItems().add(p.getProjNumber());
        for (WorksOn w : worksOnList) worksList.getItems().add(w.toString());
    }

    private void setupEventHandlers(Button addButton, Button deleteButton, Button saveButton) {
        addButton.setOnAction(e -> handleAdd());
        deleteButton.setOnAction(e -> handleDelete());
        saveButton.setOnAction(e -> handleSave());
    }

    private void setupSelectionHandlers() {
        deptList.setOnMouseClicked(e -> {
            Integer selected = deptList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                for (Department d : departments) {
                    if (d.getDeptNumber() == selected) {
                        deptFields.get("Department Number").setText(String.valueOf(d.getDeptNumber()));
                        deptFields.get("Name").setText(d.getDeptName());
                        deptFields.get("Manager").setText(String.valueOf(d.getManagerNumber()));
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
                        empFields.get("Employee Number").setText(String.valueOf(emp.getEmpNumber()));
                        empFields.get("Name").setText(emp.getName());
                        empFields.get("DOB").setText(emp.getDob());
                        empFields.get("Address").setText(emp.getAddress());
                        empFields.get("Gender").setText(emp.getGender());
                        empFields.get("Salary").setText(String.valueOf(emp.getSalary()));
                        empFields.get("Supervisor").setText(String.valueOf(emp.getSupervisorNumber()));
                        empFields.get("Department").setText(String.valueOf(emp.getDeptNumber()));
                        if (emp instanceof Admin) {
                            empFields.get("Skill/Language").setText(((Admin) emp).getSkills());
                        } else if (emp instanceof Developer) {
                            empFields.get("Skill/Language").setText(((Developer) emp).getProgrammingLanguages());
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
                        projFields.get("Project Number").setText(String.valueOf(p.getProjNumber()));
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

    private void loadData() {
        departments = DataIO.loadDepartments("departments.txt");
        employees = DataIO.loadEmployees("employees.txt");
        projects = DataIO.loadProjects("projects.txt");
        worksOnList = DataIO.loadWorksOn("workson.txt");
    }

    private void handleAdd() {
        Integer selectedEmp = empList.getSelectionModel().getSelectedItem();
        Integer selectedProj = projList.getSelectionModel().getSelectedItem();

        if (selectedEmp == null || selectedProj == null) {
            messageField.setText("Please select an employee and a project.");
            return;
        }

        Employee emp = employees.stream()
                .filter(e -> e.getEmpNumber() == selectedEmp)
                .findFirst()
                .orElse(null);

        if (!(emp instanceof Developer)) {
            messageField.setText("Please select a developer.");
            return;
        }

        for (WorksOn w : worksOnList) {
            if (w.getEmpNumber() == selectedEmp && w.getProjNumber() == selectedProj) {
                messageField.setText("Employee " + selectedEmp + " has already worked on Project " + selectedProj);
                return;
            }
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input Hours");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter hours:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(hoursStr -> {
            try {
                double hours = Double.parseDouble(hoursStr);
                WorksOn newRecord = new WorksOn(selectedEmp, selectedProj, hours);
                worksOnList.add(newRecord);
                worksList.getItems().add(newRecord.toString());
                messageField.setText("Work assignment added.");
            } catch (NumberFormatException ex) {
                messageField.setText("Invalid number format.");
            }
        });
    }

    private void handleDelete() {
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
    }

    private void handleSave() {
        try (java.io.PrintWriter writer = new java.io.PrintWriter("src/main/resources/workson.txt")) {
            for (WorksOn w : worksOnList) {
                writer.println(w.getEmpNumber() + ", " + w.getProjNumber() + ", " + w.getHours());
            }
            messageField.setText(worksOnList.size() + " works-on records saved to file.");
        } catch (Exception e) {
            messageField.setText("Error saving to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
