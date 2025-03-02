package controller;

import entities.Candidat;
import entities.Moniteur;
import entities.TypePermis;
import entities.Vehicule;
import entities.rdv.Conduite;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.CandidatService;
import service.MoniteurService;
import service.VehiculeService;
import service.rdv.ConduiteService;

import java.util.List;

public class AddConduiteController {

    @FXML private ComboBox<String> typePermisCombo;
    @FXML private Label selectedCandidatCinLabel;
    @FXML private Label selectedMoniteurCinLabel;
    @FXML private Label selectedMatriculeLabel;

    private CandidatService candidatService;
    private MoniteurService moniteurService;
    private VehiculeService vehiculeService;
    private int selectedCandidatCin = -1;
    private int selectedMoniteurCin = -1;
    private int selectedMatricule=-1;

    @FXML
    public void initialize() {
        typePermisCombo.setItems(FXCollections.observableArrayList("moto", "voiture", "camion"));
    }





    @FXML
    private void handleSave(ActionEvent event) {
        try {
            // Validation des champs
            if (typePermisCombo.getValue() == null ||
                    selectedCandidatCin == -1 ||
                    selectedMoniteurCin == -1 ||
                    selectedMatricule==-1) {

                showAlert("Veuillez remplir tous les champs obligatoires");
                return;
            }

            TypePermis typePermis = TypePermis.valueOf(typePermisCombo.getValue());

            Conduite conduite = new Conduite(
                    typePermis,
                    selectedCandidatCin,
                    selectedMoniteurCin,
                    selectedMatricule
            );

            new ConduiteService().ajouterConduite(conduite);
            closeWindow();

        } catch (NumberFormatException e) {
            showAlert("Format numérique invalide pour le matricule");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void closeWindow() {
        Stage stage = (Stage) typePermisCombo.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void handleSelectCandidate(ActionEvent event) {
        candidatService = new CandidatService();
        // Show the candidate selection dialog and get the selected CIN
        selectedCandidatCin = showCandidats(candidatService.getAllCandidats());
        selectedCandidatCinLabel.setText(selectedCandidatCin == -1 ? "Aucun candidat sélectionné" : String.valueOf(selectedCandidatCin));
    }
    @FXML
    private void handleSelectVehicule(ActionEvent event) {
        vehiculeService = new VehiculeService();
        try {
            TypePermis typePermis=TypePermis.valueOf(typePermisCombo.getValue());

            // Show the candidate selection dialog and get the selected CIN
            selectedMatricule = showVehicules(vehiculeService.getAllVehicules(),typePermis);

            selectedMatriculeLabel.setText(selectedMatricule == -1 ? "Aucun vehicule sélectionné" : String.valueOf(selectedMatricule));
        } catch (Exception e) {
            showAlert("choisir le type de permi");
        }

    }



    @FXML
    private void handleSelectMoniteur(ActionEvent event) {
        moniteurService = new MoniteurService();
        // Show the moniteur selection dialog and get the selected CIN
        selectedMoniteurCin = showMoniteurs(moniteurService.getAllMoniteurs());
        selectedMoniteurCinLabel.setText(selectedMoniteurCin == -1 ? "Aucun moniteur sélectionné" : String.valueOf(selectedMoniteurCin));
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
    @FXML
    private void handleCancel(ActionEvent event) {
        closeWindow();
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
    private int showVehicules(List<Vehicule> allVehicules,TypePermis typePermis) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/formation/ShowVehicules.fxml"));
            Parent root = loader.load();
            // Get the controller and pass the vehicule list
            ShowVehiculesController controller = loader.getController();
            controller.setVehicules(allVehicules,typePermis);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Sélectionner un Véhicule");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Return the selected vehicule's matricule
            return controller.getSelectedVehiculeMatricule();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }



}