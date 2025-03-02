package app;

import controller.vehicule.VehiculeControllerMain;
import entities.Personne;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    /*public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AutoEcoleFxml.fxml"));
        AnchorPane root = loader.load();
x
        Scene scene = new Scene(root);
        primaryStage.setTitle("Auto Ecole");
        primaryStage.setScene(scene);
        primaryStage.show();
    }*/
    public void start(Stage stage) throws IOException {
        System.out.println(System.getProperty("javafx.runtime.version"));

        VBox root =  FXMLLoader.load(getClass().getResource("/fxml/formation/Formation.fxml"));
        //Parent root =  FXMLLoader.load(getClass().getResource("/fxml/Vehicule/VehiculeMain.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
