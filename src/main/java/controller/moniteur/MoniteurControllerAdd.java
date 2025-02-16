package controller.moniteur;

import entities.Moniteur;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MoniteurControllerAdd {

    @FXML
    private TextField txtCin;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private DatePicker dpDate;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtSalaire;
    @FXML
    private CheckBox cbDispo;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    private Moniteur moniteur;

    @FXML
    public void initialize() {
        btnSave.setOnAction(e -> saveMoniteur());
        btnCancel.setOnAction(e -> closeWindow());
    }

    private void saveMoniteur() {
        try {
            int cin = Integer.parseInt(txtCin.getText());
            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            LocalDate date = dpDate.getValue();
            String email = txtEmail.getText();
            double salaire = Double.parseDouble(txtSalaire.getText());
            boolean dispo = cbDispo.isSelected();

            // Create a new Moniteur instance (adjust constructor as needed)
            moniteur = new Moniteur(cin, nom, prenom, date, email, salaire, dispo);

            closeWindow();
        } catch (Exception ex) {
            ex.printStackTrace();
            // Optionally display an alert to the user
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    // This method will be called after the window is closed to retrieve the new Moniteur.
    public Moniteur getMoniteurFromForm() {
        return moniteur;
    }
}
