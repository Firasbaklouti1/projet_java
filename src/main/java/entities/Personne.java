package entities;

import java.time.LocalDate;

public class Personne {
    private String nom;
    private String prenom;
    private LocalDate date;
    private String email;

    public Personne(String nom, String prenom, LocalDate date, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.date = date;
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
