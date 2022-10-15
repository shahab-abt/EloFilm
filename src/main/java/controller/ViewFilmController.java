package controller;

import Model.Film;
import Model.ModelDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class ViewFilmController {
    /*
    simple page to observe saved Films in Database
    TODO: view should be changed to a detailed list  
     */

    private List<Film> allFilm;
    private int filmNum;

    @FXML
    private Label showTitle;
    @FXML
    private Label showYear;
    @FXML
    private Label showElo;
    @FXML
    private Button btnMainMenu;

    @FXML
    private Button btnFirstPage;

    @FXML
    protected void initialize(){

        ModelDB db = ModelDB.DB.GetModel();
        this.allFilm = db.GetAllFilms();
        this.filmNum=0;
        ReloadFilm(0);
        System.out.print(allFilm.size());

    }


    @FXML
    private void OpenMainMenu(ActionEvent event) throws Exception{
        StageManager.SM.NewScene("mainMenu", "Main Menu");
    }


    private void ReloadFilm(int parameter){


        switch (parameter){
            case -1:
                if (filmNum - 1 >= 0){
                    filmNum-=1;
                }
                break;
            case 1:
                if(filmNum< allFilm.size()-1){
                    filmNum+=1;
                }
                break;
            case 2:
                filmNum = allFilm.size()-1;
            break;
            default:
                filmNum=0;
        }

        Film film = this.allFilm.get(filmNum);
        showTitle.setText(film.getTitle());

        showYear.setText(String.valueOf(film.getYear()));
        showElo.setText(String.valueOf(film.getEloRate()));


    }

    @FXML
    public void ChangeFilm(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource() ;
        String data = (String) node.getUserData();
        int value = Integer.parseInt(data);

        ReloadFilm(value);
    }


}
