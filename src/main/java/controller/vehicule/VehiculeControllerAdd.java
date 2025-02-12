package controller.vehicule;

import entities.Vehicule;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class VehiculeControllerAdd {
    @FXML
    private ChoiceBox<String> vehiculeTypeChoiceBox;

    @FXML
    private TextField numMatriculeField;

    @FXML
    private DatePicker miseEnServicePicker;

    @FXML
    private TextField kilometrageTotaleField;

    @FXML
    private TextField kmAvantEntretienField;

    @FXML
    private DatePicker dateVignettePicker;

    @FXML
    private DatePicker dateVisiteTechniquePicker;

    @FXML
    private DatePicker dateAssurancePicker;

    @FXML
    private DatePicker dateVidangePicker;

    @FXML
    private CheckBox isDispoCheckBox;

    @FXML
    private Button addButton;

    @FXML
    private void initialize() {
        addButton.setOnAction(event -> {
            Vehicule vehicule = getVehiculeFromForm();
            System.out.println("Véhicule ajouté : " + vehicule);
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        });
    }

    public Vehicule getVehiculeFromForm() {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
        int numMatricule = Integer.parseInt(numMatriculeField.getText());
        String type = vehiculeTypeChoiceBox.getValue();
        LocalDate miseEnService = miseEnServicePicker.getValue();
        int kilometrageTotale = Integer.parseInt(kilometrageTotaleField.getText());
        int kmAvantEntretien = Integer.parseInt(kmAvantEntretienField.getText());
        LocalDate dateVignette = dateVignettePicker.getValue();
        LocalDate dateVisiteTechnique = dateVisiteTechniquePicker.getValue();
        LocalDate dateAssurance = dateAssurancePicker.getValue();
        LocalDate dateVidange = dateVidangePicker.getValue();
        boolean isDispo = isDispoCheckBox.isSelected();

        return new Vehicule(numMatricule,type, miseEnService, kilometrageTotale, kmAvantEntretien,
                dateVignette, dateVisiteTechnique, dateAssurance, dateVidange, isDispo);
    }
}
