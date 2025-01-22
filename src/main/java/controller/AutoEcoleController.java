package controller;

import entities.AutoEcole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import fxml.AutoEcoleFxml;
import service.AutoEcoleService;

public class AutoEcoleController {
    private AutoEcoleService autoEcoleService;

    @FXML
    private Button addAutoEcole;

    @FXML
    private AutoEcoleFxml autoEcoleFxml=new AutoEcoleFxml(); // FXML injection of AutoEcoleFxml

    // Initialiser AutoEcoleFxml
    @FXML
    public void initialize() {
        autoEcoleService = new AutoEcoleService();
    }

    @FXML
    public void ajouterAutoEcole(ActionEvent event) {
        // Utiliser AutoEcoleFxml pour récupérer les données
        AutoEcole autoEcole = autoEcoleFxml.saisirAutoEcole();

        // Ajouter l'auto-école
        autoEcoleService.ajouteAutoEcole(autoEcole);

        System.out.println("Auto-école ajoutée : " + autoEcole);
    }
}
