package service.rdv;


import dao.rdv.ConduiteDAO;
import entities.rdv.Conduite;

import java.util.List;

public class ConduiteService {
    public void ajouterConduite(Conduite conduite) {
        ConduiteDAO.ajouterConduite(conduite);
    }
    public void updateConduite(Conduite conduite) {
        ConduiteDAO.updateConduite(conduite);
    }
    public void deleteConduite(int conduiteId) {
        ConduiteDAO.deleteConduite(conduiteId);
    }
    public List<Conduite> getAllConduites() {
        return ConduiteDAO.getAllConduites();
    }

    public String getNameOfMoniteur(int cinMoniteur) {
        return ConduiteDAO.getNameOfMoniteur(cinMoniteur);
    }

    public String getNameOfCandidat(int cinCandidat) {
        return ConduiteDAO.getNameOfCandidat(cinCandidat);
    }
}
