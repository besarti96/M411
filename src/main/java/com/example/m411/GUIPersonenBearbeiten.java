package com.example.m411;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class GUIPersonenBearbeiten implements Initializable {
    private static final String SELECT_QUERY = "SELECT * FROM person WHERE AHV_Nummer = ?";
    private static final String UPDATE_QUERY = "UPDATE Person SET Name = ?, Vorname = ?, Geschlecht = ?, Geburtsdatum = ?, AHV_Nummer = ?, ID_Region = ?, Kinderanzahl = ? WHERE AHV_Nummer = ?";

    @FXML
    private TextField editName;
    @FXML
    private TextField editVorname;
    @FXML
    private TextField geschlechtField;
    @FXML
    private TextField birthday;
    @FXML
    private TextField editAHV;
    @FXML
    private TextField choiceRegion;
    @FXML
    private TextField editKinder;

    @FXML
    private Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connection databaseConnection = DatabaseConnection.getDatabase();
        if (databaseConnection == null) {
            // Handle the error appropriately
            return;
        }

        populateFields(databaseConnection);
        setupSaveButton(databaseConnection);
    }

    private void populateFields(Connection connection) {
        String ahvNummer = "756.9999.9999.99"; // Beispiel-AHV-Nummer
        try (PreparedStatement ps = connection.prepareStatement(SELECT_QUERY)) {
            ps.setString(1, ahvNummer);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    editName.setText(resultSet.getString("Name"));
                    editVorname.setText(resultSet.getString("Vorname"));
                    geschlechtField.setText(resultSet.getString("Geschlecht"));
                    birthday.setText(resultSet.getString("Geburtsdatum"));
                    editAHV.setText(String.valueOf(Integer.parseInt(resultSet.getString("AHV_Nummer"))));
                    choiceRegion.setText(resultSet.getString("ID_Region"));
                    editKinder.setText(resultSet.getString("Kinderanzahl"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Methode zum Setzen der AHV-Nummer
    public void setAhvNummer(TextField ahvNummer) {
        this.editAHV = ahvNummer;
        populateFields(DatabaseConnection.getDatabase());
    }
    private void setupSaveButton(Connection connection) {
        saveButton.setOnAction(e -> {
            try (PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {
                // Setzen der Parameter...
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println("Datensatz erfolgreich aktualisiert.");
                } else {
                    System.out.println("Aktualisierung fehlgeschlagen.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
