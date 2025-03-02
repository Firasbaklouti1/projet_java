package entities.rdv;

import java.time.LocalDateTime;

public class DisponabiliteMoniteur {
    private int id;
    private int cinMoniteur;
    private LocalDateTime debut;
    private LocalDateTime fin;

    public DisponabiliteMoniteur(int id, int cinMoniteur, LocalDateTime debut, LocalDateTime fin) {
        this.id = id;
        this.cinMoniteur = cinMoniteur;
        this.debut = debut;
        this.fin = fin;
    }

    public DisponabiliteMoniteur(int cinMoniteur, LocalDateTime debut, LocalDateTime fin) {
        this.cinMoniteur = cinMoniteur;
        this.debut = debut;
        this.fin = fin;
    }

    public DisponabiliteMoniteur() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCinMoniteur() {
        return cinMoniteur;
    }

    public void setCinMoniteur(int cinMoniteur) {
        this.cinMoniteur = cinMoniteur;
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
