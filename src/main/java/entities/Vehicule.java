package entities;

import java.time.LocalDate;

public class Vehicule {
    private int numMatricule;
    private String type;
    private LocalDate miseEnService;
    private int KilometrageTotale;
    private int kmAvantEntretien;
    private LocalDate dateVignette;
    private LocalDate dateVisiteTechnique;
    private LocalDate dateAssurance;
    private LocalDate dateVidange;
    private boolean isDispo;

    @Override
    public String toString() {
        return "Vehicule{" +
                "numMatricule=" + numMatricule +
                ", type='" + type + '\'' +
                ", miseEnService=" + miseEnService +
                ", KilometrageTotale=" + KilometrageTotale +
                ", kmAvantEntretien=" + kmAvantEntretien +
                ", dateVignette=" + dateVignette +
                ", dateVisiteTechnique=" + dateVisiteTechnique +
                ", dateAssurance=" + dateAssurance +
                ", dateVidange=" + dateVidange +
                ", isDispo=" + isDispo +
                '}';
    }

    public int getNumMatricule() {
        return numMatricule;
    }

    public void setNumMatricule(int numMatricule) {
        this.numMatricule = numMatricule;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getMiseEnService() {
        return miseEnService;
    }

    public void setMiseEnService(LocalDate miseEnService) {
        this.miseEnService = miseEnService;
    }

    public int getKilometrageTotale() {
        return KilometrageTotale;
    }

    public void setKilometrageTotale(int kilometrageTotale) {
        KilometrageTotale = kilometrageTotale;
    }

    public int getKmAvantEntretien() {
        return kmAvantEntretien;
    }

    public void setKmAvantEntretien(int kmAvantEntretien) {
        this.kmAvantEntretien = kmAvantEntretien;
    }

    public LocalDate getDateVignette() {
        return dateVignette;
    }

    public void setDateVignette(LocalDate dateVignette) {
        this.dateVignette = dateVignette;
    }

    public LocalDate getDateVisiteTechnique() {
        return dateVisiteTechnique;
    }

    public void setDateVisiteTechnique(LocalDate dateVisiteTechnique) {
        this.dateVisiteTechnique = dateVisiteTechnique;
    }

    public LocalDate getDateAssurance() {
        return dateAssurance;
    }

    public void setDateAssurance(LocalDate dateAssurance) {
        this.dateAssurance = dateAssurance;
    }

    public LocalDate getDateVidange() {
        return dateVidange;
    }

    public void setDateVidange(LocalDate dateVidange) {
        this.dateVidange = dateVidange;
    }

    public boolean isDispo() {
        return isDispo;
    }

    public void setDispo(boolean dispo) {
        isDispo = dispo;
    }

    public Vehicule(int numMatricule, String type, LocalDate miseEnService, int kilometrageTotale, int kmAvantEntretien, LocalDate dateVignette, LocalDate dateVisiteTechnique, LocalDate dateAssurance, LocalDate dateVidange, boolean isDispo) {
        this.numMatricule = numMatricule;
        this.type = type;
        this.miseEnService = miseEnService;
        KilometrageTotale = kilometrageTotale;
        this.kmAvantEntretien = kmAvantEntretien;
        this.dateVignette = dateVignette;
        this.dateVisiteTechnique = dateVisiteTechnique;
        this.dateAssurance = dateAssurance;
        this.dateVidange = dateVidange;
        this.isDispo = isDispo;
    }
}
