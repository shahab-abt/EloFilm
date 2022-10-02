module com.example.elo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.elo to javafx.fxml;
    exports com.example.elo;
    exports Model;
    opens Model to javafx.fxml;
}