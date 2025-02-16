package controller.document;

import entities.Document;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import service.AutoEcoleService;
import service.DocumentService;

import java.awt.Desktop;
import java.io.File;
import java.sql.Blob;
import java.util.List;

public class DocumentListController {

    @FXML
    private TableView<Document> documentTable;

    @FXML
    private TableColumn<Document, Integer> idColumn;

    @FXML
    private TableColumn<Document, String> nomColumn;

    @FXML
    private TableColumn<Document, String> typeColumn;

    @FXML
    private TableColumn<Document, String> dateAjoutColumn;

    @FXML
    private TableColumn<Document, Blob> fichierColumn;

    private DocumentService documentService = new DocumentService();

    @FXML
    public void initialize() {
        // Configure columns using property names that match your Document class
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateAjoutColumn.setCellValueFactory(new PropertyValueFactory<>("dateAjout"));

        // Create a custom cell for the "Fichier" column that adds a "Voir" button
        fichierColumn.setCellFactory(col -> new TableCell<Document, Blob>() {
            private final Button btn = new Button("Voir");

            @Override
            protected void updateItem(Blob fichier, boolean empty) {
                super.updateItem(fichier, empty);
                if (empty ) {
                    setGraphic(null);
                }
                else {
                    btn.setOnAction(e -> handleViewFichier(getTableRow().getItem()));
                    setGraphic(btn);
                }

            }
        });
    }

    /**
     * Loads documents for a given candidate (or related entity) id.
     *
     * @param candidatId the id of the candidate to load documents for
     */
    public void loadDocuments(int candidatId) {
        List<Document> documents = documentService.getAllDocuments(candidatId);
        documentTable.setItems(FXCollections.observableArrayList(documents));
    }


    private void handleViewFichier(Document document) {
        AutoEcoleService autoEcoleService = new AutoEcoleService();
        try {
            // Try to convert the file into a PDF and open it
            File pdf = documentService.convertBlobToPdf(document.getFichier(),autoEcoleService.getPiedDePage(),autoEcoleService.enteteAutoEcole());
            Desktop.getDesktop().open(pdf);
        } catch (Exception e) {
            try {
                // If the PDF conversion fails, try to convert to an image and display it
                Image image = documentService.convertBlobToImage(document.getFichier());
                if (image != null) {
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(600);
                    imageView.setPreserveRatio(true);

                    StackPane root = new StackPane(imageView);
                    Scene scene = new Scene(root, 700, 500);

                    Stage stage = new Stage();
                    stage.setTitle("Document");
                    stage.setScene(scene);
                    stage.show();
                } else {
                    System.out.println("Erreur de chargement du fichier");
                }
            } catch (Exception e1) {
                System.out.println("Erreur de chargement du fichier");
            }
        }
    }
}
