package controller.vehicule;

import entities.Vehicule;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import service.VehiculeService;

import static java.lang.Thread.sleep;

public class VehiculeControllerMain implements Initializable {
    @FXML
    private Button notificationButton;

    @FXML
    private Label notificationBadge;
    @FXML
    private TableColumn<Vehicule,String> colType;
    @FXML
    private ChoiceBox<String> filterChoiceBox;

    @FXML
    private TableView<Vehicule> vehiculeTableView;

    @FXML
    private TableColumn<Vehicule, Integer> colNumMatricule;

    @FXML
    private TableColumn<Vehicule, LocalDate> colMiseEnService;

    @FXML
    private TableColumn<Vehicule, Integer> colKilometrageTotale;

    @FXML
    private TableColumn<Vehicule, Integer> colKmAvantEntretien;

    @FXML
    private TableColumn<Vehicule, LocalDate> colDateVignette;

    @FXML
    private TableColumn<Vehicule, LocalDate> colDateVisiteTechnique;

    @FXML
    private TableColumn<Vehicule, LocalDate> colDateAssurance;

    @FXML
    private TableColumn<Vehicule, LocalDate> colDateVidange;

    @FXML
    private TableColumn<Vehicule, Boolean> colIsDispo;

    // This column will host the 3 action buttons per row
    @FXML
    private TableColumn<Vehicule, Void> colActions;

    @FXML
    private Button btnAddVehicule;

    // Instance of the service that manages Vehicule data
    private VehiculeService vehiculeService = new VehiculeService();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set up cell value factories for each column
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colNumMatricule.setCellValueFactory(new PropertyValueFactory<>("numMatricule"));
        colMiseEnService.setCellValueFactory(new PropertyValueFactory<>("miseEnService"));
        colKilometrageTotale.setCellValueFactory(new PropertyValueFactory<>("kilometrageTotale"));
        colKmAvantEntretien.setCellValueFactory(new PropertyValueFactory<>("kmAvantEntretien"));
        colDateVignette.setCellValueFactory(new PropertyValueFactory<>("dateVignette"));
        colDateVisiteTechnique.setCellValueFactory(new PropertyValueFactory<>("dateVisiteTechnique"));
        colDateAssurance.setCellValueFactory(new PropertyValueFactory<>("dateAssurance"));
        colDateVidange.setCellValueFactory(new PropertyValueFactory<>("dateVidange"));
        colIsDispo.setCellValueFactory(new PropertyValueFactory<>("dispo"));

        // Set a default value for the ChoiceBox
        filterChoiceBox.setValue("all vehicule");

        // Initially load vehicules with the default filter
        loadVehicules(filterChoiceBox.getValue());

