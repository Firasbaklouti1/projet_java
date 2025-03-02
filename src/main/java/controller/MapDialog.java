package controller;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import entities.rdv.Lieu;
import java.util.concurrent.CountDownLatch;

public class MapDialog {
    // Version Java 11 compatible (sans text blocks)
    private static final String MAP_HTML =
            "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <title>Select Location</title>\n" +
                    "    <script src=\"https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&callback=initMap\" async defer></script>\n" +
                    "    <script>\n" +
                    "        var map;\n" +
                    "        var marker;\n" +
                    "        \n" +
                    "        function initMap() {\n" +
                    "            map = new google.maps.Map(document.getElementById('map'), {\n" +
                    "                center: {lat: 48.8566, lng: 2.3522},\n" +
                    "                zoom: 12\n" +
                    "            });\n" +
                    "            \n" +
                    "            map.addListener('click', function(e) {\n" +
                    "                if (marker) marker.setMap(null);\n" +
                    "                marker = new google.maps.Marker({\n" +
                    "                    position: e.latLng,\n" +
                    "                    map: map\n" +
                    "                });\n" +
                    "                window.javaBridge.setLocation(e.latLng.lat(), e.latLng.lng());\n" +
                    "            });\n" +
                    "        }\n" +
                    "    </script>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div id=\"map\" style=\"height: 500px; width: 800px;\"></div>\n" +
                    "</body>\n" +
                    "</html>";

    public static Lieu showMapDialog() throws InterruptedException {
        // Initialize JavaFX if not already done
        if (!Platform.isFxApplicationThread()) {
            new JFXPanel();
        }

        CountDownLatch latch = new CountDownLatch(1);
        Lieu[] selectedLieu = new Lieu[1];

        Platform.runLater(() -> {
            Stage stage = new Stage();
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();

            // Bridge to communicate between JavaScript and Java
            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    JSBridge bridge = new JSBridge(lat -> {
                        selectedLieu[0] = new Lieu(0, "Custom Location", "", lat[0], lat[1], "");
                        stage.close();
                        latch.countDown();
                    });
                    webEngine.executeScript("window.javaBridge = " + bridge);
                }
            });

            webEngine.loadContent(MAP_HTML.replace("YOUR_API_KEY", "YOUR_GOOGLE_MAPS_API_KEY"));
            stage.setScene(new Scene(webView));
            stage.showAndWait();
        });

        latch.await();
        return selectedLieu[0];
    }

    // JavaScript-Java bridge
    public static class JSBridge {
        private final java.util.function.Consumer<double[]> callback;

        public JSBridge(java.util.function.Consumer<double[]> callback) {
            this.callback = callback;
        }

        public void setLocation(double lat, double lng) {
            callback.accept(new double[]{lat, lng});
        }
    }
}