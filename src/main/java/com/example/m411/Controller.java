package com.example.m411;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Objects;

public class Controller {

    public Label nameLabel;
    public Label vornameLabel;
    public Button back;
    public Button forward;
    public Button edit;

    @FXML
    public void handleEditButtonAction() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("edit_person_view.fxml")));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}