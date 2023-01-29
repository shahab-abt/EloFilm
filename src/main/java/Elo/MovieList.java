package Elo;

import Model.Film;
import Model.ModelDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;
import java.util.function.Predicate;


public class MovieList implements Initializable {

    //Selected Movie Displaying nodes
    @FXML
    ImageView selectedFilmImage;
    @FXML
    Label selectedFilmTitle;
    @FXML
    Label selectedFilmYear;
    @FXML
    ComboBox<Integer> selectedRating;
    @FXML
    Button addFilmBtn;
    @FXML
    AnchorPane selectedAnchor;

    //
    @FXML
    AnchorPane mainAnchorPane;
    @FXML
    ListView<Film> userFilms;
    @FXML
    ListView<Film> viewable;
    @FXML
    TextField searchText;

    // @FXML
    //ComboBox searchCombo;
    private List<Film> allFilm;
    private  List<String> filmTitles= new ArrayList<>();
    private ObservableList<Film> listOutput = FXCollections.observableArrayList();

    private ObservableList<Film> addedFilm = FXCollections.observableArrayList();
    //private String ratingOptions[] = {"1","2","3","4","5","6","7","8","9","10"} ;
    private Integer ratingOptions[] ={1,2,3,4,5,6,7,8,9,10};
    private int selectedRate=0;
    private Film currentSelectedFilm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ModelDB db = ModelDB.DB.GetModel();
        allFilm = db.GetAllFilms();
        setUpFilmTitles();

