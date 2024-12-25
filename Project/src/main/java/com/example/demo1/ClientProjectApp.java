package com.example.demo1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;


public class ClientProjectApp extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/database";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123654";

    private Connection connection;

    @Override
    public void start(Stage primaryStage) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            showError("Driver Error", "MySQL JDBC Driver not found: " + e.getMessage());
            return;
        } catch (SQLException e) {
            showError("Database Connection Error", e.getMessage());
            return;
        }

        Label projectLabel = new Label("Select Project:");
        ComboBox<String> projectComboBox = new ComboBox<>();
        Button fetchButton = new Button("Fetch Clients");
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        Label addLabel = new Label("Add New:");
        TextField newClientField = new TextField();
        newClientField.setPromptText("Client Name");
        TextField newProjectField = new TextField();
        newProjectField.setPromptText("Project Name");
        ComboBox<String> clientComboBox = new ComboBox<>();
        Button addClientButton = new Button("Add Client");
        Button addProjectButton = new Button("Add Project");
        Button linkClientProjectButton = new Button("Link Client to Project");

        loadProjects(projectComboBox);
        loadClients(clientComboBox);

        fetchButton.setOnAction(event -> {
            String selectedProject = projectComboBox.getValue();
            if (selectedProject == null || selectedProject.isEmpty()) {
                showError("Input Error", "Please select a project.");
                return;
            }

            try {
                int projectId = Integer.parseInt(selectedProject.split(" - ")[0]);
                String result = fetchClientsForProject(projectId);
                resultArea.setText(result);
            } catch (NumberFormatException e) {
                showError("Input Error", "Invalid project selection.");
            }
        });

        addClientButton.setOnAction(event -> {
            String clientName = newClientField.getText();
            if (clientName.isEmpty()) {
                showError("Input Error", "Client name cannot be empty.");
                return;
            }

            addClient(clientName);
            newClientField.clear();
            loadClients(clientComboBox);
        });

        addProjectButton.setOnAction(event -> {
            String projectName = newProjectField.getText();
            if (projectName.isEmpty()) {
                showError("Input Error", "Project name cannot be empty.");
                return;
            }

            addProject(projectName);
            newProjectField.clear();
            loadProjects(projectComboBox);
        });

        linkClientProjectButton.setOnAction(event -> {
            String selectedClient = clientComboBox.getValue();
            String selectedProject = projectComboBox.getValue();

            if (selectedClient == null || selectedProject == null || selectedClient.isEmpty() || selectedProject.isEmpty()) {
                showError("Input Error", "Please select both a client and a project.");
                return;
            }

            try {
                int clientId = Integer.parseInt(selectedClient.split(" - ")[0]);
                int projectId = Integer.parseInt(selectedProject.split(" - ")[0]);
                linkClientToProject(clientId, projectId);
            } catch (NumberFormatException e) {
                showError("Input Error", "Invalid client or project selection.");
            }
        });

        VBox layout = new VBox(10, projectLabel, projectComboBox, fetchButton, resultArea, addLabel, newClientField, addClientButton, newProjectField, addProjectButton, clientComboBox, linkClientProjectButton);
        Scene scene = new Scene(layout, 400, 600);

        primaryStage.setTitle("Client-Project Manager");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> closeConnection());
    }

    private void loadProjects(ComboBox<String> projectComboBox) {
        projectComboBox.getItems().clear();
        String query = "SELECT id, name FROM projects";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                projectComboBox.getItems().add(id + " - " + name);
            }
        } catch (SQLException e) {
            showError("Database Error", "Unable to load projects: " + e.getMessage());
        }
    }

    private void loadClients(ComboBox<String> clientComboBox) {
        clientComboBox.getItems().clear();
        String query = "SELECT id, name FROM clients";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                clientComboBox.getItems().add(id + " - " + name);
            }
        } catch (SQLException e) {
            showError("Database Error", "Unable to load clients: " + e.getMessage());
        }
    }

    private String fetchClientsForProject(int projectId) {
        String query = "SELECT clients.id, clients.name FROM clients " +
                "JOIN client_project ON clients.id = client_project.client_id " +
                "WHERE client_project.project_id = ?";

        StringBuilder result = new StringBuilder("Clients associated with project ID " + projectId + ":\n");

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, projectId);

            ResultSet resultSet = preparedStatement.executeQuery();

            boolean hasResults = false;
            while (resultSet.next()) {
                hasResults = true;
                int clientId = resultSet.getInt("id");
                String clientName = resultSet.getString("name");
                result.append("- ID: ").append(clientId).append(", Name: ").append(clientName).append("\n");
            }

            if (!hasResults) {
                result.append("No clients are associated with this project.");
            }
        } catch (SQLException e) {
            result = new StringBuilder("Error fetching data: " + e.getMessage());
        }

        return result.toString();
    }

    private void addClient(String clientName) {
        String query = "INSERT INTO clients (name) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, clientName);
            preparedStatement.executeUpdate();
            showInfo("Success", "Client added successfully.");
        } catch (SQLException e) {
            showError("Database Error", "Unable to add client: " + e.getMessage());
        }
    }

    private void addProject(String projectName) {
        String query = "INSERT INTO projects (name) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, projectName);
            preparedStatement.executeUpdate();
            showInfo("Success", "Project added successfully.");
        } catch (SQLException e) {
            showError("Database Error", "Unable to add project: " + e.getMessage());
        }
    }

    private void linkClientToProject(int clientId, int projectId) {
        String query = "INSERT INTO client_project (client_id, project_id) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, clientId);
            preparedStatement.setInt(2, projectId);
            preparedStatement.executeUpdate();
            showInfo("Success", "Client linked to project successfully.");
        } catch (SQLException e) {
            showError("Database Error", "Unable to link client to project: " + e.getMessage());
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
