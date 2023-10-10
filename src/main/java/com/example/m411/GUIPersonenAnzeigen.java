package com.example.m411;

import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIPersonenAnzeigen implements Initializable {
    @FXML
    private Label nameLabel;

    @FXML
    private Label vornameLabel;

    @FXML
    private Label geschlechtLabel;

    @FXML
    private Label geburtsdatumLabel;

    @FXML
    private Label ahvNummerLabel;

    @FXML
    private Label idRegionLabel;

    @FXML
    private Label kinderanzahlLabel;

    @FXML
    private Button editButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Beispielsweise können Sie die Datenbankverbindung hier herstellen
        MongoDatabase database = mongodb.getDatabase();

        // Hier könnte der Code stehen, um eine spezifische Person anhand eines Identifikators abzurufen
        String ahvNummer = "756.9999.9999.99"; // Beispiel-AHV-Nummer
        Person person = mongodb.getPerson(database, ahvNummer); // Angenommen, dass eine Methode getPerson existiert

        // Setzen der Labels mit den Daten aus der MongoDB
        assert person != null;
        nameLabel.setText(person.getName());
        vornameLabel.setText(person.getVorname());
        geschlechtLabel.setText(person.getGeschlecht());
        geburtsdatumLabel.setText(person.getGeburtsdatum());
        ahvNummerLabel.setText(person.getAhvNummer());
        idRegionLabel.setText(person.getIdRegion());
        kinderanzahlLabel.setText(String.valueOf(person.getKinderanzahl()));

        editButton.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("com/example/m411/edit_person_view.fxml"));
                Parent root = fxmlLoader.load();

                // Erstellen Sie eine neue Szene mit der Bearbeitungsansicht
                Scene scene = new Scene(root);

                // Erstellen Sie ein neues Fenster (Stage) für die Bearbeitungsansicht
                Stage editStage = new Stage();
                editStage.setTitle("Person Bearbeiten"); // Titel des Bearbeitungsfensters
                editStage.setScene(scene);

                // Zeigen Sie das Bearbeitungsfenster an
                editStage.show();

                // Schließen Sie das aktuelle Fenster (dieses Fenster)
                Stage currentStage = (Stage) editButton.getScene().getWindow();
                currentStage.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    @FXML
    private Button edit;

    @FXML
    private void handleEditButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) edit.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("personen_bearbeiten.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }
}
