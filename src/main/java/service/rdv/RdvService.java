package service.rdv;

import entities.rdv.Seance;

import java.time.LocalDateTime;
import java.util.List;

public class RdvService {
    private SeanceConduiteService seanceConduiteService=new SeanceConduiteService();
    public List<Seance> getAllSeances() {
        return null;
    }

    public boolean checkDisponibilite(int cin, int i, LocalDateTime debut, LocalDateTime fin) {
        return seanceConduiteService.estDisponibleMoniteur(cin, debut, fin) && seanceConduiteService.estDisponibleVehicule(i, debut, fin);
    }

    public void deleteSeance(int id) {
        seanceConduiteService.deleate(id);
    }

    public void addSeance(Seance seance) {
        seanceConduiteService.ajouter(seance);
    }
}
