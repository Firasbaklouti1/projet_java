package fxml;

import entities.AutoEcole;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AutoEcoleFxml {

    @FXML private TextField nomField;
    @FXML private TextField adresseField;
    @FXML private TextField telephoneField;
    @FXML private TextField emailField;

    @FXML
    public AutoEcole saisirAutoEcole() {
        // Récupérer les valeurs des champs de texte
        String nomAutoEcole = nomField.getText();
        String adresseAutoEcole = adresseField.getText();
        String emailAutoEcole = emailField.getText();

        // Convertir le texte du téléphone en entier
        int telAutoEcole = 0;
        try {
            telAutoEcole = Integer.parseInt(telephoneField.getText());
        } catch (NumberFormatException e) {
            // Gérer l'exception si le numéro de téléphone n'est pas valide
            System.out.println("Numéro de téléphone invalide.");
        }

        // Créer un objet AutoEcole et le retourner
        return new AutoEcole(nomAutoEcole, adresseAutoEcole, telAutoEcole, emailAutoEcole);
    }
}
