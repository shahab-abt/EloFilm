package controller;

import Model.UserFilm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableViewFilm implements Initializable {
    @FXML
    private TableView<UserFilm> tableView;

    @FXML
    private TableColumn<UserFilm,String> filmId;

    @FXML
    private TableColumn<UserFilm,Integer> yearId;
    @FXML
    private TableColumn<UserFilm,Integer> eloId;

    ObservableList<UserFilm> list  = FXCollections.observableArrayList(
            new UserFilm("pedarkhande",1978,800),
            new UserFilm("Heat",1995,900),
            new UserFilm("FilmSevomi",1998,1000),
            new UserFilm("FilmChahoromi",2002,1100),
            new UserFilm("FilmPanjomi",2010,700)
    );


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filmId.setCellValueFactory(new PropertyValueFactory<UserFilm,String>("filmTile"));
        yearId.setCellValueFactory(new PropertyValueFactory<UserFilm,Integer>("filmYear"));
        eloId.setCellValueFactory(new PropertyValueFactory<UserFilm,Integer>("filmElo"));

        tableView.setItems(list);
    }
}
