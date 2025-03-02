package controller;

import entities.Moniteur;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ShowMoniteursController {

    @FXML
    private TableView<Moniteur> moniteurTable;
    @FXML
    private TableColumn<Moniteur, Integer> cinColumn;
    @FXML
    private TableColumn<Moniteur, String> nomColumn;
    @FXML
    private TableColumn<Moniteur, String> prenomColumn;
    @FXML
    private TableColumn<Moniteur, String> dateColumn;
    @FXML
    private TableColumn<Moniteur, String> emailColumn;
    @FXML
    private TableColumn<Moniteur, Double> salaireColumn;
    @FXML
    private TableColumn<Moniteur, Boolean> dispoColumn;

    // Holds the selected moniteur CIN
    private int selectedMoniteurCin = -1;

    @FXML
    public void initialize() {
        // Bind table columns to Moniteur properties (ensure these names match your Moniteur class)
        cinColumn.setCellValueFactory(new PropertyValueFactory<>("cin"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        salaireColumn.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        dispoColumn.setCellValueFactory(new PropertyValueFactory<>("dispo"));
    }

    /**
     * Sets the moniteur list in the table.
     */
    public void setMoniteurs(List<Moniteur> moniteurs) {
        moniteurTable.setItems(FXCollections.observableArrayList(moniteurs));
    }

    /**
     * Returns the CIN of the selected moniteur.
     */
    public int getSelectedMoniteurCin() {
        return selectedMoniteurCin;
    }

    @FXML
    private void handleSelectMoniteur() {
        Moniteur selected = moniteurTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selectedMoniteurCin = selected.getCin();
            // Close the dialog window
            Stage stage = (Stage) moniteurTable.getScene().getWindow();
            stage.close();
        }
    }
}
