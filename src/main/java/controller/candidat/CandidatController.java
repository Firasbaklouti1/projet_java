package controller.candidat;

import controller.document.DocumentController;
import controller.document.DocumentListController;
import entities.Candidat;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.CandidatService;

public class CandidatController implements Initializable {

    @FXML
    private TableView<Candidat> candidatTableView;
    @FXML
    private TableColumn<Candidat, Integer> colCin;
    @FXML
    private TableColumn<Candidat, String> colNom;
    @FXML
    private TableColumn<Candidat, String> colPrenom;
    @FXML
    private TableColumn<Candidat, LocalDate> colDate;
    @FXML
    private TableColumn<Candidat, String> colEmail;
    @FXML
    private TableColumn<Candidat, Void> colActions;
    @FXML
    private Button btnAddCandidat;

    private CandidatService candidatService = new CandidatService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        loadCandidats();
        addButtonToTable();
    }

    private void loadCandidats() {
        List<Candidat> list = candidatService.getAllCandidats();
        candidatTableView.getItems().setAll(list);
    }

    private void addButtonToTable() {
        Callback<TableColumn<Candidat, Void>, TableCell<Candidat, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Candidat, Void> call(final TableColumn<Candidat, Void> param) {
                        return new TableCell<>() {
                            private final Button btnDelete = new Button("Delete");
                            private final Button btnEdit = new Button("Edit");
                            private final Button btnAddDocument = new Button("Ajouter document");
                            private final Button btnShowDocuments = new Button("Afficher documents");
                            private final HBox pane = new HBox(5, btnDelete, btnEdit, btnAddDocument,btnShowDocuments);

                            {
                                btnDelete.setOnAction(e -> {
                                    Candidat candidat = getTableView().getItems().get(getIndex());
                                    handleDeleteCandidat(candidat);
                                });

                                btnEdit.setOnAction(e -> {
                                    Candidat candidat = getTableView().getItems().get(getIndex());
                                    handleEditCandidat(candidat);
                                });
                                btnAddDocument.setOnAction(e -> {
                                    Candidat candidat = getTableView().getItems().get(getIndex());
                                    handleAddDocument(candidat);
                                });
                                btnShowDocuments.setOnAction(e -> {
                                    Candidat candidat = getTableView().getItems().get(getIndex());
                                    handleShowDocuments(candidat);
                                });
                                
                            }

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                setGraphic(empty ? null : pane);
                            }
                        };
                    }
                };
        colActions.setCellFactory(cellFactory);
    }



    private void handleAddDocument(Candidat candidat) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document/Document.fxml"));
            Parent root = loader.load();

            DocumentController controller = loader.getController();
            controller.setNumCin(candidat.getCin());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter document");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void handleShowDocuments(Candidat candidat) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document/DocumentList.fxml"));
            Parent root = loader.load();

            DocumentListController controller = loader.getController();
            controller.loadDocuments(candidat.getCin());

            Stage stage = new Stage();
            stage.setTitle("document du candidat  " + candidat.getNom());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // This shows the full stack trace
        }
    }


    @FXML
    private void handleAddCandidat(ActionEvent event) {
        System.out.println("Add Candidate");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Candidat/CondidatAdd.fxml"));
            Parent root = loader.load();

            CandidatControllerAdd controller = loader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Candidate");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Candidat newCandidat = controller.getCandidatFromForm();
            if(newCandidat != null) {
                candidatService.ajouterCandidat(newCandidat);
                loadCandidats();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteCandidat(Candidat candidat) {
        candidatService.deleteCandidat(candidat.getCin());
        loadCandidats();
    }

    private void handleEditCandidat(Candidat candidat) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Candidat/CondidatUpdate.fxml"));
            Parent root = loader.load();

            CandidatControllerUpdate controller = loader.getController();
            controller.setCandidat(candidat);
            controller.showDetails();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Candidate");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Candidat updatedCandidat = controller.getUpdatedCandidat();
            if(updatedCandidat != null) {
                candidatService.updateCandidat(updatedCandidat);
                loadCandidats();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}