package controller;

import entities.TypePermis;
import entities.Vehicule;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class ShowVehiculesController {

    @FXML
    private TableView<Vehicule> vehiculeTable;

    @FXML
    private TableColumn<Vehicule, Integer> matriculeColumn;

    @FXML
    private TableColumn<Vehicule, String> typeColumn;

    @FXML
    private TableColumn<Vehicule, String> miseEnServiceColumn;

    @FXML
    private TableColumn<Vehicule, Integer> kilometrageTotaleColumn;

    // Stocke le matricule du véhicule sélectionné
    private int selectedVehiculeMatricule = -1;

    @FXML
    public void initialize() {
        // Liez les colonnes aux propriétés du véhicule
        matriculeColumn.setCellValueFactory(new PropertyValueFactory<>("numMatricule"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        miseEnServiceColumn.setCellValueFactory(new PropertyValueFactory<>("miseEnService"));
        // Veillez à ce que le nom de la propriété corresponde au getter de KilometrageTotale (ex: getKilometrageTotale)
        kilometrageTotaleColumn.setCellValueFactory(new PropertyValueFactory<>("kilometrageTotale"));
    }

    /**
     * Définit la liste des véhicules dans le tableau.
     *
     * @param vehicules la liste des véhicules à afficher.
     */
    public void setVehicules(List<Vehicule> vehicules, TypePermis typePermis) {

            List<Vehicule> list=vehicules.stream()
                    .filter(v->v.getType().toString().equals(typePermis.toString()))
                    .collect(Collectors.toList());
            vehiculeTable.setItems(FXCollections.observableArrayList(list));


    }

    /**
     * Retourne le matricule du véhicule sélectionné.
     *
     * @return le matricule sélectionné ou -1 s'il n'y en a pas.
     */
    public int getSelectedVehiculeMatricule() {
        return selectedVehiculeMatricule;
    }

    @FXML
    private void handleSelectVehicule() {
        Vehicule selected = vehiculeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selectedVehiculeMatricule = selected.getNumMatricule();
            // Ferme la fenêtre de dialogue
            Stage stage = (Stage) vehiculeTable.getScene().getWindow();
            stage.close();
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
