package controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import Model.*;
public class HelloApplication extends Application  implements EventHandler<ActionEvent> {
    private Film f1 = new Film("God Father",1972, 0,800);
    private Film f2 = new Film("Citizen Kane",1941, 0,800);
    private Button lastButton;
    private Label txtField;

    @Override
    public void start(Stage stage) throws IOException {
        ModelDB db = new ModelDB("t1.db");

        //db.InsertFilm(f1);
        //db.InsertFilm(f2);

        List<Film> tempList = db.GetAllFilms();
        URL tempTest =  HelloApplication.class.getResource("mainMenu.fxml");
        URL test2= FilmEntryController.class.getResource("mainMenu.fxml");





        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainMenu.fxml"));
        FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("FilmEntry.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 800);
       /*
        lastButton = (Button) scene.lookup("#last");
        lastButton.setOnAction(this);
        txtField = (Label) scene.lookup("#filmTitle");

    */
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handle(ActionEvent event) {

        if(event.getSource()==lastButton){
            txtField.setText(f1.getTitle());
            System.out.println("clicked");
        }
    }

    public static void main(String[] args) {



        launch();
    }
}