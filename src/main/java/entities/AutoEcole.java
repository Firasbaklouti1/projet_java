package entities;

public class AutoEcole {
    private String nom;
    private String adresse;
    private int numTelephone;
    private String email;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNumTelephone() {
        return numTelephone;
    }

    public void setNumTelephone(int numTelephone) {
        this.numTelephone = numTelephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AutoEcole(String nom, String adresse, int numTelephone, String email) {
        this.nom = nom;
        this.adresse = adresse;
        this.numTelephone = numTelephone;
        this.email = email;
    }

}
