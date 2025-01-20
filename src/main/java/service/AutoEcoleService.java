package service;

import dao.AutoEcoleDAO;
import entities.AutoEcole;

public class AutoEcoleService {
    public AutoEcoleService() {
    }
    public void ajouteAutoEcole(AutoEcole autoEcole) {
        AutoEcoleDAO.save(autoEcole);
    }
}
