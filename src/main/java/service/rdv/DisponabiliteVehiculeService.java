package service.rdv;

import dao.rdv.DisponabiliteVehiculeDAO;
import entities.rdv.DisponabiliteVehicule;

import java.util.List;

public class DisponabiliteVehiculeService {
    public void ajouter(DisponabiliteVehicule disponabiliteVehicule){
        DisponabiliteVehiculeDAO.ajouter(disponabiliteVehicule);
    }
    public void delete(int id){
        DisponabiliteVehiculeDAO.delete(id);
    }
    public void update(DisponabiliteVehicule disponabiliteVehicule){
        DisponabiliteVehiculeDAO.update(disponabiliteVehicule);
    }
    public DisponabiliteVehicule getDisponabiliteVehicule(int id){
        return DisponabiliteVehiculeDAO.getDisponabiliteVehicule(id);
    }
    public List<DisponabiliteVehicule>getAll(){
        return DisponabiliteVehiculeDAO.getAll();
    }
    public List<DisponabiliteVehicule>getDisponabiliteVehiculeByMatricule(int matricule){
        return DisponabiliteVehiculeDAO.getDisponabiliteVehiculeByMatricule(matricule);
    }
}
