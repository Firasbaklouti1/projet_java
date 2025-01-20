package fxml;

import entities.AutoEcole;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AutoEcoleFxml {
    @FXML
    private TextField nomAutoEcole;
    @FXML
    private TextField adresseAutoEcole;
    @FXML
    private TextField telAutoEcole;
    @FXML
    private TextField emailAutoEcole;

    @FXML
    public static AutoEcole saisirAutoEcole() {
        String nomAutoEcole =nomAutoEcole.getText();
        String adresseAutoEcole= adresseAutoEcole.getText();
        int telAutoEcole=telAutoEcole.getText();
        String emailAutoEcole=emailAutoEcole.getText();

        return new AutoEcole(nomAutoEcole,adresseAutoEcole,telAutoEcole,emailAutoEcole);
    }



}
