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
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class FilmEntryController implements Initializable {

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

    private Image defaultImage;

    //Testing Variable
    @FXML
    private Button showImage;
    private int viwCounter = 1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageController.Current.setImageView(imageView);
        ImageController.Current.setWidthTarget(200);
        ImageController.Current.setHeightTarget(150);

        inputTitle.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                    imageIsFocused(false);
                }
            }
        });
        inputYear.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean newPropertyValue) {
                if (newPropertyValue) imageIsFocused(false);
            }
        });
        inputElo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean newPropertyValue) {
                if (newPropertyValue) imageIsFocused(false);
            }
        });

        //load a default image to assign to imageView
        File file = new File("defaultImage.jpg");
        defaultImage =new Image(file.toURI().toString());
        SetImage(defaultImage);

        loadBtn.setOnAction(e->browseImage() );
        showImage.setOnAction(e-> ShowImage() );

    }


    @FXML
    private void InsertEntry(ActionEvent event) throws Exception {
        String tile =inputTitle.getText();
        int year = Integer.parseInt(inputYear.getText());
        int elo = Integer.parseInt(inputElo.getText());
        Image image = imageView.getImage();
        Film newEntry = new Film(tile, year,image, elo);

        ModelDB db = ModelDB.DB.GetModel();
        int filmID = db.InsertFilm(newEntry);
        EmptyAllFields();

    }


    @FXML
    private void OpenMainMenu(ActionEvent event) throws Exception{

        StageManager.SM.SetCurrentScene("mainMenu", "Main Menu");

    }

    //This funktion is temporal, just to control if reading from Db works fine
    private void ShowImage(){
        //can be kept for now maybe there is a usage for this sample code
        /* OLD
        ModelDB db = ModelDB.DB.GetModel();
        Image image = db.TestGetImage(2);
        Current.setImage(image);
        //Current.updateImageView();

         */
        ModelDB db = ModelDB.DB.GetModel();
        Image image = db.getImageById(viwCounter);
        viwCounter+=1;
        imageIsFocused(true);
        SetImage(image);
    }

    private void SetImage (Image image){
            ImageController.Current.setImage(image);
    }

    private void browseImage() {
        imageIsFocused(true);
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(loadBtn.getScene().getWindow());
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image =new Image(fileInputStream);
            SetImage(image);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void EmptyAllFields(){
        inputTitle.setText("");
        inputYear.setText("");
        inputElo.setText("");
        imageView.setImage(defaultImage);
    }


    public void imageClicked(MouseEvent mouseEvent) {
        imageIsFocused(true);
    }

    private void imageIsFocused(Boolean inFocus){
        ImageController.Current.imageEditable =inFocus;

        if(inFocus){
            imageStackPane.getStyleClass().remove(0);
            imageStackPane.getStyleClass().add("imageViewSelected");
            imageStackPane.requestFocus();

        }else{
            imageStackPane.getStyleClass().remove(0);
            imageStackPane.getStyleClass().add("imageViewIdle");
        }
    }

}
