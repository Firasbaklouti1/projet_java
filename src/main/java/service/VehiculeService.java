package service;

import dao.VehiculeDAO;
import entities.Vehicule;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class VehiculeService {
    public void addVehicule(Vehicule vehicule){
        VehiculeDAO.addVehicule(vehicule);
    }
    public Vehicule getVehicule(int numMatricule){
        return VehiculeDAO.getVehicule(numMatricule);
    }
    public List<Vehicule> getAllVehicules(){
        return VehiculeDAO.getAllVehicules();
    }
    public void updateVehicule(Vehicule vehicule){
        VehiculeDAO.updateVehicule(vehicule);
    }
    public void deleteVehicule(int numMatricule){
        VehiculeDAO.deleteVehicule(numMatricule);
    }
    public void updateKmAvantEntretien(int numMatricule, int kms){
        VehiculeDAO.updateKmAvantEntretien(numMatricule,kms);
    }
    public Map<Vehicule, LocalDate> getVignetteAlert(){
        return VehiculeDAO.getVignetteAlert();
    }
    public Map<Vehicule, LocalDate>getVisiteTechniqueAlert(){
        return VehiculeDAO.getVisiteTechniqueAlert();
    }
    public Map<Vehicule, LocalDate>getAssuranceAlert(){
        return VehiculeDAO.getAssuranceAlert();
    }
    public Map<Vehicule, LocalDate>getVidangeAlert(){
        return VehiculeDAO.getVidangeAlert();
    }
    public Map<Vehicule, Integer>getEntretientAlert() {
        return VehiculeDAO.getEntretientAlert();
    }
}
