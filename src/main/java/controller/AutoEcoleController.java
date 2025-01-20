package controller;

import entities.AutoEcole;
import service.AutoEcoleService;
import fxml.AutoEcoleFxml;

public class AutoEcoleController {
    private AutoEcoleService autoEcoleService;
    private AutoEcoleFxml autoEcoleFxml;

    public AutoEcoleController() {
        autoEcoleService=new AutoEcoleService();
        autoEcoleFxml=new AutoEcoleFxml();
    }

    public void autoEcoleController() {
        AutoEcole autoEcole = autoEcoleFxml.saisirAutoEcole();

        autoEcoleService.ajouteAutoEcole(autoEcole);
    }
}