        // Add a listener to the ChoiceBox to detect changes
        filterChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Update your variable (if needed) and reload the table data
                String type = newValue;
                loadVehicules(type);
            }
        });

        // Add action buttons to the "Actions" column
        addButtonToTable();
        checkAndNotify();
    }

    private void loadVehicules(String type) {
        List<Vehicule>list=vehiculeService.getAllVehicules();
        switch (type){
            case "moto":
                list=list.stream()
                        .filter(v->v.getType().equals("moto"))
                        .collect(Collectors.toList());
                break;
            case "voiture":
                list=list.stream()
                        .filter(v->v.getType().equals("voiture"))
                        .collect(Collectors.toList());
                break;
            case  "camion":
                list=list.stream()
                        .filter(v->v.getType().equals("camion"))
                        .collect(Collectors.toList());

                break;

        }
        vehiculeTableView.getItems().setAll(list);
    }

    private void addButtonToTable() {
        Callback<TableColumn<Vehicule, Void>, TableCell<Vehicule, Void>> cellFactory =
                new Callback<TableColumn<Vehicule, Void>, TableCell<Vehicule, Void>>() {
                    @Override
                    public TableCell<Vehicule, Void> call(final TableColumn<Vehicule, Void> param) {
                        final TableCell<Vehicule, Void> cell = new TableCell<Vehicule, Void>() {

                            private final Button btnDelete = new Button("Effacer");
                            private final Button btnEdit = new Button("Modifier");
                            private final Button btnAddMaintenance = new Button("Ajouter Maintenance");
                            // Nouveau bouton
                            private final Button btnShowMaintenance = new Button("Afficher Maintenances");

                            private final HBox pane = new HBox(5, btnDelete, btnEdit, btnAddMaintenance, btnShowMaintenance);
                            {
                                // Button action for deletion
                                btnDelete.setOnAction((ActionEvent event) -> {
                                    Vehicule vehicule = getTableView().getItems().get(getIndex());
                                    handleDeleteVehicule(vehicule);
                                });

                                // Button action for editing
                                btnEdit.setOnAction((ActionEvent event) -> {
                                    Vehicule vehicule = getTableView().getItems().get(getIndex());
                                    handleEditVehicule(vehicule);
                                });

                                // Button action for adding maintenance
                                btnAddMaintenance.setOnAction((ActionEvent event) -> {
                                    Vehicule vehicule = getTableView().getItems().get(getIndex());
                                    handleAddMaintenance(vehicule);
                                });
                                btnShowMaintenance.setOnAction((ActionEvent event) -> {
                                    Vehicule vehicule = getTableView().getItems().get(getIndex());
                                    handleShowMaintenances(vehicule);
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
    private void handleAddVehicule(ActionEvent event) {
        System.out.println("Add vehicule");
        try {
            // Charger le fichier FXML de l'ajout de véhicule
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Vehicule/VehiculeAdd.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de l'ajout de véhicule
            VehiculeControllerAdd vehiculeControllerAdd = loader.getController();

            // Créer une nouvelle fenêtre pour l'ajout de véhicule
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Bloque l'accès à la fenêtre principale
            stage.setTitle("Ajouter un Véhicule");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Attendre la fermeture de la fenêtre

            // Récupérer l'objet Véhicule
            Vehicule newVehicule = vehiculeControllerAdd.getVehiculeFromForm();
            if (newVehicule != null) {
                System.out.println("Véhicule ajouté dans MainController : " + newVehicule);
                // Tu peux maintenant ajouter ce véhicule à une liste, une base de données, etc.
            }
            else {
                System.out.println("ti7che");
            }
            vehiculeService.addVehicule(newVehicule);
            loadVehicules(filterChoiceBox.getValue());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void handleDeleteVehicule(Vehicule vehicule) {
        vehiculeService.deleteVehicule(vehicule.getNumMatricule());
        loadVehicules(filterChoiceBox.getValue());
    }

    private void handleEditVehicule(Vehicule vehicule) {
        // TODO: Open an edit dialog/form. For now, this simply calls update.
        System.out.println("modifier vehicule");
        try {
            // Charger le fichier FXML de l'ajout de véhicule
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Vehicule/VehiculeUpdate.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de l'ajout de véhicule
            VehiculeControllerUpdate vehiculeControllerUpdate = loader.getController();
            vehiculeControllerUpdate.setVehicule(vehicule);
            vehiculeControllerUpdate.afficherDetailsVehicule();

            // Créer une nouvelle fenêtre pour l'ajout de véhicule
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Bloque l'accès à la fenêtre principale
            stage.setTitle("modifier la vheicule");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Attendre la fermeture de la fenêtre

            // Récupérer l'objet Véhicule
            Vehicule newVehicule = vehiculeControllerUpdate.getVehiculeFromForm(vehicule.getType(),vehicule.getNumMatricule());
            if (newVehicule != null) {
                System.out.println("Véhicule modifie dans MainController : " + newVehicule);
                // Tu peux maintenant ajouter ce véhicule à une liste, une base de données, etc.
            }
            else {
                System.out.println("ti7che");
            }
            vehiculeService.updateVehicule(newVehicule);
            loadVehicules(filterChoiceBox.getValue());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void handleAddMaintenance(Vehicule vehicule) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Vehicule/Maintenance.fxml"));
            Parent root = loader.load();

            VehiculeControllerMaintenance controller = loader.getController();
            controller.setSelectedVehicule(vehicule.getNumMatricule());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter Maintenance");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void handleShowMaintenances(Vehicule vehicule) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Vehicule/MaintenanceList.fxml"));
            Parent root = loader.load();

            controller.vehicule.MaintenanceListController controller = loader.getController();
            controller.loadMaintenances(vehicule.getNumMatricule());

            Stage stage = new Stage();
            stage.setTitle("Maintenances du véhicule " + vehicule.getNumMatricule());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // This shows the full stack trace
        }
    }

    public List<String> checkAndNotify() {
        List<Vehicule> vehicules = vehiculeService.getAllVehicules();
        LocalDate today = LocalDate.now();
        int preavis = 30; // Number of days before expiration for notification
        boolean notificationNeeded = false;
        List<String>notifications = new ArrayList<>();

        for (Vehicule v : vehicules) {
            if (v.getDateVignette().minusDays(preavis).isBefore(today)) {
                notificationNeeded = true;
                notifications.add(v.getType()+v.getNumMatricule() +"doit faire un visite de vignette");
            }
            if (v.getDateVisiteTechnique().minusDays(preavis).isBefore(today)) {
                notificationNeeded = true;
                notifications.add(v.getType()+v.getNumMatricule() +"doit faire un visite technique");
            }
            if (v.getDateAssurance().minusDays(preavis).isBefore(today)) {
                notificationNeeded = true;
                notifications.add(v.getType()+v.getNumMatricule() +"doit faire un visite de assurance");
            }
            if (v.getDateVidange().minusDays(preavis).isBefore(today)) {
                notifications.add(v.getType()+v.getNumMatricule() +"doit faire un visite de vidange");
                notificationNeeded = true;
            }
            if(v.getKmAvantEntretien()<5000){
                notificationNeeded = true;
                notifications.add(v.getType()+v.getNumMatricule() +"doit faire un visite de avant   nombreKilometreRestant="+v.getKmAvantEntretien());
            }
        }

        // If any of the conditions require notification, show the red badge
        if (notificationNeeded) {
            newNotificationReceived();
        }
        return notifications;
    }

    @FXML
    private void handleNotification() {
        // Hide the badge since notifications are being acknowledged.
        notificationBadge.setVisible(false);

        // Retrieve notifications. Replace getNotifications() with your actual notification retrieval logic.
        List<String> notifications = checkAndNotify();

        // Create an Alert dialog to display notifications.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notifications");

        if (notifications.isEmpty()) {
            alert.setHeaderText("Aucune notification");
            alert.setContentText("Il n'y a pas de nouvelles notifications.");
        } else {
            alert.setHeaderText("Nouvelles notifications");
            // Combine all notifications into a single string separated by line breaks.
            String content = notifications.stream().collect(Collectors.joining("\n"));
            alert.setContentText(content);
        }
        alert.showAndWait();
    }
    public void newNotificationReceived() {
        // Make the badge visible
        notificationBadge.setVisible(true);

        // Create a pop animation using ScaleTransition
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), notificationBadge);
        scaleTransition.setFromX(0.0);
        scaleTransition.setFromY(0.0);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }
}
