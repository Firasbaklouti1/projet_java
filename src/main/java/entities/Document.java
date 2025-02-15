package entities;



import java.sql.Blob;
import java.time.LocalDate;

public class Document {
    private int id;
    private int cin;
    private String nom;
    private String type;
    private LocalDate dateAjout;
    private Blob fichier;

    public Document(int id, int cin, String nom, String type, LocalDate dateAjout, Blob fichier) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.type = type;
        this.dateAjout = dateAjout;
        this.fichier = fichier;
    }

    public Document() {
    }

    public Document(int cin, String nom, String type, LocalDate dateAjout, Blob fichier) {
        this.cin = cin;
        this.nom = nom;
        this.type = type;
        this.dateAjout = dateAjout;
        this.fichier = fichier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Blob getFichier() {
        return fichier;
    }

    public void setFichier(Blob fichier) {
        this.fichier = fichier;
    }


}
