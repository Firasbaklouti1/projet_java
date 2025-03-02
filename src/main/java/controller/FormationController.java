package controller;


import entities.TypePermis;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.rdv.Code;
import entities.rdv.Conduite;
import javafx.beans.property.SimpleStringProperty;
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
import service.rdv.CodeService;
import service.rdv.ConduiteService;



public class FormationController implements Initializable {

    @FXML private ChoiceBox<String> entityTypeChoiceBox;
    @FXML private TableView<Object> entityTableView;

    private final CodeService codeService = new CodeService();
    private final ConduiteService conduiteService = new ConduiteService();
    private boolean showingCode = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        entityTypeChoiceBox.getItems().addAll("Code", "Conduite");
        entityTypeChoiceBox.setValue("Code");

        entityTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    showingCode = newVal.equals("Code");
                    configureTableColumns();
                    loadEntities();
                }
        );

        configureTableColumns();
        loadEntities();
    }

    private void addButtonToTable() {
        TableColumn<Object, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(param -> new TableCell<Object, Void>() {
            private final Button deleteButton = new Button("Delete");
            private final Button gestionRdv = new Button("gestionRdv");
            private final HBox buttons = new HBox( deleteButton, gestionRdv);

            {
                buttons.setSpacing(5);

                deleteButton.setOnAction(event -> {
                    Object entity = getTableView().getItems().get(getIndex());
                    handleDelete(entity);
                });
                gestionRdv.setOnAction(event -> {
                    Object entity = getTableView().getItems().get(getIndex());
                    handleGesionRdv(entity);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttons);
                }
            }
        });

        entityTableView.getColumns().add(actionCol);
    }


    private void configureTableColumns() {
        entityTableView.getColumns().clear();

        // Common columns
        TableColumn<Object, TypePermis> typeCol = new TableColumn<>("Type Permis");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("typePermis"));

        // Configure cell factory for enum display
        typeCol.setCellFactory(column -> new TableCell<Object, TypePermis>() {
            @Override
            protected void updateItem(TypePermis item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item != null ? item.name() : null);
            }
        });

        if (showingCode) {
            // Columns for Code
            TableColumn<Object, String> candidatNameCol = new TableColumn<>("CandidatName");
            candidatNameCol.setCellValueFactory(cellData -> {
                Code code = (Code) cellData.getValue();
                String name = codeService.getNameOfCandidat(code.getCinCandidat());
                return new SimpleStringProperty(name);
            });

            TableColumn<Object, String> moniteurNameCol = new TableColumn<>("MoniteurName");
            moniteurNameCol.setCellValueFactory(cellData -> {
                Code code = (Code) cellData.getValue();
                String name = codeService.getNameOfMoniteur(code.getCinMoniteur());
                return new SimpleStringProperty(name);
            });

            entityTableView.getColumns().addAll(typeCol, candidatNameCol, moniteurNameCol);
        } else {
            // Columns for Conduite
            TableColumn<Object, String> candidatNameCol = new TableColumn<>("CandidatName");
            candidatNameCol.setCellValueFactory(cellData -> {
                Conduite conduite = (Conduite) cellData.getValue();
                String name = conduiteService.getNameOfCandidat(conduite.getCinCandidat());
                return new SimpleStringProperty(name);
            });
            TableColumn<Object, String> moniteurNameCol = new TableColumn<>("MoniteurName");
            moniteurNameCol.setCellValueFactory(cellData -> {
                Conduite conduite = (Conduite) cellData.getValue();
                String name = conduiteService.getNameOfMoniteur(conduite.getCinMoniteur());
                return new SimpleStringProperty(name);
            });

            TableColumn<Object, Integer> matriculeCol = new TableColumn<>("Matricule");
            matriculeCol.setCellValueFactory(new PropertyValueFactory<>("numMatricule"));

            TableColumn<Object, Integer> heuresCol = new TableColumn<>("Heures");
            heuresCol.setCellValueFactory(new PropertyValueFactory<>("nbHeures"));

            entityTableView.getColumns().addAll(typeCol, candidatNameCol,moniteurNameCol, matriculeCol, heuresCol);
        }

        // Re-add the action buttons column
        addButtonToTable();
    }



    private void loadEntities() {
        if (showingCode) {
            List<Code> codes = codeService.getAllCodes();
            entityTableView.getItems().setAll(codes);
        } else {
            List<Conduite> conduites = conduiteService.getAllConduites();
            entityTableView.getItems().setAll(conduites);
        }
    }

    public void handleAddEntity(ActionEvent actionEvent) {
        try {
            String fxmlFile = showingCode ? "AddCode.fxml" : "AddConduite.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/formation/" + fxmlFile));

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add " + (showingCode ? "Code" : "Conduite"));
            stage.setScene(new Scene(root));

            stage.showAndWait();
            loadEntities();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDelete(Object entity) {
        if (entity instanceof Code) {
            codeService.deleteCode(((Code) entity).getCinCandidat());
        } else {
            conduiteService.deleteConduite(((Conduite) entity).getCinCandidat());
        }
        loadEntities();
    }



    private void handleGesionRdv(Object entity) {
        try {
            FXMLLoader loader;
            if(showingCode){
                loader = new FXMLLoader(getClass().getResource("/fxml/formation/GestionRdvCode.fxml"));
            }
            else {
                loader = new FXMLLoader(getClass().getResource("/fxml/formation/GestionRdvConduite.fxml"));
            }
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Gestion RDV");
            stage.setScene(new Scene(root));

            // Récupérer le contrôleur après le chargement
            if (showingCode) {
                GestionRdvCodeController controller = loader.getController();
                controller.setCode((Code) entity);
            } else {
                GestionRdvConduiteController controller = loader.getController();
                controller.setConduite((Conduite) entity);
            }

            stage.showAndWait();
            loadEntities();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}