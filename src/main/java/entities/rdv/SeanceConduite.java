package entities.rdv;

import java.time.LocalDateTime;

public class SeanceConduite extends Seance{
    private int numMatricule;
    private int idlieu;

    public SeanceConduite(int id, int cinCandidat, int cinMoniteur, LocalDateTime debut, int duree, int numMatricule, int idlieu) {
        super(id, cinCandidat, cinMoniteur, debut, duree);
        this.numMatricule = numMatricule;
        this.idlieu = idlieu;
    }

    public SeanceConduite(int id, int cinCandidat, int cinMoniteur, LocalDateTime debut, int duree, int numMatricule) {
        super(id, cinCandidat, cinMoniteur, debut, duree);
        this.numMatricule = numMatricule;
    }

    public int getNumMatricule() {
        return numMatricule;
    }

    public void setNumMatricule(int numMatricule) {
        this.numMatricule = numMatricule;
    }

    public int getIdlieu() {
        return idlieu;
    }

    public void setIdlieu(int idlieu) {
        this.idlieu = idlieu;
    }

    @Override
    public String toString() {
        return "SeanceConduite{" +
                super.toString() +
                "numMatricule=" + numMatricule +
                ", idlieu=" + idlieu +
                '}';
    }
}
