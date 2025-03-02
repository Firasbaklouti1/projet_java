package entities.rdv;

import java.time.LocalDateTime;

public class SeanceCode extends Seance{
    public SeanceCode(int id, int cinCandidat, int cinMoniteur, LocalDateTime debut, int duree) {
        super(id, cinCandidat, cinMoniteur, debut, duree);
    }
}
