package controller;

import entities.Candidat;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ShowCandidatsController {

    @FXML
    private TableView<Candidat> candidatTable;
    @FXML
    private TableColumn<Candidat, Integer> cinColumn;
    @FXML
    private TableColumn<Candidat, String> nomColumn;
    @FXML
    private TableColumn<Candidat, String> prenomColumn;
    @FXML
    private TableColumn<Candidat, String> dateColumn; // Assuming you have a String representation or use LocalDate
    @FXML
    private TableColumn<Candidat, String> emailColumn;

    // Holds the selected candidate CIN
    private int selectedCandidatCin = -1;

    @FXML
    public void initialize() {
        // Bind table columns to Candidat properties (make sure property names match your Candidat class)
        cinColumn.setCellValueFactory(new PropertyValueFactory<>("cin"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    /**
     * Sets the candidate list in the table.
     */
    public void setCandidats(List<Candidat> candidats) {
        candidatTable.setItems(FXCollections.observableArrayList(candidats));
    }

    /**
     * Returns the CIN of the selected candidate.
     */
    public int getSelectedCandidatCin() {
        return selectedCandidatCin;
    }

    @FXML
    private void handleSelectCandidat() {
        Candidat selected = candidatTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selectedCandidatCin = selected.getCin();
            // Close the dialog window
            Stage stage = (Stage) candidatTable.getScene().getWindow();
            stage.close();
        }
    }
}
