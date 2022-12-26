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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;
import java.util.function.Predicate;


public class MovieList implements Initializable {

    @FXML
    ListView<String> viewable;
    @FXML
    TextField searchText;
    @FXML
    ComboBox searchCombo;
    private List<Film> allFilm;
    private  List<String> filmTitles= new ArrayList<>();
    private ObservableList<String> listOutput = FXCollections.observableArrayList();

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

        listOutput.add("t1");
        listOutput.add("t2");
        viewable.setItems(listOutput);

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
        List<String> foundTitles= new ArrayList<>();
        //String[] inputArr = .split(" ");
        String[] inputArr = SplitAndLowercase(inputStr);

        for (String title:filmTitles) {
            boolean isMatch =false;
            //String[] titleArr = title.split(" ");
            String[] titleArr = SplitAndLowercase(title);
            for (String wrd:inputArr) {
                Predicate<String> word = s -> s.startsWith(wrd);
                if(Arrays.stream(titleArr).anyMatch(word)){
                    System.out.println(wrd+"-"+title);
                    isMatch =true;
                    break;
                }
            }
            if (isMatch) foundTitles.add(title);
        }
        searchCombo.getItems().clear();

        int before = searchCombo.getItems().size();
        if (before >0){
            int a=5;
        }



        for (String s: foundTitles){
            searchCombo.getItems().add(s);
        }
        int after = searchCombo.getItems().size();
        searchCombo.show();
        System.out.println(before+" "+after);

        //foundTitles
    }

    private String[] SplitAndLowercase(String s){
        String[] returnValue = s.split(" ");
        for (int i = 0; i < returnValue.length; i++) {
            returnValue[i] =returnValue[i].toLowerCase();
        }

        return returnValue;
    }

}
