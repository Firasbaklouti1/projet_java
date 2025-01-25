package entities;

import java.time.LocalDate;

public class Document {
    private String nom;
    private String chemin;
    private LocalDate dateAjout;

    public Document(String nom, String chemin, LocalDate dateAjout) {
        this.nom = nom;
        this.chemin = chemin;
        this.dateAjout = dateAjout;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    @Override
    public String toString() {
        return "Document{" +
                "nom='" + nom + '\'' +
                ", chemin='" + chemin + '\'' +
                ", dateAjout=" + dateAjout +
                '}';
    }
}

