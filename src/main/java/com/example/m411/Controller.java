package com.example.m411;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private PersonenAnzeigenController personenAnzeigenController;

    @FXML
    private Button edit;

    @FXML
    private Label nameLabel;

    @FXML
    private Label vornameLabel;

    @FXML
    private Label geschlechtLabel;

    @FXML
    private Label geburtstagLabel;

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        personenAnzeigenController = new PersonenAnzeigenController(nameLabel, vornameLabel, geschlechtLabel, geburtstagLabel /*, weitere Labels */);


        nextButton.setOnAction(e -> personenAnzeigenController.showNextPerson(nameLabel, vornameLabel, geschlechtLabel, geburtstagLabel/*, weitere Labels */));
        prevButton.setOnAction(e -> personenAnzeigenController.showPreviousPerson(nameLabel, vornameLabel, geschlechtLabel, geburtstagLabel /*, weitere Labels */));
    }

    @FXML
    private void handleEditButtonAction(ActionEvent event) throws IOException {
        // Holen der aktuellen AHV Nummer
        String ahvNummer = personenAnzeigenController.getCurrentAhvNummer();

        Stage stage = (Stage) edit.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit_person_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        // Zugriff auf den Controller der edit_person_view.fxml
        PersonenBearbeitenController controller = fxmlLoader.getController();

        // Setzen der AHV Nummer im PersonenBearbeitenController
        if (controller != null) {
            controller.setAhvNummer(ahvNummer);
        } else {
            System.err.println("PersonenBearbeitenController ist null, kann nicht AHV-Nummer setzen.");
        }

        stage.setScene(scene);
    }

}
