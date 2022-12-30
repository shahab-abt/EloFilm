package Elo;

import Model.Film;
import Model.ModelDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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
    ListView<Film> viewable;
    @FXML
    TextField searchText;

    // @FXML
    //ComboBox searchCombo;
    private List<Film> allFilm;
    private  List<String> filmTitles= new ArrayList<>();
    private ObservableList<Film> listOutput = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ModelDB db = ModelDB.DB.GetModel();
        allFilm = db.GetAllFilms();
        setUpFilmTitles();
        /*
        searchCombo.getEditor().textProperty().addListener((observable, oldValue, newValue)->{
            System.out.println(oldValue+" "+newValue);
            searchForTitles(newValue);

                }
        );

         */



        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            searchForTitles(newValue);
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });

        //listOutput.add("t1");
        //listOutput.add("t2");
        viewable.setItems(listOutput);
        viewable.setPrefHeight(175.0);
        viewable.setVisible(false);

        viewable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Film>() {
            @Override
            public void changed(ObservableValue<? extends Film> observableValue, Film film, Film t1) {
                MovieIsSelected(t1);
            }
        });


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

    private void MovieIsSelected(Film film){
        if (film==null){
            System.out.println("GOT NULL for selected Film");
            return;
        }

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
