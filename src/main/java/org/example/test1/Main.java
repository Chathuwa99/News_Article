package org.example.test1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.test1.Controller.UserController;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Fix: Use the correct path to the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test1/fxml files/UserRegistration.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("User Registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
