package controller;

import Model.Film;
import Model.ModelDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class FilmEntryController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField inputTitle;

    @FXML
    private TextField inputYear;

    @FXML
    private TextField inputElo;

    @FXML
    private Button insertButton;
    @FXML
    private Button btnMainMenu;

    @FXML
    private ImageView imageView;

    @FXML
    private Pane imageStackPane;

    @FXML
    private Button loadBtn;
    @FXML
    private Button testView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Current.viewStatic= imageView;
        inputTitle.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue)
                {


                    imageStackPane.getStyleClass().remove(0);
                    imageStackPane.getStyleClass().add("imageViewIdle");
                    System.out.println("Textfield on focus");
                }
                else
                {
                    System.out.println("Textfield out focus");
                }

            }
        });

        //load a default image to assign to imageView
        File file = new File("sample.png");
        Image image =new Image(file.toURI().toString());
        Current.setImage(image);
        //UpdateImageView(image);

        loadBtn.setOnAction(e->browseImage() );
        testView.setOnAction(e-> ShowImage() );

    }


    public FilmEntryController(){

    }

    // would read information
    @FXML
    private void InsertEntry(ActionEvent event) throws Exception {
        String tile =inputTitle.getText();
        int year = Integer.parseInt(inputYear.getText());
        int elo = Integer.parseInt(inputElo.getText());

        Film newEntry = new Film(tile, year,0, elo);
        ModelDB db = ModelDB.DB.GetModel();
        int filmID = db.InsertFilm(newEntry);
        db.InsertImage(filmID,Current.image);

        //remove:
        db.GetAllFilms();
    }


    @FXML
    private void OpenMainMenu(ActionEvent event) throws Exception{

        StageManager.SM.SetScene("mainMenu", "Main Menu");

    }

    private void ShowImage(){
        ModelDB db = ModelDB.DB.GetModel();
        Image image = db.TestGetImage(2);
        Current.setImage(image);
        Current.updateImageView();
    }

    private void browseImage() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(loadBtn.getScene().getWindow());
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ModelDB db = ModelDB.DB.GetModel();
            db.TestInsertImage(2,fileInputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

/*
    @Override
    public void handle(Event event) {
        Scene scene = inputTitle.getScene();
        scene.setOnKeyPressed(e->{
            if(e.getCode() == KeyCode.A){
                System.out.println("new scene  A key was pressed");


            }
        });

    }

 */



    /* defined in static
    private void UpdateImageView(Image image){
        imageView.setImage(image);
    }

     */

    /* what usage have it??
    public void MouseClicked(MouseEvent mouseEvent) {
        System.out.println("mouse click detected");
    }

     */

    public void ImagePaste(KeyEvent keyEvent) {
        System.out.println(keyEvent.getCode());
    }

    public void imageClicked(MouseEvent mouseEvent) {
        imageStackPane.getStyleClass().remove(0);
        imageStackPane.getStyleClass().add("imageViewSelected");

        imageStackPane.requestFocus();

    }

    //Static Class make it possible to manipulate ImageView from another Class
    public static class Current {
        private static Image image;
        private static ImageView viewStatic;

        public static void setImage(Image img) {
            image = img;
        }

        public Image getImage() {
            return image;
        }

        public static void updateImageView(){
            viewStatic.setImage(image);
        }


    }
}
