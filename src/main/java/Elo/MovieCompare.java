package Elo;

import Model.Film;
import Model.ModelDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.security.PrivateKey;
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



    private List<Film> userFilmList;
    private Film firstFilm;
    private Film secondFilm;

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

        int randomIndex2;
        do {
            randomIndex2 = rand.nextInt(listLength);
        }
        while (randomIndex2 ==randomIndex1);
        System.out.println(randomIndex1+" and "+ randomIndex2+" from "+listLength);
        firstFilm = userFilmList.get(randomIndex1);
        secondFilm = userFilmList.get(randomIndex2);
        DisplayComparingFilm(firstFilm,secondFilm);

    }
    private void DisplayComparingFilm(Film film1, Film film2)
    {
        filmLabel1.setText(film1.toString());
        filmLabel2.setText(film2.toString());
        //filmYear1.setText(String.valueOf(film1.getYear()));
        //filmYear2.setText(String.valueOf(film2.getYear()));

        Image croppedImage1 = ImageController.Current.CropAndResizeImage(film1.getImage(),220,150);
        compareImage1.setImage(croppedImage1);
        ImageController.Current.MakeCornerRound(compareImage1,20,20);
        Image croppedImage2 = ImageController.Current.CropAndResizeImage(film2.getImage(),220,150);
        compareImage2.setImage(croppedImage2);
        ImageController.Current.MakeCornerRound(compareImage2,20,20);
    }


    @FXML
    private void GotoUserFilm() {
        try {
            StageManager.SM.SetCurrentScene("UserMainView", "Movie List");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void SelectDraw() {
        CalculateElo(0);
        System.out.println("draw!");
    }

    @FXML
    private void FirstFilmSelected() {
        CalculateElo(1);
        System.out.println("First one!");
    }
    @FXML
    private void SecondFilmSelected() {
        CalculateElo(-1);
        System.out.println("Second one!");
    }

    /*
    this function will calculate Elo rating for compared Films
    Calculation from this Page is used: https://metinmediamath.wordpress.com/2013/11/27/how-to-calculate-the-elo-rating-including-example/

    parameter result show which Film is chosen, or it's a draw
    FirstFilm:       1
    SecondFilm:      -1
    Draw:            0
     */
    private void CalculateElo(int result){
        double kFactor = 100;

        double film1rate = firstFilm.getEloRate();
        double film2rate = secondFilm.getEloRate();

        double fR1 =  Math.pow(10,(film1rate)/400 );
        double fR2 =  Math.pow(10,(film2rate)/400 );

        double fE1 = fR1/(fR1+fR2);
        double fE2 = fR2/(fR1+fR2);

        double fS1, fS2 ;

        switch (result) {
            case 1 -> {
                fS1 = 1;
                fS2 = 0;
            }
            case -1 -> {
                fS1 = 0;
                fS2 = 1;
            }
            default -> {
                fS1 = 0.5;
                fS2 = 0.5;
            }
        }
        int newFR1 = (int) Math.round( film1rate + kFactor*(fS1-fE1));
        int newFR2 = (int) Math.round(film2rate + kFactor*(fS2-fE2));
        System.out.println("Movie1 old: " +film1rate+" now is:" +newFR1 );
        System.out.println("Movie2 old: " +film2rate+" now is:" +newFR2 );

        firstFilm.setEloRate(newFR1);
        secondFilm.setEloRate(newFR2);
        //Todo: userID
        ModelDB.DB.GetModel().UpdateEloRate(1, firstFilm.getFilm_id(), firstFilm.getEloRate());
        ModelDB.DB.GetModel().UpdateEloRate(1, secondFilm.getFilm_id(), secondFilm.getEloRate());
        SelectComparingFilm();
    }

}
