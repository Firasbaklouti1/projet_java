package fxml;


import entities.AutoEcole;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AutoEcoleFxml {

    @FXML
    private TextField nomField;
    @FXML private TextField adresseField;
    @FXML private TextField telephoneField;
    @FXML private TextField emailField;

    @FXML
    public AutoEcole saisirAutoEcole() {
        String nomAutoEcole = nomField.getText();
        String adresseAutoEcole = adresseField.getText();
        String emailAutoEcole = emailField.getText();


        int telAutoEcole = 0;
        try {
            telAutoEcole = Integer.parseInt(telephoneField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Numéro de téléphone invalide.");
        }

        return new AutoEcole(nomAutoEcole, adresseAutoEcole, telAutoEcole, emailAutoEcole);
    }
}
