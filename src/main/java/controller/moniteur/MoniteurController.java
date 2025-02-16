package controller.moniteur;

import controller.document.DocumentController;
import controller.document.DocumentListController;
import entities.Moniteur;

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
import service.MoniteurService;

import static java.lang.Thread.sleep;

public class MoniteurController implements Initializable {
   
    @FXML
    private TableColumn<Moniteur,Integer >  colCin;
    

    @FXML
    private TableView<Moniteur> moniteurTableView;

    @FXML
    private TableColumn<Moniteur, String> colNom;

    @FXML
    private TableColumn<Moniteur, String> colPrenom;

    @FXML
    private TableColumn<Moniteur, LocalDate> colDate;

    @FXML
    private TableColumn<Moniteur, String> colEmail;

    @FXML
    private TableColumn<Moniteur, Boolean> colIsDispo;

    // This column will host the 3 action buttons per row
    @FXML
    private TableColumn<Moniteur, Void> colActions;
    @FXML
    private TableColumn<Moniteur,Double>colSalaire;

    @FXML
    private Button btnAddMoniteur;

    // Instance of the service that manages Vehicule data
    private MoniteurService moniteurService = new MoniteurService();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set up cell value factories for each column
        colCin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSalaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        colIsDispo.setCellValueFactory(new PropertyValueFactory<>("dispo"));

        // Set a default value for the ChoiceBox
        loadMoniteurs();

        // Add action buttons to the "Actions" column
        addButtonToTable();
    }

    private void loadMoniteurs() {
        List<Moniteur>list= moniteurService.getAllMoniteurs();

        moniteurTableView.getItems().setAll(list);
    }

    private void addButtonToTable() {
        Callback<TableColumn<Moniteur, Void>, TableCell<Moniteur, Void>> cellFactory =
                new Callback<TableColumn<Moniteur, Void>, TableCell<Moniteur, Void>>() {
                    @Override
                    public TableCell<Moniteur, Void> call(final TableColumn<Moniteur, Void> param) {
                        final TableCell<Moniteur, Void> cell = new TableCell<Moniteur, Void>() {

                            private final Button btnDelete = new Button("Effacer");
                            private final Button btnEdit = new Button("Modifier");
                            private final Button btnAddDocument = new Button("Ajouter Maintenance");
                            // Nouveau bouton
                            private final Button btnShowDocuments = new Button("Afficher Maintenances");

                            private final HBox pane = new HBox(5, btnDelete, btnEdit, btnAddDocument, btnShowDocuments);
                            {
                                // Button action for deletion
                                btnDelete.setOnAction((ActionEvent event) -> {
                                    Moniteur moniteur = getTableView().getItems().get(getIndex());
                                    handleDeleteMoniteur(moniteur);
                                });

                                // Button action for editing
                                btnEdit.setOnAction((ActionEvent event) -> {
                                    Moniteur moniteur = getTableView().getItems().get(getIndex());
                                    handleEditMoniteur(moniteur);
                                });

                                // Button action for adding maintenance
                                btnAddDocument.setOnAction((ActionEvent event) -> {
                                    Moniteur moniteur = getTableView().getItems().get(getIndex());
                                    handleAddDocument(moniteur);
                                });
                                btnShowDocuments.setOnAction((ActionEvent event) -> {
                                    Moniteur moniteur = getTableView().getItems().get(getIndex());
                                    handleShowDocuments(moniteur);
                                });
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(pane);
                                }
                            }
                        };
                        return cell;
                    }
                };
        colActions.setCellFactory(cellFactory);
    }

    @FXML
    private void handleAddMoniteur(ActionEvent event) {
        System.out.println("Add vehicule");
        try {
            // Charger le fichier FXML de l'ajout de véhicule
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Moniteur/MoniteurAdd.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de l'ajout de véhicule
            MoniteurControllerAdd moniteurControllerAdd = loader.getController();

            // Créer une nouvelle fenêtre pour l'ajout de véhicule
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Bloque l'accès à la fenêtre principale
            stage.setTitle("Ajouter un moniteur");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Attendre la fermeture de la fenêtre

            // Récupérer l'objet Véhicule
            Moniteur newMoniteur = moniteurControllerAdd.getMoniteurFromForm();
            if (newMoniteur != null) {
                System.out.println("moniteur ajouté dans MainController : " + newMoniteur);
                // Tu peux maintenant ajouter ce véhicule à une liste, une base de données, etc.
            }
            else {
                System.out.println("error");
            }
            moniteurService.addMoniteur(newMoniteur);
            loadMoniteurs();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void handleDeleteMoniteur(Moniteur moniteur) {
        moniteurService.deleteMoniteur(moniteur.getCin());
        loadMoniteurs();
    }

    private void handleEditMoniteur(Moniteur moniteur) {

        System.out.println("modifier moniteur");
        try {
            // Charger le fichier FXML de l'ajout de véhicule
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Moniteur/MoniteurUpdate.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de l'ajout de véhicule
            MoniteurControllerUpdate moniteurControllerUpdate = loader.getController();
            moniteurControllerUpdate.setMoniteur(moniteur);
            moniteurControllerUpdate.afficherDetailsMoniteur();

            // Créer une nouvelle fenêtre pour l'ajout de véhicule
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Bloque l'accès à la fenêtre principale
            stage.setTitle("modifier le moniteur");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Attendre la fermeture de la fenêtre

            // Récupérer l'objet Véhicule
            Moniteur newMoniteur = moniteurControllerUpdate.getMoniteurFromForm(moniteur.getCin());
            if (newMoniteur != null) {
                System.out.println("Véhicule modifie dans MainController : " + newMoniteur);
                // Tu peux maintenant ajouter ce véhicule à une liste, une base de données, etc.
            }
            else {
                System.out.println("error");
            }
            moniteurService.updateMoniteur(newMoniteur);
            loadMoniteurs();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void handleAddDocument(Moniteur moniteur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document/Document.fxml"));
            Parent root = loader.load();

            DocumentController controller = loader.getController();
            controller.setNumCin(moniteur.getCin());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter document");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void handleShowDocuments(Moniteur moniteur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document/DocumentList.fxml"));
            Parent root = loader.load();

            DocumentListController controller = loader.getController();
            controller.loadDocuments(moniteur.getCin());

            Stage stage = new Stage();
            stage.setTitle("documents du moniteur " + moniteur.getNom());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // This shows the full stack trace
        }
    }

}
