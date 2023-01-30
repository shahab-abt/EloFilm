package Elo;

import Model.Film;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainMenuController implements Initializable  {

    @FXML
    private Button btnMainMenu;

    @FXML
    private TableView<Film> tableView;
    @FXML
    private TableColumn<Film,String> filmId;
    @FXML
    private TableColumn<Film,Integer> yearId;
    @FXML
    private TableColumn<Film,Integer> eloId;

    ObservableList<Film> list  = FXCollections.observableArrayList(
            new Film("Padre",1978,null,800),
            new Film("F2",1989,null,900),
            new Film("F3",1990,null,1000),
            new Film("F4",2000,null,1100),
            new Film("F5",2010,null,700)

    );



    @FXML
    private Button btnAddFilm;

    @FXML
    private Button btnViewFilms;

    //function to navigate from a Button to FilmEntry
    @FXML
    private void OpenAddFilm(ActionEvent event) throws Exception {
        StageManager.SM.SetCurrentScene("FilmEntry", "Add new Film");
    }

    @FXML
    private void OpenViewFilm(ActionEvent event) throws Exception {
        //StageManager.SM.NewScene("ViewFilm", "View saved Films");
        StageManager.SM.SetCurrentScene("TableViewFilmController", "View saved Films");
        /*
        StageManager stage = new StageManager();
        stage.UpdateStage("FilmEnry");
        */
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filmId.setCellValueFactory(new PropertyValueFactory<Film,String>("title"));
        yearId.setCellValueFactory(new PropertyValueFactory<Film,Integer>("year"));
        eloId.setCellValueFactory(new PropertyValueFactory<Film,Integer>("eloRate"));

        tableView.setItems(list);

    }

    public void OpenMainMenu(ActionEvent actionEvent) throws IOException {
        StageManager.SM.SetCurrentScene("FilmEntry", "Add New Film");
    }
    public void OpenUserMainView(ActionEvent actionEvent) throws IOException {
        StageManager.SM.SetCurrentScene("UserMainView", "Main Manu");
    }

    public void OpenMovieList(ActionEvent actionEvent) throws IOException {
        StageManager.SM.SetCurrentScene("MovieList", "Movie List");
    }
}
