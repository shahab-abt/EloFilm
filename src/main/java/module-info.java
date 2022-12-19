module com.example.elo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;


    opens controller to javafx.fxml;
    exports controller;
    exports Model;
    opens Model to javafx.fxml;
}