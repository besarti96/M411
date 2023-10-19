package com.example.m411;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GUIPersonenBearbeiten implements Initializable {
    private final HashMap<String, Integer> regionMap = new HashMap<>();
    private static final String SELECT_QUERY = "SELECT * FROM person WHERE AHV_Nummer = ?";
    private static final String UPDATE_QUERY = "UPDATE Person SET Name = ?, Vorname = ?, Geschlecht = ?, Geburtsdatum = ?, AHV_Nummer = ?, ID_Region = ?, Kinderanzahl = ? WHERE AHV_Nummer = ?";
    public Button cancelButton;
    private String currentAhvNummer;

    @FXML
    public RadioButton manRadio;
    @FXML
    public RadioButton womanRadio;
    @FXML
    public TextField geschlechtField;
    @FXML
    private TextField editName;
    @FXML
    private TextField editVorname;
    @FXML
    private DatePicker birthday;
    @FXML
    private TextField editAHV;
    @FXML
    private ChoiceBox<String> choiceRegion;
    @FXML
    private TextField editKinder;
    @FXML
    private Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup genderToggleGroup = new ToggleGroup();
        manRadio.setToggleGroup(genderToggleGroup);
        womanRadio.setToggleGroup(genderToggleGroup);

        genderToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton selected = (RadioButton) newToggle;
                geschlechtField.setText(selected.getText()); // aktualisiere das TextField
            }
        });

        try (Connection databaseConnection = DatabaseConnection.getDatabase()) {
            if (databaseConnection == null) {
                System.err.println("Datenbankverbindung konnte nicht hergestellt werden.");
                return;
            }
            populateFields(databaseConnection);
            setupSaveButton();
            loadRegionenFromDatabase(databaseConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setupCancelButton();
    }

    private void setupCancelButton() {
        cancelButton.setOnAction(e -> {
            try {
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("persons_view.fxml"))); // Pfad zum FXML des ersten Views
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void loadRegionenFromDatabase(Connection databaseConnection) {
        String query = "SELECT ID_Region, Region FROM RegionTB";
        try (PreparedStatement ps = databaseConnection.prepareStatement(query);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                choiceRegion.getItems().add(resultSet.getString("Region"));
                regionMap.put(resultSet.getString("Region"), resultSet.getInt("ID_Region"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateFields(Connection connection) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_QUERY)) {
            ps.setString(1, currentAhvNummer);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    // Hier Felder füllen
                    editName.setText(resultSet.getString("Name"));
                    editVorname.setText(resultSet.getString("Vorname"));
                    birthday.setValue(LocalDate.parse(resultSet.getString("Geburtsdatum")));
                    editAHV.setText(resultSet.getString("AHV_Nummer"));
                    editKinder.setText(resultSet.getString("Kinderanzahl"));
                    int idRegion = resultSet.getInt("ID_Region");
                    for (Map.Entry<String, Integer> entry : regionMap.entrySet()) {
                        if (entry.getValue().equals(idRegion)) {
                            choiceRegion.getSelectionModel().select(entry.getKey());
                            break;
                        }
                    }
                    String geschlecht = resultSet.getString("Geschlecht");
                    if ("Männlich".equals(geschlecht)) {
                        manRadio.setSelected(true);
                    } else if ("Weiblich".equals(geschlecht)) {
                        womanRadio.setSelected(true);
                    }
                    geschlechtField.setText(geschlecht);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAhvNummer(String ahvNummer) {
        this.currentAhvNummer = ahvNummer;
        try (Connection databaseConnection = DatabaseConnection.getDatabase()) {
            if (databaseConnection != null) {
                populateFields(databaseConnection);
            } else {
                System.err.println("Datenbankverbindung konnte nicht hergestellt werden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupSaveButton() {
        saveButton.setOnAction(e -> {
            try (Connection connection = DatabaseConnection.getDatabase()) {
                assert connection != null;
                try (PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {

                    // Setzen der anderen Werte.
                    ps.setString(1, editName.getText());
                    ps.setString(2, editVorname.getText());
                    ps.setString(3, geschlechtField.getText());
                    ps.setDate(4, Date.valueOf(birthday.getValue()));
                    ps.setString(5, editAHV.getText());

                    String selectedRegion = choiceRegion.getSelectionModel().getSelectedItem();
                    int idRegion = regionMap.getOrDefault(selectedRegion, 0); // 0 als Standardwert
                    ps.setInt(6, idRegion); // 6. Platzhalter in UPDATE_QUERY

                    ps.setString(7, editKinder.getText());
                    ps.setString(8, currentAhvNummer);

                    ps.executeUpdate(); // Ausführen der Abfrage

                    int rowsAffected = ps.executeUpdate(); // Ausführen der Abfrage

                    if (rowsAffected > 0) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Update Status");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully updated.");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Update Status");
                        alert.setHeaderText(null);
                        alert.setContentText("Update failed.");
                        alert.showAndWait();
                    }

                    // Zurück zum ersten View
                    Stage stage = (Stage) saveButton.getScene().getWindow();
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("persons_view.fxml")));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

}
