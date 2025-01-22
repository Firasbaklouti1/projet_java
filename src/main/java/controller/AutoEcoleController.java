package controller;

import entities.AutoEcole;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import service.AutoEcoleService;
import fxml.AutoEcoleFxml;

public class AutoEcoleController {
    private AutoEcoleService autoEcoleService;
    private AutoEcoleFxml autoEcoleFxml;
    @FXML private Button addAutoEcole;


    public AutoEcoleController() {
        autoEcoleService=new AutoEcoleService();
        autoEcoleFxml=new AutoEcoleFxml();
    }

    public void autoEcoleController() {
        addAutoEcole.setOnAction(e ->{
            AutoEcole autoEcole = autoEcoleFxml.saisirAutoEcole();

            autoEcoleService.ajouteAutoEcole(autoEcole);} );

    }
}
