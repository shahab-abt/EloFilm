module com.example.elo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens controller to javafx.fxml;
    exports controller;
    exports Model;
    opens Model to javafx.fxml;
}