package controller.candidat;

import entities.Candidat;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class CandidatControllerAdd {

    @FXML private TextField txtCin;
    @FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private DatePicker dpDate;
    @FXML private TextField txtEmail;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private Candidat candidat;

    @FXML
    public void initialize() {
        btnSave.setOnAction(e -> saveCandidat());
        btnCancel.setOnAction(e -> closeWindow());
    }

    private void saveCandidat() {
        System.out.println("Save Candidataaaaaa");
        try {
            int cin = Integer.parseInt(txtCin.getText());
            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            LocalDate date = dpDate.getValue();
            String email = txtEmail.getText();

            candidat = new Candidat(cin, nom, prenom, date, email);
            closeWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public Candidat getCandidatFromForm() {
        return candidat;
    }
}