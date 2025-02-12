package controller.vehicule;

import entities.Vehicule;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class VehiculeControllerUpdate {
    private Vehicule vehicule;
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
    private Button confirmerButton;

    @FXML
    private void initialize() {

        confirmerButton.setOnAction(event -> {
            Vehicule newVehicule = getVehiculeFromForm(vehicule.getType(), vehicule.getNumMatricule());
            System.out.println("Véhicule modifie : " + newVehicule);
            Stage stage = (Stage) confirmerButton.getScene().getWindow();
            stage.close();
        });
    }
    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }


    public Vehicule getVehiculeFromForm(String type,int numMatricule) {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
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
    public void afficherDetailsVehicule() {
        // Conversion des nombres en String pour les TextField
        kilometrageTotaleField.setText(String.valueOf(vehicule.getKilometrageTotale()));
        kmAvantEntretienField.setText(String.valueOf(vehicule.getKmAvantEntretien()));

        // Dates directes pour les DatePicker
        miseEnServicePicker.setValue(vehicule.getMiseEnService());
        dateVignettePicker.setValue(vehicule.getDateVignette());
        dateVisiteTechniquePicker.setValue(vehicule.getDateVisiteTechnique());
        dateAssurancePicker.setValue(vehicule.getDateAssurance());
        dateVidangePicker.setValue(vehicule.getDateVidange());

        // Checkbox booléenne
        isDispoCheckBox.setSelected(vehicule.isDispo());
    }
}
