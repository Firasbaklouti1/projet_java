package controller.vehicule;

import entities.Maintenance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.MaintenanceService;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.nio.file.Files;

public class VehiculeControllerMaintenance {
    @FXML private TextField numMatriculeField;
    @FXML private DatePicker dateField;
    @FXML private TextField descriptionField;
    @FXML private TextField priceField;
    @FXML private Label fileNameLabel;

    private File selectedFile;
    private MaintenanceService maintenanceService = new MaintenanceService();
    private int selectedVehiculeMatricule;

    public void setSelectedVehicule(int numMatricule) {
        this.selectedVehiculeMatricule = numMatricule;
        numMatriculeField.setText(String.valueOf(numMatricule));
    }

    @FXML
    private void handleFileUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner la facture");
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null && (selectedFile.getName().endsWith(".pdf")|| selectedFile.getName().endsWith(".png"))) {
            fileNameLabel.setText(selectedFile.getName());
        }
    }

    @FXML
    private void saveMaintenance(ActionEvent event) {
        try {
            byte[] fileContent = Files.readAllBytes(selectedFile.toPath());

            Maintenance maintenance = new Maintenance(selectedVehiculeMatricule,dateField.getValue(),
                    descriptionField.getText(),Double.parseDouble(priceField.getText()),new SerialBlob(fileContent));
            maintenanceService.addMaintenance(maintenance);

            Stage stage = (Stage) numMatriculeField.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            showAlert("Erreur", "Erreur d'enregistrement : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) numMatriculeField.getScene().getWindow();
        stage.close();
    }


    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
