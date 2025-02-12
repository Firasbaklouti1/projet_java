package controller.vehicule;

import entities.Maintenance;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import service.AutoEcoleService;
import service.MaintenanceService;

import java.awt.*;
import java.io.File;
import java.sql.Blob;
import java.util.List;

public class MaintenanceListController {

    @FXML private TableView<Maintenance> maintenanceTable;
    @FXML private TableColumn<Maintenance, String> dateColumn;
    @FXML private TableColumn<Maintenance, String> descriptionColumn;
    @FXML private TableColumn<Maintenance, Double> priceColumn;
    @FXML private TableColumn<Maintenance, Blob> factureColumn;

    private  MaintenanceService maintenanceService = new MaintenanceService();

    @FXML
    public void initialize() {
        // Configuration des colonnes
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Ajout du bouton pour la colonne Facture
        factureColumn.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Voir");

            @Override
            protected void updateItem(Blob facture, boolean empty) {
                super.updateItem(facture, empty);
                if (empty || facture == null) {
                    setGraphic(null);
                } else {
                    btn.setOnAction(e -> handleViewFacture(getTableRow().getItem()));
                    setGraphic(btn);
                }
            }
        });
    }

    public void loadMaintenances(int numMatricule) { // Changed to String if numMatricule contains letters
        List<Maintenance> maintenances = maintenanceService.getMaintenancesByVehicule(numMatricule);
        maintenanceTable.setItems(FXCollections.observableArrayList(maintenances));
    }



    private void handleViewFacture(Maintenance maintenance) {
        AutoEcoleService autoEcoleService = new AutoEcoleService();



        try {
            File pdf = maintenanceService.convertBlobToPdf(maintenance.getFacture(),autoEcoleService.getPiedDePage(),autoEcoleService.enteteAutoEcole());
            Desktop.getDesktop().open(pdf);
        } catch (Exception e) {
            try {
                Image image = maintenanceService.convertBlobToImage(maintenance.getFacture());
                if (image != null) {
                    // Afficher l'image
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(600);
                    imageView.setPreserveRatio(true);

                    StackPane root = new StackPane(imageView);
                    Scene scene = new Scene(root, 700, 500);

                    Stage stage = new Stage();
                    stage.setTitle("Facture");
                    stage.setScene(scene);
                    stage.show();
                } else {
                    System.out.println("Erreur de chargement de la facture");
                }
            }catch (Exception e1) {
                System.out.println("Erreur de chargement de la facture");
            }
        }
    }


}