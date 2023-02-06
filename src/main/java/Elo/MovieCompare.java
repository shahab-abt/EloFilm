package Elo;

import Model.Film;
import Model.ModelDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class MovieCompare implements Initializable {

    @FXML
    private ImageView compareImage1;
    @FXML
    private ImageView compareImage2;
    @FXML
    private Label filmLabel1;
    @FXML
    private Label filmLabel2;
    @FXML
    private Label filmYear1;
    @FXML
    private Label filmYear2;


    private List<Film> userFilmList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO should be adjusted for current user
        userFilmList = ModelDB.DB.GetModel().GetAllMovieUser(1);
        SelectComparingFilm();
    }

    //Select Two Films from userFilmList, which should be shown to be compared
    //The selection algorithm should be random for the first movie
    // for the second movie should have a small distance from the first movie
    //TODO: update selection algorithm
    private void SelectComparingFilm(){
        int listLength= userFilmList.size();
        //if there is less than 2 Films in userFilmList the function should be eliminated
        if( listLength <2 ){
            System.out.println("there is not enough Movie to compare");
            return;
        }
        //choosing two random Index
        Random rand = new Random();
        int randomIndex1 = rand.nextInt(listLength);
        System.out.println(randomIndex1+" from "+listLength);

        int randomIndex2;
        do {
            randomIndex2 = rand.nextInt(listLength);
        }
        while (randomIndex2 ==randomIndex1);
        System.out.println(randomIndex1+" and "+ randomIndex2+" from "+listLength);

        DisplayComparingFilm(userFilmList.get(randomIndex1),userFilmList.get(randomIndex2));

    }
    private void DisplayComparingFilm(Film film1, Film film2)
    {
        filmLabel1.setText(film1.getTitle());
        filmLabel2.setText(film2.getTitle());
        filmYear1.setText(String.valueOf(film1.getYear()));
        filmYear2.setText(String.valueOf(film2.getYear()));

        Image croppedImage1 = ImageController.Current.CropAndResizeImage(film1.getImage(),220,150);
        compareImage1.setImage(croppedImage1);
        ImageController.Current.MakeCornerRound(compareImage1,20,20);
        Image croppedImage2 = ImageController.Current.CropAndResizeImage(film2.getImage(),220,150);
        compareImage2.setImage(croppedImage2);
        ImageController.Current.MakeCornerRound(compareImage2,20,20);
    }


    @FXML
    public void GotoUserFilm() {
        try {
            StageManager.SM.SetCurrentScene("UserMainView", "Movie List");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
