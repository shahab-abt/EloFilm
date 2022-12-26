module com.example.elo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;


    opens Elo to javafx.fxml;
    exports Elo;
    exports Model;
    opens Model to javafx.fxml;
}