package controller;

import Model.Film;
import Model.ModelDB;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.*;

public class ViewFilmController {
    /*
    simple page to observe saved Films in Database
    TODO: view should be changed to a detailed list
     */

    private List<Film> allFilm;
    private int filmNum;

    private ObservableList listam = FXCollections.observableArrayList();

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn tc1;

    @FXML
    private Label showTitle;
    @FXML
    private Label showYear;
    @FXML
    private Label showElo;
    @FXML
    private Button btnMainMenu;

    @FXML
    private ListView<String> movieList;

    @FXML
    private Button btnFirstPage;

    @FXML
    protected void initialize(){

        ModelDB db = ModelDB.DB.GetModel();
        this.allFilm = db.GetAllFilms();
        this.filmNum=0;
        ReloadFilm(0);
        System.out.print(allFilm.size());

        ObservableList<Label> testList =new ObservableList<Label>() {
            @Override
            public void addListener(ListChangeListener<? super Label> listChangeListener) {

            }

            @Override
            public void removeListener(ListChangeListener<? super Label> listChangeListener) {

            }

            @Override
            public boolean addAll(Label... labels) {
                return false;
            }

            @Override
            public boolean setAll(Label... labels) {
                return false;
            }

            @Override
            public boolean setAll(Collection<? extends Label> collection) {
                return false;
            }

            @Override
            public boolean removeAll(Label... labels) {
                return false;
            }

            @Override
            public boolean retainAll(Label... labels) {
                return false;
            }

            @Override
            public void remove(int i, int i1) {

            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Label> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Label label) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Label> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Label> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Label get(int index) {
                return null;
            }

            @Override
            public Label set(int index, Label element) {
                return null;
            }

            @Override
            public void add(int index, Label element) {

            }

            @Override
            public Label remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Label> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Label> listIterator(int index) {
                return null;
            }

            @Override
            public List<Label> subList(int fromIndex, int toIndex) {
                return null;
            }

            @Override
            public void addListener(InvalidationListener invalidationListener) {

            }

            @Override
            public void removeListener(InvalidationListener invalidationListener) {

            }
        };



        ObservableList<String> seasonList = FXCollections.<String>observableArrayList("Spring", "Summer", "Fall", "Winter");

        //seasons.setOrientation(Orientation.VERTICAL);
        //seasons.setPrefSize(120, 100);


        //movieList = seasons;

        TableColumn firstNameCol = new TableColumn("First Name");
        SimpleStringProperty firstName = new SimpleStringProperty("Heat");
        TableCell testCell=new TableCell<Film,String >();
        tc1.setCellValueFactory(new PropertyValueFactory("is it a cell"));




        Label l1 = new Label("L1");
        Label l2 = new Label("L2");
        Label l3 = new Label("L3");
        testList.add(showTitle);
        testList.add(showElo);
        testList.add(l3);

        //movieList.setItems(testList);
        //movieList.refresh();
        LoadData();
    }

    private void LoadData(){
        listam.removeAll(listam);
        String a ="Add";
        String b ="Bjj";
        String c ="Cgg";
        listam.addAll(a,b,c);

        movieList.getItems().addAll(listam);

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
