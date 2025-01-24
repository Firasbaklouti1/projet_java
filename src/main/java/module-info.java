module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    requires java.sql;

    // Export the main application package for use by other modules
    exports app;

    // Open packages to allow JavaFX reflection for FXML files
    opens fxml to javafx.fxml;
    opens controller to javafx.fxml;

    // If reflection is not required for fxml or controller, remove their exports
    exports controller;
}
