package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Candidat extends Personne {
    public Candidat(int cin, String nom, String prenom, LocalDate date, String email) {
        super(cin, nom, prenom, date, email);
    }
}
