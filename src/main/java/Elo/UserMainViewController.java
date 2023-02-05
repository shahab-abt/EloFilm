package Elo;

import Model.Film;
import Model.ModelDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


//Controller Class for UserMainView
//it will be the Main Menu of a logged user. from this window can the user navigate to other windows
public class UserMainViewController implements Initializable  {
    @FXML
    ImageView compareImageRight;
    @FXML
    ImageView compareImageLeft;
    @FXML
    private Label title;
    @FXML
    ListView<Film> userAllFilms;

    private ObservableList<Film> observingFilms = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageController.Current.MakeCornerRound(compareImageRight, 50,50);
        ImageController.Current.MakeCornerRound(compareImageLeft, 30,30);


        var test1 = Font.getFamilies();

        Font.loadFont(UserMainViewController.class.getResourceAsStream("/Style/Fonts/BAD GRUNGE.ttf"),10);
        Font.loadFont(UserMainViewController.class.getResourceAsStream("/Style/Fonts/AVENGEANCE MIGHTIEST AVENGER.ttf"),10);

        var test2 = Font.getFamilies();
        int a =2;


        // setup userAllFilms so it creat List of viewable object that present information every rated Film from user
        userAllFilms.setCellFactory(listView -> new ListCell<Film>(){
                    @Override
                    protected void updateItem(Film film, boolean empty) {
                        super.updateItem(film, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {

                            // Create a HBox to hold our displayed value
                            HBox movieBox = new HBox();
                            movieBox.setPrefHeight(112);
                            movieBox.setPrefWidth(-1);

                            movieBox.getStyleClass().add("movieBox");
                            //movieBox.setAlignment(Pos.CENTER);
                            //movieBox.getStylesheets().addAll(mainAnchorPane.getStylesheets());
                            //movieBox children



                            ImageView imv = new ImageView();
                            imv.setImage(film.getImage());

                            imv.setFitHeight(100);
                            imv.setFitWidth(220);
                            imv.setLayoutX(1);
                            imv.setLayoutY(6);

                            Pane imageContainer = new Pane();
                            imageContainer.setPrefHeight(112);
                            imageContainer.setPrefWidth(245);

                            //Insets insets = new Insets(6,6,6,6);

                            imageContainer.getStyleClass().add("imageContainer");
                            imageContainer.getChildren().add(imv);



                            //VBox movieInfo = new VBox();
                            //movieInfo.setPrefHeight(160);
                            //movieInfo.setPrefWidth(800);
                            //vBox children
                            Label filmTitle = new Label(film.getTitle());
                            filmTitle.setPrefHeight(112);
                            filmTitle.setPrefWidth(245);
                            filmTitle.setPadding(new Insets(6,6,6,6));
                            filmTitle.getStyleClass().add("filmTitle");


                            Label filmYear = new Label(String.valueOf(film.getYear()));
                            filmYear.setPrefHeight(112);
                            filmYear.setPrefWidth(245);
                            filmYear.setPadding(new Insets(6,6,6,6));
                            filmYear.getStyleClass().add("filmYear");

                            Pane emptySpace = new Pane();
                            emptySpace.setPrefHeight(112);
                            emptySpace.setMinWidth(210);


                            //movieInfo.getChildren().addAll(filmTitle,filmYear);
                            Label eloRank = new Label(String.valueOf( film.getEloRate()));
                            eloRank.setPrefHeight(112);
                            eloRank.setPrefWidth(200);

                            eloRank.getStyleClass().add("filmRate");
                            eloRank.setPadding(new Insets(6,0,6,0));

                            Label star = new Label("★");
                            star.getStyleClass().add("filmRateStar");
                            star.setPrefWidth(45);
                            star.setPrefHeight(112);

                            ImageController.Current.MakeCornerRound(imv,20,20);
                            movieBox.getChildren().addAll(imageContainer,filmTitle,filmYear,emptySpace,eloRank,star);


                            setGraphic(movieBox);
                        }

                    }

        });
        userAllFilms.setItems(observingFilms);
        UpdateUserFilm();

    }

    private void UpdateUserFilm() {
        List<Film> userFilmList = ModelDB.DB.GetModel().GetAllMovieUser(1);
        observingFilms.clear();
        observingFilms.addAll(userFilmList);
        userAllFilms.refresh();
    }

    //Temporal just for testing, should be removed
    public void DoCrop(ActionEvent event) {
        ImageController.Current.MakeCornerRound(compareImageRight, 100,20);
    }
}
