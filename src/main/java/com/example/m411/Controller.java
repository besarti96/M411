package com.example.m411;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    private final GUIPersonenAnzeigen guiPersonenAnzeigen = new GUIPersonenAnzeigen();

    @FXML
    public Button edit;

    @FXML
    public TextField editName;

    @FXML
    public TextField editVorname;

    @FXML
    public TextField editGeburtstag;

    @FXML
    public TextField editAHV;

    @FXML
    public TextField editRegion;

    @FXML
    public Button saveButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label vornameLabel;

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;

    @FXML
    public void initialize() {
        nextButton.setOnAction(e -> guiPersonenAnzeigen.showNextPerson(nameLabel, vornameLabel /*, weitere Labels */));
        prevButton.setOnAction(e -> guiPersonenAnzeigen.showPreviousPerson(nameLabel, vornameLabel /*, weitere Labels */));
    }

    @FXML
    private void handleEditButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) edit.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit_person_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }

}
