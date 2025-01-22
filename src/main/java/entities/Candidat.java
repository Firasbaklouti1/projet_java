package entities;

public class Candidat extends Personne {
    int idCandidat;
    public Candidat(int idCandidat,String nom, String prenom, int age, String email) {
        super(nom, prenom, age, email);
    }

    public int getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(int idCandidat) {
        this.idCandidat = idCandidat;
    }
}
