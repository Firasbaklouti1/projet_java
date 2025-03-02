package entities.rdv;

import java.time.LocalDateTime;

public class DisponabiliteVehicule {
    private int id;
    private int numMatricule;
    private LocalDateTime debut;
    private LocalDateTime fin;

    public DisponabiliteVehicule(int id, int numMatricule, LocalDateTime debut, LocalDateTime fin) {
        this.id = id;
        this.numMatricule = numMatricule;
        this.debut = debut;
        this.fin = fin;
    }

    public DisponabiliteVehicule(int numMatricule, LocalDateTime debut, LocalDateTime fin) {
        this.numMatricule = numMatricule;
        this.debut = debut;
        this.fin = fin;
    }

    public DisponabiliteVehicule() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumMatricule() {
        return numMatricule;
    }

    public void setNumMatricule(int numMatricule) {
        this.numMatricule = numMatricule;
    }

    public LocalDateTime getDebut() {
        return debut;
    }

    public void setDebut(LocalDateTime debut) {
        this.debut = debut;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }
}
