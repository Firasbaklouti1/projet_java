package controller;

import entities.rdv.Code;
import entities.rdv.SeanceCode;
import entities.rdv.SeanceConduite;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import service.rdv.SeanceCodeService;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class GestionRdvCodeController implements Initializable {


    private Code code;
    @FXML
    private TableView<SeanceCode> rdvTableView;
    @FXML private TableColumn<SeanceCode, LocalDateTime> debutCol;
    @FXML private TableColumn<SeanceCode, Integer> dureeCol;

    private final SeanceCodeService seanceCodeService = new SeanceCodeService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureColumns();
        addActionButtons();
    }
    public void setCode(Code code){
        this.code = code;
        loadData();
    }

    private void configureColumns() {
        debutCol.setCellValueFactory(new PropertyValueFactory<>("debut"));
        debutCol.setCellFactory(column -> new TableCell<SeanceCode, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            }
        });

        dureeCol.setCellValueFactory(new PropertyValueFactory<>("duree"));
    }

    private void addActionButtons() {
        TableColumn<SeanceCode, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(param -> new TableCell<SeanceCode, Void>() {
            private final Button deleteBtn = new Button("Delete");
            private final HBox buttons = new HBox( deleteBtn);

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
        rdvTableView.getItems().setAll(seanceCodeService.getSeancesByCandidat(code.getCinCandidat()));
    }



    private void handleDelete(SeanceCode seance) {
        seanceCodeService.delete(seance.getId());
        loadData();
    }

    public void handleAddRdv(javafx.event.ActionEvent actionEvent) {
        CalendrierCode dialog = new CalendrierCode(
                LocalDate.now(),
                LocalDate.now().plusWeeks(1),
                code.getCinMoniteur(),
                code.getCinCandidat()
        );

        Optional<SeanceCode> result = dialog.showAndWait();


        result.ifPresent(seance -> {
            System.out.println(seance);
            seanceCodeService.ajouter(seance);
            loadData();
        });
    }
}