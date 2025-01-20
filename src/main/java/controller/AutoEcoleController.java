package controller;

import entities.AutoEcole;
import fxml.AutoEcoleFxml;
import service.AutoEcoleService;

public class AutoEcoleController {
    AutoEcoleService autoEcoleService;

    public AutoEcoleController() {
        autoEcoleService = new AutoEcoleService();
    }
    public void autoEcoleController(){
        AutoEcole autoEcole=AutoEcoleFxml.saisirAutoEcole();
        autoEcoleService.ajouteAutoEcole(autoEcole);
    }
}
