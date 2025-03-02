package controller;

import entities.rdv.DisponabiliteMoniteur;
import entities.rdv.SeanceCode;
import entities.rdv.SeanceConduite;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import service.rdv.DisponabiliteMoniteurService;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CalendrierCode extends Dialog<SeanceCode> {
    private LocalDate startDate;
    private  LocalDate endDate;
    private  int cinMoniteur;
    private int cinCandidat;

    public CalendrierCode(LocalDate startDate, LocalDate endDate, int cinMoniteur, int cinCandidat) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.cinMoniteur = cinMoniteur;
        this.cinCandidat = cinCandidat;

        buildUI();
    }

    private void buildUI() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        // En-têtes des colonnes (jours)
        LocalDate currentDate = startDate;
        int col = 1;
        while (!currentDate.isAfter(endDate)) {
            grid.add(new Label(currentDate.toString()), col++, 0);
            currentDate = currentDate.plusDays(1);
        }

        // Créneaux horaires
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        Duration slotDuration = Duration.ofMinutes(30);

        int row = 1;
        LocalTime currentTime = startTime;
        while (currentTime.isBefore(endTime)) {
            grid.add(new Label(currentTime.toString()), 0, row);

            LocalDate date = startDate;
            int column = 1;
            while (!date.isAfter(endDate)) {
                Rectangle timeSlot = createTimeSlot(date, currentTime);
                grid.add(timeSlot, column++, row);
                date = date.plusDays(1);
            }

            currentTime = currentTime.plus(slotDuration);
            row++;
        }

        this.getDialogPane().setContent(grid);
        this.setResultConverter(dialogButton -> null);
    }

    private Rectangle createTimeSlot(LocalDate date, LocalTime time) {
        Rectangle rect = new Rectangle(80, 20);

        // Vérifier la disponibilité
        boolean moniteurDispo = disponibiliteMoniteur(cinMoniteur, date, time);

        if (moniteurDispo) {
            rect.setFill(Color.GREEN);
            rect.setOnMouseClicked(e -> handleTimeSlotSelection(date, time));
        } else {
            rect.setFill(Color.RED);
        }

        return rect;
    }

    private void handleTimeSlotSelection(LocalDate date, LocalTime time) {
        LocalDateTime debut = LocalDateTime.of(date, time);

        // Créer la séance
        SeanceCode nouvelleSeance = new SeanceCode(
                0, // ID auto-généré
                cinCandidat,
                cinMoniteur,
                debut,
                60
        );

        this.setResult(nouvelleSeance);
    }

    // Méthodes à implémenter
    private boolean disponibiliteMoniteur(int cinMoniteur, LocalDate date, LocalTime time) {
        DisponabiliteMoniteurService service=new DisponabiliteMoniteurService();
        List<DisponabiliteMoniteur> disponabiliteMoniteurs=service.getDisponabiliteMoniteurByCin(cinMoniteur);
        LocalDateTime rdv = LocalDateTime.of(date, time);

        // Return true if none of the intervals conflict with the requested time.
        return disponabiliteMoniteurs.stream()
                .noneMatch(d ->
                        !rdv.isBefore(d.getDebut()) && rdv.isBefore(d.getFin())
                );
    }

}
