package com.cat2.task3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EchoClientFX extends Application {

    private EchoService stub;
    private final ObservableList<String> messages = FXCollections.observableArrayList();
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        ListView<String> messageList = new ListView<>(messages);
        messageList.setPrefHeight(300);

        TextField inputField = new TextField();
        inputField.setPromptText("Enter text to send to the server...");
        HBox.setHgrow(inputField, Priority.ALWAYS);

        Button sendButton = new Button("Send");
        sendButton.setDefaultButton(true);
        sendButton.setOnAction(e -> {
            String text = inputField.getText().trim();
            if (!text.isEmpty()) {
                inputField.clear();
                sendMessage(text);
            }
        });

        statusLabel = new Label("Connecting to server...");

        VBox root = new VBox(10,
            new Label("Messages"),
            messageList,
            new HBox(10, inputField, sendButton),
            statusLabel
        );
        root.setPadding(new Insets(15));

        primaryStage.setTitle("Task 3 - RMI Echo Client");
        primaryStage.setScene(new Scene(root, 500, 420));
        primaryStage.show();

        connect();
    }

    private void connect() {
        String registryHost = System.getProperty("registry.host", "localhost");
        new Thread(() -> {
            try {
                Registry registry = LocateRegistry.getRegistry(registryHost, EchoServer.REGISTRY_PORT);
                stub = (EchoService) registry.lookup("EchoService");
                Platform.runLater(() -> statusLabel.setText("Connected to " + registryHost));
            } catch (Exception e) {
                Platform.runLater(() -> statusLabel.setText("Connection failed: " + e.getMessage()));
            }
        }).start();
    }

    private void sendMessage(String text) {
        if (stub == null) {
            statusLabel.setText("Not connected yet.");
            return;
        }
        messages.add("You: " + text);
        new Thread(() -> {
            try {
                String response = stub.echo(text);
                Platform.runLater(() -> messages.add("Server: " + response));
            } catch (Exception e) {
                Platform.runLater(() -> statusLabel.setText("Error: " + e.getMessage()));
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
