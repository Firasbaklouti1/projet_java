package controller.candidat;

import entities.Candidat;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class CandidatControllerUpdate {

    @FXML private TextField txtCin;
    @FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private DatePicker dpDate;
    @FXML private TextField txtEmail;
    @FXML private Button btnUpdate;
    @FXML private Button btnCancel;

    private Candidat candidat;

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public void showDetails() {
        txtCin.setText(String.valueOf(candidat.getCin()));
        txtCin.setDisable(true);
        txtNom.setText(candidat.getNom());
        txtPrenom.setText(candidat.getPrenom());
        dpDate.setValue(candidat.getDate());
        txtEmail.setText(candidat.getEmail());
    }

    @FXML
    public void initialize() {
        btnUpdate.setOnAction(e -> updateCandidat());
        btnCancel.setOnAction(e -> closeWindow());
    }

    private void updateCandidat() {
        try {
            candidat.setNom(txtNom.getText());
            candidat.setPrenom(txtPrenom.getText());
            candidat.setDate(dpDate.getValue());
            candidat.setEmail(txtEmail.getText());
            closeWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public Candidat getUpdatedCandidat() {
        return candidat;
    }
}