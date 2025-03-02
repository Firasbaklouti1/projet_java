package controller;

import entities.Candidat;
import entities.Moniteur;
import entities.TypePermis;
import entities.rdv.Code;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.CandidatService;
import service.MoniteurService;
import service.rdv.CodeService;

import java.util.List;

public class AddCodeController {

    @FXML
    private ComboBox<String> typePermisCombo;

    // Labels to show the selected CINs
    @FXML
    private Label selectedCandidatCinLabel;
    @FXML
    private Label selectedMoniteurCinLabel;

    private CandidatService candidatService;
    private MoniteurService moniteurService;

    // Variables to hold the actual CIN numbers
    private int selectedCandidatCin = -1;
    private int selectedMoniteurCin = -1;

    @FXML
    public void initialize() {
        // Initialize the permis types
        typePermisCombo.setItems(FXCollections.observableArrayList("moto", "voiture", "camion"));
    }

    @FXML
    private void handleSelectCandidate(ActionEvent event) {
        candidatService = new CandidatService();
        // Show the candidate selection dialog and get the selected CIN
        selectedCandidatCin = showCandidats(candidatService.getAllCandidats());
        selectedCandidatCinLabel.setText(selectedCandidatCin == -1 ? "Aucun candidat sélectionné" : String.valueOf(selectedCandidatCin));
    }

    @FXML
    private void handleSelectMoniteur(ActionEvent event) {
        moniteurService = new MoniteurService();
        // Show the moniteur selection dialog and get the selected CIN
        selectedMoniteurCin = showMoniteurs(moniteurService.getAllMoniteurs());
        selectedMoniteurCinLabel.setText(selectedMoniteurCin == -1 ? "Aucun moniteur sélectionné" : String.valueOf(selectedMoniteurCin));
    }

    @FXML
    private void handleSave(ActionEvent event) {
        // Validate that a permis type has been selected.
        String selectedValue = typePermisCombo.getValue();
        if (selectedValue == null || selectedValue.trim().isEmpty()) {
            System.out.println("Veuillez sélectionner un type de permis.");
            return;
        }

        // Validate that both a candidate and a moniteur have been selected.
        if (selectedCandidatCin == -1) {
            System.out.println("Veuillez sélectionner un candidat.");
            return;
        }
        if (selectedMoniteurCin == -1) {
            System.out.println("Veuillez sélectionner un moniteur.");
            return;
        }

        // Convert the selected permis type to the enum value (assuming enum names are in uppercase)
        TypePermis typePermis = TypePermis.valueOf(selectedValue);

        // Create the Code object using the selected CIN values
        Code code = new Code(typePermis, selectedCandidatCin, selectedMoniteurCin);

        // Save the code (assuming CodeService handles persistence)
        new CodeService().ajouterCode(code);

        // Close the current window
        closeWindow();
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) typePermisCombo.getScene().getWindow();
        stage.close();
    }


    private int showCandidats(List<Candidat> candidats) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/formation/ShowCandidats.fxml"));
            Parent root = loader.load();
            // Get the controller and pass the candidate list
            ShowCandidatsController controller = loader.getController();
            controller.setCandidats(candidats);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Sélectionner un Candidat");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Return the selected candidate's CIN
            return controller.getSelectedCandidatCin();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    private int showMoniteurs(List<Moniteur> moniteurs) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/formation/ShowMoniteurs.fxml"));
            Parent root = loader.load();
            // Get the controller and pass the moniteur list
            ShowMoniteursController controller = loader.getController();
            controller.setMoniteurs(moniteurs);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Sélectionner un Moniteur");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Return the selected moniteur's CIN
            return controller.getSelectedMoniteurCin();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
