package controller;


import entities.Candidat;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CandidatController {

    @FXML
    private TableView<Candidat> candidatsTable;
    @FXML
    private TableColumn<Candidat, Integer> cinColumn;
    @FXML
    private TableColumn<Candidat, String> nomColumn;
    @FXML
    private TableColumn<Candidat, String> prenomColumn;
    @FXML
    private TableColumn<Candidat, String> dateColumn;
    @FXML
    private TableColumn<Candidat, String> emailColumn;
    @FXML
    private TableColumn<Candidat, String> telephoneColumn;
    @FXML
    private TableColumn<Candidat, String> typeColumn;
    @FXML
    private TableColumn<Candidat, Integer> nbHeuresColumn;
    @FXML
    private TableColumn<Candidat, Void> actionsColumn;
    @FXML
    private Button addCandidatButton;

    @FXML
    public void initialize() {
        System.out.println("hello");
    }
}