        selectedRating.setItems(FXCollections.observableArrayList(ratingOptions));
        selectedRating.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                selectedRate=t1;
            }
        });

        //add an existing Film to user Database
        addFilmBtn.setOnAction(event -> {
            Film film = currentSelectedFilm;
            int eloRank=selectedRate*200;

            film.setEloRate(eloRank);
            ModelDB.DB.GetModel().GiveFilmRank(1,film.getFilm_id(),eloRank);
            UpdateUserFilm();
            /*
            addedFilm.add(film);
            userFilms.refresh();

             */

        });

        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            searchForTitles(newValue);
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });

        viewable.setItems(listOutput);
        viewable.setPrefHeight(175.0);
        viewable.setVisible(false);

        viewable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Film>() {
            @Override
            public void changed(ObservableValue<? extends Film> observableValue, Film film, Film t1) {
                MovieIsSelected(t1);
                DisplaySelectedFilm(true);
            }
        });

        userFilms.setCellFactory(listView -> new ListCell<Film>(){
            @Override
            protected void updateItem(Film film, boolean empty) {
                super.updateItem(film, empty);

                //Scene currentScene = selectedFilmTitle.getScene();
                //ObservableList stylSheet = currentScene.getStylesheets();
                //ObservableList stylSheet2= mainAnchorPane.getStylesheets();

                if (empty) {
                    setGraphic(null);
                } else {

                    // Create a HBox to hold our displayed value
                    HBox movieBox = new HBox();
                    movieBox.setPrefHeight(160);
                    movieBox.setPrefWidth(-1);

                    movieBox.getStyleClass().add("movieBox");
                    //movieBox.setAlignment(Pos.CENTER);
                    //movieBox.getStylesheets().addAll(mainAnchorPane.getStylesheets());
                    //movieBox children

                        ImageView imv = new ImageView();
                        imv.setImage(film.getImage());
                        imv.setFitHeight(150);
                        imv.setFitWidth(200);

                        VBox movieInfo = new VBox();
                        movieInfo.setPrefHeight(160);
                        movieInfo.setPrefWidth(800);
                        //vBox children
                            Label filmTitle = new Label(film.getTitle());
                            filmTitle.setPrefHeight(40);
                            filmTitle.getStyleClass().add("filmTitle");
                            Label filmYear = new Label(String.valueOf(film.getYear()));
                            filmYear.getStyleClass().add("filmYear");
                            filmYear.setPrefHeight(10);
                        movieInfo.getChildren().addAll(filmTitle,filmYear);
                        Label eloRank = new Label(String.valueOf( film.getEloRate()));
                        eloRank.getStyleClass().add("filmRate");
                    movieBox.getChildren().addAll(imv,movieInfo,eloRank);




                    //Add Value
                    /*
                    movieBox.getChildren().addAll(
                            new ImageView(film.getImage()),
                            new Label(film.getTitle()),
                            new Label (String.valueOf(film.getYear()))
                    );

                     */

                    setGraphic(movieBox);
                }

            }

        });
        userFilms.setItems(addedFilm);

        UpdateUserFilm();

        DisplaySelectedFilm(false);
    }

    private void UpdateUserFilm() {
        List<Film> userFilmList = ModelDB.DB.GetModel().GetAllMovieUser(1);
        addedFilm.clear();
        addedFilm.addAll(userFilmList);
        userFilms.refresh();
    }


    private void setUpFilmTitles() {
        for (Film film: allFilm) {
            String title =film.getTitle()+" ( "+film.getYear()+" )";
            String[] arr = title.split(" ");
            //boolean contains = Arrays.stream(arr).anyMatch("Orange"::equals);
            filmTitles.add(title);
        }
        Collections.sort(filmTitles);

    }

    private void setUpFilmTitlesXBackup() {
        for (Film film: allFilm) {
            String title =film.getTitle()+" ( "+film.getYear()+" )";
            String[] arr = title.split(" ");
            //boolean contains = Arrays.stream(arr).anyMatch("Orange"::equals);
            filmTitles.add(title);
        }
        Collections.sort(filmTitles);

    }

    private void searchForTitles(String inputStr){

        List<Film> foundFilm= new ArrayList<>();
        //String[] inputArr = .split(" ");
        String[] inputArr = SplitAndLowercase(inputStr);

        for (Film film:allFilm) {
            boolean isMatch =false;
            //String[] titleArr = film.split(" ");

            //create a StringArray off all Words in Film Titel
            String[] titleArr = SplitAndLowercase(film.toString());
            for (String wrd:inputArr) {
                Predicate<String> word = s -> s.startsWith(wrd);
                if(Arrays.stream(titleArr).anyMatch(word)){
                    System.out.println(wrd+"-"+film.toString());
                    isMatch =true;
                    break;
                }
            }
            if (isMatch) foundFilm.add(film);
        }
        //searchCombo.getItems().clear();

//??
        /*
      int before = searchCombo.getItems().size();
        if (before >0){
            int a=5;
        }

         */
        listOutput.clear();


        for (Film film: foundFilm){
            //searchCombo.getItems().add(s);
            listOutput.add (film);
        }
        if (foundFilm.size()<1)
        {
            viewable.setVisible(false);
        }else {
            viewable.setVisible(true);
        }
        //int after = searchCombo.getItems().size();
        //searchCombo.show();
        //System.out.println(before+" "+after);

        //foundTitles
    }
    //TODO Selected Film should be disappear when the focus is lost
    public void DisplaySelectedFilm(boolean bool){
        if(bool) {


            //selectedAnchor.layoutYProperty().set(51);
            selectedAnchor.setVisible(true);
        }else{


            //selectedAnchor.layoutYProperty().set(-500);
            selectedAnchor.setVisible(false);
        }
    }

    private void MovieIsSelected(Film film){
        if (film==null){
            System.out.println("GOT NULL for selected Film");
            return;
        }
        currentSelectedFilm=film;
        selectedFilmTitle.setText(film.getTitle());
        selectedFilmYear.setText(String.valueOf(film.getYear()));
        selectedFilmImage.setImage(film.getImage());
        viewable.setVisible(false);



    }
    private String[] SplitAndLowercase(String s){
        String[] returnValue = s.split(" ");
        for (int i = 0; i < returnValue.length; i++) {
            returnValue[i] =returnValue[i].toLowerCase();
        }

        return returnValue;
    }

}
