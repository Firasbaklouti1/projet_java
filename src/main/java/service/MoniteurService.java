package service;



import dao.MoniteurDAO;
import entities.Moniteur;

import java.util.List;

public class MoniteurService {



    public List<Moniteur> getAllMoniteurs() {
        return MoniteurDAO.getAllMoniteurs();
    }

    public void addMoniteur(Moniteur moniteur) {
        MoniteurDAO.ajouterMoniteur(moniteur);
    }

    public void updateMoniteur(Moniteur moniteur) {
        MoniteurDAO.updateMoniteur(moniteur);
    }

    public void deleteMoniteur(int moniteurId) {
        MoniteurDAO.deleteMoniteur(moniteurId);
    }
}