package controller;

import dao.rdv.LieuDAO;
import entities.rdv.Conduite;
import entities.rdv.Lieu;
import entities.rdv.SeanceConduite;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import service.rdv.LieuService;
import service.rdv.SeanceConduiteService;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;



import java.util.concurrent.CountDownLatch;

public class GestionRdvConduiteController implements Initializable {

    @FXML private TableView<SeanceConduite> rdvTableView;
    @FXML private TableColumn<SeanceConduite, LocalDateTime> debutCol;
    @FXML private TableColumn<SeanceConduite, Integer> dureeCol;
    @FXML private TableColumn<SeanceConduite, Integer> matriculeCol;
    @FXML private TableColumn<SeanceConduite, Integer> lieuCol;

    private final SeanceConduiteService seanceConduiteService = new SeanceConduiteService();
    private Conduite conduite;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureColumns();

    }
    public void setConduite(Conduite conduite){
        this.conduite = conduite;
        loadData();
    }

    private void configureColumns() {
        debutCol.setCellValueFactory(new PropertyValueFactory<>("debut"));
        debutCol.setCellFactory(column -> new TableCell<SeanceConduite, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(empty ? null : item.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                }
            }
        });

        dureeCol.setCellValueFactory(new PropertyValueFactory<>("duree"));
        matriculeCol.setCellValueFactory(new PropertyValueFactory<>("numMatricule"));
        lieuCol.setCellValueFactory(new PropertyValueFactory<>("idlieu"));
        addActionButtons();
    }

    private void addActionButtons() {
        TableColumn<SeanceConduite, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(param -> new TableCell<SeanceConduite, Void>() {
            private final Button deleteBtn = new Button("Delete");
            private final HBox buttons = new HBox(deleteBtn);

            {
                buttons.setSpacing(5);
                deleteBtn.setOnAction(e -> handleDelete(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : buttons);
            }
        });
        rdvTableView.getColumns().add(actionCol);
    }



    private void loadData() {
        List<SeanceConduite>list=seanceConduiteService.getSeancesByCandidat(conduite.getCinCandidat());
        rdvTableView.getItems().setAll(list);
    }


    private void handleDelete(SeanceConduite seance) {
        seanceConduiteService.deleate(seance.getId());
        loadData();
    }



    public void handleAddRdv(javafx.event.ActionEvent actionEvent) {
        CalendrierConduite dialog = new CalendrierConduite(
                LocalDate.now(),
                LocalDate.now().plusWeeks(1),
                conduite.getCinMoniteur(),
                conduite.getNumMatricule(),
                conduite.getCinCandidat()
        );

        Optional<SeanceConduite> result = dialog.showAndWait();
        

        result.ifPresent(seance -> {
            LieuService lieuService = new LieuService();
            int idLieu= lieuService.ajouter(traitementDeLieu());
            seance.setIdlieu(idLieu);
            seanceConduiteService.ajouter(seance);
            loadData();
        });
    }

    private Lieu traitementDeLieu() {
        try {
            return MapDialog.showMapDialog();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null; // Handle error appropriately
        }
    }
}