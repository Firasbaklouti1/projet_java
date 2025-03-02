package entities.rdv;

import entities.TypePermis;


public class Code {

    private TypePermis typePermis;
    private int cinCandidat;
    private int cinMoniteur;


    public Code(TypePermis typePermis, int cinCandidat, int cinMoniteur) {
        this.typePermis = typePermis;
        this.cinCandidat = cinCandidat;
        this.cinMoniteur = cinMoniteur;
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

    @Override
    public String toString() {
        return "Code{" +
                "typePermis=" + typePermis +
                ", cinCandidat=" + cinCandidat +
                ", cinMoniteur=" + cinMoniteur +
                '}';
    }
}
