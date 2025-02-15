package entities;

import java.time.LocalDate;

public class Moniteur extends Personne{
    private double salaire;
    private Boolean dispo;

    public Moniteur(int cin, String nom, String prenom, LocalDate date, String email, double salaire, Boolean dispo) {
        super(cin, nom, prenom, date, email);
        this.salaire = salaire;
        this.dispo = dispo;
    }
    public boolean isDispo() {
        return dispo;
    }

    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public Boolean getDispo() {
        return dispo;
    }

    public void setDispo(Boolean dispo) {
        this.dispo = dispo;
    }

    @Override
    public String toString() {
        return "Moniteur{" +
                "salaire=" + salaire +
                ", dispo=" + dispo +
                '}';
    }
}