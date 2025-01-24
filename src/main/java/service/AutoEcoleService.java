package service;

import dao.AutoEcoleDAO;
import entities.AutoEcole;

public class AutoEcoleService {
    public AutoEcoleService() {
    }
    public void ajouteAutoEcole(AutoEcole autoEcole) {
        AutoEcoleDAO.save(autoEcole);
    }
    public String getPiedDePage() {
        AutoEcole autoEcole = AutoEcoleDAO.getAutoEcole();
        return "numero de telephone : "+autoEcole.getNumTelephone()+"       email : "+autoEcole.getEmail();
    }
    public String enteteAutoEcole() {
        AutoEcole autoEcole = AutoEcoleDAO.getAutoEcole();
        return autoEcole.getNom()+"     "+autoEcole.getAdresse();
    }
}
