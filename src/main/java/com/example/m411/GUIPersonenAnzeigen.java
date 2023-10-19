package com.example.m411;

import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GUIPersonenAnzeigen {
    private ResultSet resultSet;

    public GUIPersonenAnzeigen(Label nameLabel, Label vornameLabel, Label geschlechtLabel, Label geburtstagLabel) {
        try {
            // Verwenden der DatabaseConnection Klasse für die Verbindung
            Connection connection = DatabaseConnection.getDatabase();

            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM Person",
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY
                );
                resultSet = preparedStatement.executeQuery();

                // Die erste Person anzeigen
                showNextPerson(nameLabel, vornameLabel, geschlechtLabel, geburtstagLabel /*, weitere Labels */);

            } else {
                System.out.println("Datenbankverbindung fehlgeschlagen");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showNextPerson(Label nameLabel, Label vornameLabel, Label geschlechtLabel, Label geburtstagLabel /*, weitere Labels */) {
        try {
            if (resultSet.next()) {
                updatePersonInfo(nameLabel, vornameLabel, geschlechtLabel, geburtstagLabel /*, weitere Labels */);
            } else {
            resultSet.first();
            updatePersonInfo(nameLabel, vornameLabel, geschlechtLabel, geburtstagLabel /*, weitere Labels */);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showPreviousPerson(Label nameLabel, Label vornameLabel, Label geschlechtLabel, Label geburtstagLabel /*, weitere Labels */) {
        try {
            if (resultSet.previous()) {
                updatePersonInfo(nameLabel, vornameLabel, geschlechtLabel, geburtstagLabel /*, weitere Labels */);
            } else {
                resultSet.last();
                updatePersonInfo(nameLabel, vornameLabel, geschlechtLabel, geburtstagLabel /*, weitere Labels */);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePersonInfo(Label nameLabel, Label vornameLabel, Label geschlechtLabel, Label geburtstagLabel/*, weitere Labels */) throws Exception {
        nameLabel.setText(resultSet.getString("Name"));
        vornameLabel.setText(resultSet.getString("Vorname"));
        geschlechtLabel.setText(resultSet.getString("Geschlecht"));
        geburtstagLabel.setText(String.valueOf(resultSet.getDate("Geburtsdatum")));
        // Hier könnten Sie die Informationen für weitere Labels hinzufügen
    }

    public String getCurrentAhvNummer() {
        try {
            if (!resultSet.isAfterLast()) {
                return resultSet.getString("AHV_Nummer");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
