package com.example.m411;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.util.Objects;

public class MainApplication extends Application {

    // Start des Programms
    @Override
    public void start(Stage stage) throws Exception {

        Connection databaseConnection = DatabaseConnection.getDatabase();
        if (databaseConnection != null) {
            System.out.println("Datenbankverbindung erfolgreich hergestellt.");
        } else {
            System.out.println("Fehler bei der Datenbankverbindung.");
            return; // Beenden der Anwendung, da keine Datenbankverbindung hergestellt werden konnte
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("persons_view.fxml"))); // Erste View aufrufen
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}