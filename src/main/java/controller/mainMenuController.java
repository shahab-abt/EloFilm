package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class mainMenuController {
    @FXML
    private Button btnAddFilm;

    @FXML
    private Button btnViewFilms;

    @FXML
    private void OpenAddFilm(ActionEvent event) throws Exception {
        StageManager.SM.NewScene("FilmEntry", "Add new Film");
        /*
        StageManager stage = new StageManager();
        stage.UpdateStage("FilmEnry");
        */
    }

    @FXML
    private void OpenViewFilm(ActionEvent event) throws Exception {
        //StageManager.SM.NewScene("ViewFilm", "View saved Films");
        StageManager.SM.NewScene("TableViewFilmController", "View saved Films");
        /*
        StageManager stage = new StageManager();
        stage.UpdateStage("FilmEnry");
        */
    }

}
