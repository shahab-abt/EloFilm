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
    //@FXML
    //private Button testView;

    private Image defaultImage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Current.viewStatic= imageView;

        inputTitle.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldPropertyValue, Boolean newPropertyValue) {

                if (newPropertyValue) imageIsFocused(false);
                /*
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

                 */

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

        Current.setImage(defaultImage);

        //UpdateImageView(image);

        loadBtn.setOnAction(e->browseImage() );
        //testView.setOnAction(e-> ShowImage() );

    }


    // would read information
    @FXML
    private void InsertEntry(ActionEvent event) throws Exception {
        String tile =inputTitle.getText();
        int year = Integer.parseInt(inputYear.getText());
        int elo = Integer.parseInt(inputElo.getText());

        Film newEntry = new Film(tile, year,Current.image, elo);

        ModelDB db = ModelDB.DB.GetModel();
        int filmID = db.InsertFilm(newEntry);
        //db.InsertImage(filmID,Current.image);

        EmptyAllFields();

    }


    @FXML
    private void OpenMainMenu(ActionEvent event) throws Exception{

        StageManager.SM.SetCurrentScene("mainMenu", "Main Menu");

    }

    private void ShowImage(){
        ModelDB db = ModelDB.DB.GetModel();
        Image image = db.TestGetImage(2);
        Current.setImage(image);
        //Current.updateImageView();
    }

    private void browseImage() {
        //Current.imageEditable =true;
        imageIsFocused(true);
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(loadBtn.getScene().getWindow());
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image =new Image(fileInputStream);
            Current.setImage(image);
            return;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }




        /*
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ModelDB db = ModelDB.DB.GetModel();
            db.TestInsertImage(2,fileInputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

         */


    }

    private void EmptyAllFields(){
        inputTitle.setText("");
        inputYear.setText("");
        inputElo.setText("");
        imageView.setImage(defaultImage);

    }

    public void ImagePaste(KeyEvent keyEvent) {
        System.out.println(keyEvent.getCode());
    }

    public void imageClicked(MouseEvent mouseEvent) {
        imageIsFocused(true);
        /*
        imageStackPane.getStyleClass().remove(0);
        imageStackPane.getStyleClass().add("imageViewSelected");

        imageStackPane.requestFocus();

         */

    }

    private void imageIsFocused(Boolean inFocus){
        Current.imageEditable =inFocus;

        if(inFocus){
            imageStackPane.getStyleClass().remove(0);
            imageStackPane.getStyleClass().add("imageViewSelected");
            imageStackPane.requestFocus();

        }else{
            imageStackPane.getStyleClass().remove(0);
            imageStackPane.getStyleClass().add("imageViewIdle");
            //System.out.println("Textfield on focus");
        }



    }

    //Static Class make it possible to manipulate ImageView from another Class
    public static class Current {
        private static Image image;
        private static ImageView viewStatic;
        private static boolean imageEditable =true;

        public static void setImage(Image img) {
            if (imageEditable){
                image = img;
                CropAndResizeImage();
                updateImageView();
            }

        }


        public Image getImage() {
            return image;
        }

        private static void updateImageView(){
            viewStatic.setImage(image);
        }

        //TODO Crop Image so it would fit to ImageView
        private static void CropAndResizeImage(){

        }


    }
}
