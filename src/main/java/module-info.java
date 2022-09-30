module com.example.elo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.elo to javafx.fxml;
    exports com.example.elo;
}