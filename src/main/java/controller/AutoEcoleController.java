package controller;

import entities.AutoEcole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import service.AutoEcoleService;

public class AutoEcoleController {
    private AutoEcoleService autoEcoleService;

    @FXML private Button addAutoEcole;
    @FXML private TextField nomField;
    @FXML private TextField adresseField;
    @FXML private TextField telephoneField;
    @FXML private TextField emailField;

    public void initialize() {
        autoEcoleService = new AutoEcoleService();
    }

    public void ajouterAutoEcole(ActionEvent event) {


            String nomAutoEcole = nomField.getText();
            String adresseAutoEcole = adresseField.getText();
            String emailAutoEcole = emailField.getText();


            int telAutoEcole = 0;
            try {
                telAutoEcole = Integer.parseInt(telephoneField.getText());
            } catch (NumberFormatException e) {
                System.out.println("Numéro de téléphone invalide.");
            }

        AutoEcole autoEcole = new AutoEcole(nomAutoEcole, adresseAutoEcole, telAutoEcole, emailAutoEcole);
        autoEcoleService.ajouteAutoEcole(autoEcole);
        System.out.println("Auto-école ajoutée : " + autoEcole);
    }
}
