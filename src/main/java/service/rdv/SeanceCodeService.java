package service.rdv;

import dao.rdv.SeanceCodeDAO;
import entities.rdv.DisponabiliteMoniteur;
import entities.rdv.SeanceCode;

import java.util.List;

public class SeanceCodeService {
    private DisponabiliteMoniteurService disponabiliteMoniteurService;

    public void ajouter(SeanceCode seanceCode){
        disponabiliteMoniteurService=new DisponabiliteMoniteurService();
        disponabiliteMoniteurService.ajouter(new DisponabiliteMoniteur(seanceCode.getCinMoniteur(),seanceCode.getDebut(),seanceCode.getDebut().plusMinutes(seanceCode.getDuree())));

        SeanceCodeDAO.ajouter(seanceCode);
    }
    public void delete(int id){
        SeanceCodeDAO.delete(id);
    }
    public void update(SeanceCode seanceCode){
        SeanceCodeDAO.update(seanceCode);
    }
    public SeanceCode getSeanceCode(int id){
        return SeanceCodeDAO.getSeanceCode(id);
    }
    public List<SeanceCode> getAll(){
        return SeanceCodeDAO.getAll();
    }
    public List<SeanceCode> getSeancesByCandidat(int cin){
        return SeanceCodeDAO.getSeancesByCandidat(cin);
    }
}
