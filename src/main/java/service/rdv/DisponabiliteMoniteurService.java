package service.rdv;

import dao.rdv.DisponabiliteMoniteurDAO;
import entities.rdv.DisponabiliteMoniteur;

import java.util.List;

public class DisponabiliteMoniteurService {
    public void ajouter(DisponabiliteMoniteur disponabiliteMoniteur){
        DisponabiliteMoniteurDAO.ajouter(disponabiliteMoniteur);
    }
    public void delete(int id){
        DisponabiliteMoniteurDAO.delete(id);
    }
    public void update(DisponabiliteMoniteur disponabiliteMoniteur){
        DisponabiliteMoniteurDAO.update(disponabiliteMoniteur);
    }
    public DisponabiliteMoniteur getDisponabiliteMoniteur(int id){
        return DisponabiliteMoniteurDAO.getDisponabiliteMoniteur(id);
    }
    public List<DisponabiliteMoniteur>getAll(){
        return DisponabiliteMoniteurDAO.getAll();
    }
    public List<DisponabiliteMoniteur> getDisponabiliteMoniteurByCin(int cin){
        return DisponabiliteMoniteurDAO.getDisponabiliteMoniteurByCin(cin);

    }
}
