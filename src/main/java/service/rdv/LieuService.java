package service.rdv;

import dao.rdv.LieuDAO;
import entities.rdv.Lieu;

import java.util.List;

public class LieuService {
    public int ajouter(Lieu lieu) {
        return LieuDAO.ajouter(lieu);
    }
    public void supprimer(int id) {
        LieuDAO.delete(id);
    }
    public Lieu getLieu(int id) {
        return LieuDAO.getLieu(id);
    }
    public List<Lieu> getAllLieu() {
        return LieuDAO.getAll();
    }
}
