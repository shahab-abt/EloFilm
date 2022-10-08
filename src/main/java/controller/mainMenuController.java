package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class mainMenuController {
    @FXML
    private Button btnAddFilm;

    @FXML
    private void OpenAddFilm(ActionEvent event) throws Exception {
        StageManager.staticClass.Switch();
        /*
        StageManager stage = new StageManager();
        stage.UpdateStage("FilmEnry");
        */
    }

}
