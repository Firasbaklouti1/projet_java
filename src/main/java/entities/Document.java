package entities;



import java.sql.Blob;
import java.time.LocalDate;

public class Document {
    private int id;
    private int candidatId;
    private String nom;
    private String type;
    private LocalDate dateAjout;
    private Blob fichier;

    public Document() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(int candidatId) {
        this.candidatId = candidatId;
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

    public Document(int id, int candidatId, String nom, String type, LocalDate dateAjout, Blob fichier) {
        this.id = id;
        this.candidatId = candidatId;
        this.nom = nom;
        this.type = type;
        this.dateAjout = dateAjout;
        this.fichier = fichier;
    }
}
