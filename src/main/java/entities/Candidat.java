package entities;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Candidat  {
    private int cin;
    private String nom;
    private String prenom;
    private LocalDate date;
    private String email;
    private int numTelephone;
    private TypePermis type;
    private int nbHeures;
    private List<Document>documentsScannes;

    public Candidat(int cin, String nom, String prenom, LocalDate date, String email, int numTelephone, TypePermis type, int nbHeures) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.date = date;
        this.email = email;
        this.numTelephone = numTelephone;
        this.type = type;
        this.nbHeures = nbHeures;
        documentsScannes = new ArrayList<>();
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumTelephone(int numTelephone) {
        this.numTelephone = numTelephone;
    }

    public void setType(TypePermis type) {
        this.type = type;
    }

    public void setNbHeures(int nbHeures) {
        this.nbHeures = nbHeures;
    }

    public int getCin() {
        return cin;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getEmail() {
        return email;
    }

    public int getNumTelephone() {
        return numTelephone;
    }

    public TypePermis getType() {
        return type;
    }

    public int getNbHeures() {
        return nbHeures;
    }

    public List<Document> getDocumentsScannes() {
        return documentsScannes;
    }
}
