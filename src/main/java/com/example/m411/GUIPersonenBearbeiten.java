package com.example.m411;

import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIPersonenBearbeiten implements Initializable {
    @FXML
    private TextField nameField;

    @FXML
    private TextField vornameField;

    @FXML
    private TextField geschlechtField;

    @FXML
    private TextField geburtsdatumField;

    @FXML
    private TextField ahvNummerField;

    @FXML
    private TextField idRegionField;

    @FXML
    private TextField kinderanzahlField;

    @FXML
    private Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Verbinden mit der Datenbank
        MongoDatabase database = mongodb.getDatabase();

        // Beispiel: Personendaten abrufen und in die Textfelder einfügen
        String ahvNummer = "756.9999.9999.99"; // Beispiel-AHV-Nummer
        Person person = mongodb.getPerson(database, ahvNummer); // Angenommen, dass eine Methode getPerson existiert

        assert person != null;
        nameField.setText(person.getName());
        vornameField.setText(person.getVorname());
        geschlechtField.setText(person.getGeschlecht());
        geburtsdatumField.setText(person.getGeburtsdatum());
        ahvNummerField.setText(person.getAhvNummer());
        idRegionField.setText(person.getIdRegion());
        kinderanzahlField.setText(String.valueOf(person.getKinderanzahl()));

        // Beispiel: Speichern der bearbeiteten Daten
        saveButton.setOnAction(e -> {
            person.setName(nameField.getText());
            person.setVorname(vornameField.getText());
            person.setGeschlecht(geschlechtField.getText());
            person.setGeburtsdatum(geburtsdatumField.getText());
            person.setAhvNummer(ahvNummerField.getText());
            person.setIdRegion(idRegionField.getText());
            person.setKinderanzahl(Integer.parseInt(kinderanzahlField.getText()));

            mongodb.updatePerson(database, person); // Angenommen, dass eine Methode updatePerson existiert
        });
    }
}
