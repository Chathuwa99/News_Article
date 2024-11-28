package org.example.test1.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.test1.Models.User;
import org.example.test1.Utils.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminUserListController {

    @FXML
    private TableView<User> userlistTable;

    @FXML
    private TableColumn<User, Integer> ID;

    @FXML
    private TableColumn<User, String> FullName;

    @FXML
    private TableColumn<User, String> UserName;

    @FXML
    private TableColumn<User, String> Email;

    @FXML
    private Button BackButton;

    @FXML
    public void initialize() {
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        FullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        UserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));

        try {
            loadUserData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadUserData() throws SQLException {
        List<User> users = DatabaseConnection.getAllUsers();
        ObservableList<User> userObservableList = FXCollections.observableArrayList(users);
        userlistTable.setItems(userObservableList);
    }

    public void navigateBackToAdminPage() {
        try {
            AnchorPane adminRoot = FXMLLoader.load(getClass().getResource("/org/example/test1/fxml files/AdminView.fxml"));
            Scene adminScene = new Scene(adminRoot);

            if (BackButton != null) {
                Stage stage = (Stage) BackButton.getScene().getWindow();
                stage.setScene(adminScene);
                stage.show();
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to load the admin page.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}