package entities.rdv;

import entities.TypePermis;

public class Conduite {
    private TypePermis typePermis;
    private int cinCandidat;
    private int cinMoniteur;
    private int numMatricule;
    private int nbHeures;

    public Conduite(TypePermis typePermis, int cinCandidat, int cinMoniteur, int numMatricule) {
        this.typePermis = typePermis;
        this.cinCandidat = cinCandidat;
        this.cinMoniteur = cinMoniteur;
        this.numMatricule = numMatricule;
        nbHeures=0;
    }

    public Conduite(TypePermis typePermis, int cinCandidat, int cinMoniteur, int numMatricule, int nbHeures) {
        this.typePermis = typePermis;
        this.cinCandidat = cinCandidat;
        this.cinMoniteur = cinMoniteur;
        this.numMatricule = numMatricule;
        this.nbHeures = nbHeures;
    }

    public TypePermis getTypePermis() {
        return typePermis;
    }

    public void setTypePermis(TypePermis typePermis) {
        this.typePermis = typePermis;
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

    public int getNumMatricule() {
        return numMatricule;
    }

    public void setNumMatricule(int numMatricule) {
        this.numMatricule = numMatricule;
    }

    public int getNbHeures() {
        return nbHeures;
    }
    public void actualiserNbHeures(int nbHeures) {
        this.nbHeures -= nbHeures;
    }



    @Override
    public String toString() {
        return "Conduite{" +
                "typePermis=" + typePermis +
                ", cinCandidat=" + cinCandidat +
                ", cinMoniteur=" + cinMoniteur +
                ", numMatricule=" + numMatricule +
                ", nbHeures=" + nbHeures +
                '}';
    }
}
