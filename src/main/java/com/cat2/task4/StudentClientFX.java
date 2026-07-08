package com.cat2.task4;

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
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentClientFX extends Application {

    private StudentService stub;
    private final ObservableList<String> studentRows = FXCollections.observableArrayList();
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        ListView<String> listView = new ListView<>(studentRows);
        listView.setPrefHeight(300);

        statusLabel = new Label("Connecting to server...");

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> loadStudents());

        VBox root = new VBox(10,
            new Label("Student Records from Server Database"),
            listView,
            statusLabel,
            refreshButton
        );
        root.setPadding(new Insets(15));

        primaryStage.setTitle("Task 4 - RMI Student Database");
        primaryStage.setScene(new Scene(root, 550, 420));
        primaryStage.show();

        connectAndLoad();
    }

    private void connectAndLoad() {
        String registryHost = System.getProperty("registry.host", "localhost");
        new Thread(() -> {
            try {
                Registry registry = LocateRegistry.getRegistry(registryHost, StudentServer.REGISTRY_PORT);
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

                Platform.runLater(() -> {
                    studentRows.clear();
                    for (Student s : students) {
                        studentRows.add(String.format("%d | %s | %s | %d | %s",
                                s.getId(), s.getName(), s.getCourse(), s.getScore(), s.getEmail()));
                    }
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
