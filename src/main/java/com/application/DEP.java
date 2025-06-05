import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Optional;

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

    @Override
    public void start(Stage primaryStage) {
        loadData();

        GridPane deptPane = createGridPane("Department", deptList,
                "Department Number", "Name", "Manager", "Budget", "Start date");
        GridPane empPane = createGridPane("Employee", empList,
                "Employee Number", "Name", "DOB", "Address", "Gender", "Salary", "Supervisor", "Department", "Skill/Language");
        GridPane projPane = createGridPane("Project", projList,
                "Project Number", "Title", "Sponsor", "Department", "Budget");
        GridPane workPane = createGridPane("Works on", worksList,
                "Works on", "Employee number", "Project number", "Hours");

        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button saveButton = new Button("Save");

        HBox buttonRow = new HBox(10, addButton, deleteButton, saveButton);
        buttonRow.setAlignment(Pos.CENTER);
        StackPane buttonCell = new StackPane(buttonRow);
        buttonCell.setAlignment(Pos.CENTER);
        workPane.add(buttonCell, 1, 6);

        workPane.add(new Label("Message"), 0, 7);
        workPane.add(messageField, 1, 7);
        messageField.setEditable(false);

        HBox infoRow = new HBox(20, deptPane, empPane, projPane, workPane);
        infoRow.setPadding(new Insets(10));
        infoRow.setAlignment(Pos.TOP_LEFT);
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

        Scene scene = new Scene(root, 1350, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Departments, employees and projects management system");
        primaryStage.show();
    }

    private GridPane createGridPane(String title, ListView<?> listView, String... labels) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);
        grid.setPadding(new Insets(5));
        grid.setStyle("-fx-background-color: white;");
        grid.setMaxWidth(Double.MAX_VALUE);

        Label header = new Label(title + " information");
        header.setMaxWidth(Double.MAX_VALUE);
        header.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(header, 2);
        grid.add(header, 0, 0, 2, 1);

        int row = 1;
        for (String label : labels) {
            Label lbl = new Label(label);
            lbl.setMinWidth(120);
            grid.add(lbl, 0, row);

            boolean isListViewRow =
                    (title.equals("Department") && label.equals("Department Number")) ||
                    (title.equals("Employee") && label.equals("Employee Number")) ||
                    (title.equals("Project") && label.equals("Project Number")) ||
                    (title.equals("Works on") && label.equals("Works on"));

            if (isListViewRow) {
                ScrollPane scroll = new ScrollPane(listView);
                scroll.setFitToHeight(true);
                scroll.setFitToWidth(true);
                scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                scroll.setPrefViewportHeight(100);
                scroll.setPrefViewportWidth(180);
                grid.add(scroll, 1, row);
            } else {
                grid.add(new TextField(), 1, row);
            }

            row++;
        }
        return grid;
    }

    private void loadData() {
        departments = DataIO.loadDepartments("departments.txt");
        employees = DataIO.loadEmployees("employees.txt");
        projects = DataIO.loadProjects("projects.txt");
        worksOnList = DataIO.loadWorksOn("workson.txt");
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

    private void handleAdd() {
        try {
            Integer selectedEmp = empList.getSelectionModel().getSelectedItem();
            Integer selectedProj = projList.getSelectionModel().getSelectedItem();
            String hourText = getTextFieldFromGrid("Hours");

            if (selectedEmp == null || selectedProj == null || hourText.isEmpty()) {
                messageField.setText("Missing input: employee, project, or hours.");
                return;
            }

            double hours = Double.parseDouble(hourText);

            for (WorksOn w : worksOnList) {
                if (w.getEmpNumber() == selectedEmp && w.getProjNumber() == selectedProj) {
                    messageField.setText("This employee already works on this project.");
                    return;
                }
            }

            WorksOn newWork = new WorksOn(selectedEmp, selectedProj, hours);
            worksOnList.add(newWork);
            worksList.getItems().add(newWork.toString());
            messageField.setText("Work assignment added.");
        } catch (Exception ex) {
            messageField.setText("Invalid input.");
        }
    }

    private String getTextFieldFromGrid(String labelText) {
        for (Node pane : ((VBox) ((Scene) messageField.getScene()).getRoot()).getChildren()) {
            if (pane instanceof HBox) {
                for (Node section : ((HBox) pane).getChildren()) {
                    if (section instanceof GridPane) {
                        GridPane grid = (GridPane) section;
                        for (int i = 0; i < grid.getChildren().size(); i++) {
                            Node node = grid.getChildren().get(i);
                            if (node instanceof Label && ((Label) node).getText().equals(labelText)) {
                                int index = GridPane.getRowIndex(node);
                                for (Node n : grid.getChildren()) {
                                    if (GridPane.getRowIndex(n) == index && n instanceof TextField) {
                                        return ((TextField) n).getText();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    private void handleDelete() {
        int selected = worksList.getSelectionModel().getSelectedIndex();
        if (selected >= 0) {
            worksOnList.remove(selected);
            worksList.getItems().remove(selected);
            messageField.setText("Work assignment deleted.");
        } else {
            messageField.setText("Please select a record to delete.");
        }
    }

    private void handleSave() {
        try (java.io.PrintWriter writer = new java.io.PrintWriter("workson.txt")) {
            for (WorksOn w : worksOnList) {
                writer.println(w.toString());
            }
            messageField.setText("Saved to workson.txt.");
        } catch (Exception e) {
            messageField.setText("Error saving file.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
