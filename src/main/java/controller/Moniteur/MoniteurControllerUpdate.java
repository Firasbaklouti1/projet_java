package controller.Moniteur;

import entities.Moniteur;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MoniteurControllerUpdate {

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
    private Button btnUpdate;
    @FXML
    private Button btnCancel;

    private Moniteur moniteur;

    // Called from the main controller to set the current moniteur.
    public void setMoniteur(Moniteur moniteur) {
        this.moniteur = moniteur;
    }

    // Preload the moniteur data into the form fields.
    public void afficherDetailsMoniteur() {
        if (moniteur != null) {
            txtCin.setText(String.valueOf(moniteur.getCin()));
            txtCin.setDisable(true); // disable editing of the unique identifier
            txtNom.setText(moniteur.getNom());
            txtPrenom.setText(moniteur.getPrenom());
            dpDate.setValue(moniteur.getDate());
            txtEmail.setText(moniteur.getEmail());
            txtSalaire.setText(String.valueOf(moniteur.getSalaire()));
            cbDispo.setSelected(moniteur.getDispo());
        }
    }

    @FXML
    public void initialize() {
        btnUpdate.setOnAction(e -> updateMoniteur());
        btnCancel.setOnAction(e -> closeWindow());
    }

    private void updateMoniteur() {
        try {
            // The CIN is not editable so we use the existing one.
            int cin = Integer.parseInt(txtCin.getText());
            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            LocalDate date = dpDate.getValue();
            String email = txtEmail.getText();
            double salaire = Double.parseDouble(txtSalaire.getText());
            boolean dispo = cbDispo.isSelected();

            // Create an updated Moniteur instance.
            moniteur = new Moniteur(cin, nom, prenom, date, email,  salaire,dispo);
            closeWindow();
        } catch (Exception ex) {
            ex.printStackTrace();
            // Optionally display an error message.
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    // Called after closing to retrieve the updated moniteur.
    public Moniteur getMoniteurFromForm(int cin) {
        return moniteur;
    }
}
