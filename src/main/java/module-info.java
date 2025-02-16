module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    requires java.sql;
    requires java.sql.rowset;
    requires java.desktop;
    requires kernel;
    requires io;

    // Export the main application package for use by other modules
    exports app;

    // Open packages to allow JavaFX reflection for FXML files
    opens fxml to javafx.fxml;
    opens controller to javafx.fxml;
    opens entities to javafx.base;

    // If reflection is not required for fxml or controller, remove their exports
    exports controller;
    exports controller.vehicule;
    exports controller.candidat;
    opens controller.vehicule to javafx.fxml;
    exports controller.moniteur;
    opens controller.moniteur to javafx.fxml;
    opens controller.candidat to javafx.fxml;
    exports controller.document;
    opens controller.document to javafx.fxml;
}
