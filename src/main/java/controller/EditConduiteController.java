package controller;

import entities.rdv.Conduite;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import service.rdv.ConduiteService;

public class EditConduiteController {
    @FXML
    private TextField numMatriculeField;
    @FXML private TextField nbHeuresField;
    private Conduite conduite;

    public void setConduite(Conduite c) {
        this.conduite = c;
        numMatriculeField.setText(String.valueOf(c.getNumMatricule()));
        nbHeuresField.setText(String.valueOf(c.getNbHeures()));
    }

    @FXML
    private void handleUpdate() {
        conduite.setNumMatricule(Integer.parseInt(numMatriculeField.getText()));
        conduite.actualiserNbHeures(Integer.parseInt(nbHeuresField.getText()));
        new ConduiteService().updateConduite(conduite);
        //closeWindow();
    }
}
