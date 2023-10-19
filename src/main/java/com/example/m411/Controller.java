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

    private GUIPersonenAnzeigen guiPersonenAnzeigen;

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

        guiPersonenAnzeigen = new GUIPersonenAnzeigen(nameLabel, vornameLabel, geschlechtLabel, geburtstagLabel /*, weitere Labels */);


        nextButton.setOnAction(e -> guiPersonenAnzeigen.showNextPerson(nameLabel, vornameLabel, geschlechtLabel, geburtstagLabel/*, weitere Labels */));
        prevButton.setOnAction(e -> guiPersonenAnzeigen.showPreviousPerson(nameLabel, vornameLabel, geschlechtLabel, geburtstagLabel /*, weitere Labels */));
    }

    @FXML
    private void handleEditButtonAction(ActionEvent event) throws IOException {
        // Holen der aktuellen AHV Nummer
        String ahvNummer = guiPersonenAnzeigen.getCurrentAhvNummer();

        Stage stage = (Stage) edit.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit_person_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        // Zugriff auf den Controller der edit_person_view.fxml
        GUIPersonenBearbeiten controller = fxmlLoader.getController();

        // Setzen der AHV Nummer im Bearbeitungscontroller
        if (controller != null) {
            controller.setAhvNummer(ahvNummer);
        } else {
            System.err.println("Edit controller is null, cannot set AHV number.");
        }

        stage.setScene(scene);
    }

}
