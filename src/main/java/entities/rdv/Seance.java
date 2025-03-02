package entities.rdv;

import java.time.LocalDateTime;

public class Seance {
    private int id;
    private int cinCandidat;
    private int cinMoniteur;
    private LocalDateTime debut;
    private int duree;

    public Seance(int id, int cinCandidat, int cinMoniteur, LocalDateTime debut, int duree) {
        this.id = id;
        this.cinCandidat = cinCandidat;
        this.cinMoniteur = cinMoniteur;
        this.debut = debut;
        this.duree = duree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCinCandidat() {
        return cinCandidat;
    }

    public void setCinCandidat(int cinCandidat) {
        this.cinCandidat = cinCandidat;
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

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", cinCandidat=" + cinCandidat +
                ", cinMoniteur=" + cinMoniteur +
                ", debut=" + debut +
                ", duree=" + duree +
                '}';
    }
}
