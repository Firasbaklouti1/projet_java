module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    requires java.sql;

    exports app; // Export the app package
    exports fxml; // If needed, also export fxml package for FXML files
    opens fxml to javafx.fxml;
    opens controller to javafx.fxml;
    exports controller;// Allow reflection for FXML files
}
