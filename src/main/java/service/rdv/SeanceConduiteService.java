package service.rdv;

import dao.rdv.DisponabiliteMoniteurDAO;
import dao.rdv.DisponabiliteVehiculeDAO;
import dao.rdv.SeanceConduiteDAO;
import entities.rdv.DisponabiliteMoniteur;
import entities.rdv.DisponabiliteVehicule;
import entities.rdv.Seance;
import entities.rdv.SeanceConduite;

import java.time.LocalDateTime;
import java.util.List;

public class SeanceConduiteService {
    private DisponabiliteMoniteurService disponabiliteMoniteurService;
    private DisponabiliteVehiculeService disponabiliteVehiculeService;


    public boolean estDisponibleMoniteur(int cinMoniteur, LocalDateTime debut, LocalDateTime fin) {
        List<DisponabiliteMoniteur> list= DisponabiliteMoniteurDAO.getAll();
        return list.stream()
                .filter(moniteur -> moniteur.getCinMoniteur() == cinMoniteur)
                .noneMatch(interval ->
                debut.isBefore(interval.getFin()) && fin.isAfter(interval.getDebut()));
    }

    public boolean estDisponibleVehicule(int numMatricule, LocalDateTime debut, LocalDateTime fin) {
        List<DisponabiliteVehicule> list= DisponabiliteVehiculeDAO.getAll();
        return list.stream()
                .filter(vehicule -> vehicule.getNumMatricule() == numMatricule)
                .noneMatch(interval ->
                        debut.isBefore(interval.getFin()) && fin.isAfter(interval.getDebut()));
    }
    public void deleate(int id){
        SeanceConduiteDAO.delete(id);
    }

    public void ajouter(Seance seance) {
        disponabiliteMoniteurService=new DisponabiliteMoniteurService();
        disponabiliteVehiculeService=new DisponabiliteVehiculeService();



        SeanceConduiteDAO.ajouter((SeanceConduite) seance);
        disponabiliteMoniteurService.ajouter(new DisponabiliteMoniteur(seance.getCinMoniteur(),seance.getDebut(),seance.getDebut().plusMinutes(seance.getDuree())));
        disponabiliteVehiculeService.ajouter(new DisponabiliteVehicule(((SeanceConduite) seance).getNumMatricule(),seance.getDebut(),seance.getDebut().plusMinutes(seance.getDuree())));




    }

    public List<SeanceConduite> getSeancesByCandidat(int cinCandidat) {
        return SeanceConduiteDAO.getSeancesByCandidat(cinCandidat);
    }
}
