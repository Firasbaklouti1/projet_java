package controller;

import entities.Document;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.DocumentService;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;

public class DocumentController {

    @FXML
    private TextField candidatIdField;

    @FXML
    private DatePicker dateAjoutField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField typeField;

    @FXML
    private Label fileNameLabel;

    private File selectedFile;
    private DocumentService documentService = new DocumentService();

    public void setNumCin(int candidatId) {
        candidatIdField.setText(String.valueOf(candidatId));
        candidatIdField.setEditable(false);
    }


    @FXML
    private void handleFileUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner le fichier du document");
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            fileNameLabel.setText(selectedFile.getName());
        }
    }


    @FXML
    private void saveDocument(ActionEvent event) {
        try {
            if (selectedFile == null) {
                showAlert("Erreur", "Veuillez sélectionner un fichier à uploader.", Alert.AlertType.ERROR);
                return;
            }

            byte[] fileContent = Files.readAllBytes(selectedFile.toPath());
            int candidatId = Integer.parseInt(candidatIdField.getText());
            String nom = nomField.getText();
            String type = typeField.getText();
            LocalDate dateAjout = dateAjoutField.getValue();

            Document document = new Document(candidatId, nom, type, dateAjout, new SerialBlob(fileContent));
            documentService.addDocument(document);

            // Close the window after saving
            Stage stage = (Stage) candidatIdField.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur d'enregistrement : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) candidatIdField.getScene().getWindow();
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
