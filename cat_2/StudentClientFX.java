/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat_2;

/**
 *
 * @author harshvirsinghahuja
 */
// Name: [Your Name] | Student Number: [Your Number] | Date: July 2026

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentClientFX extends Application {

    private StudentService stub;
    private TableView<Student> tableView;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {

        tableView = new TableView<>();

        TableColumn<Student, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Student, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setPrefWidth(120);

        TableColumn<Student, String> colCourse = new TableColumn<>("Course");
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        colCourse.setPrefWidth(80);

        TableColumn<Student, Integer> colScore = new TableColumn<>("Score");
        colScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        colScore.setPrefWidth(70);

        TableColumn<Student, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(180);

        tableView.getColumns().addAll(colId, colName, colCourse, colScore, colEmail);
        tableView.setPrefHeight(300);

        statusLabel = new Label("Connecting to server...");

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> loadStudents());

        VBox root = new VBox(10,
            new Label("Student Records from Server Database"),
            tableView,
            statusLabel,
            refreshButton
        );
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 550, 420);
        primaryStage.setTitle("Q4 - RMI Student Database");
        primaryStage.setScene(scene);
        primaryStage.show();

        connectAndLoad();
    }

    private void connectAndLoad() {
        new Thread(() -> {
            try {
                Registry registry = LocateRegistry.getRegistry("localhost", 1099);
                stub = (StudentService) registry.lookup("StudentService");

                Platform.runLater(() -> statusLabel.setText("Connected! Loading students..."));
                loadStudents();

            } catch (Exception e) {
                Platform.runLater(() -> statusLabel.setText("Connection failed: " + e.getMessage()));
            }
        }).start();
    }

    private void loadStudents() {
        new Thread(() -> {
            try {
                List<Student> students = stub.getStudents();

                ObservableList<Student> data = FXCollections.observableArrayList(students);

                Platform.runLater(() -> {
                    tableView.setItems(data);
                    statusLabel.setText("Loaded " + students.size() + " students.");
                });

            } catch (Exception e) {
                Platform.runLater(() -> statusLabel.setText("Error: " + e.getMessage()));
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}