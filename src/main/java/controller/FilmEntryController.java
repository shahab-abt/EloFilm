package controller;

import Model.Film;
import Model.ModelDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Math.round;

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


    private Image defaultImage;

    //Testing Variable
    @FXML
    private Button showImage;
    private int viwCounter = 1;


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
        showImage.setOnAction(e-> ShowImage() );

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
        Current.setImage(image);
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
        private static BufferedImage bufferedImage;
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
            //target image siz
            int widthTarget = 300;
            int heightTarget = 200;

            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            bufferedImage =
                    new BufferedImage(
                            width,
                            height,
                            BufferedImage.TYPE_INT_RGB);
            SwingFXUtils.fromFXImage(image,bufferedImage);

//removable temporal
            File original = new File("original.jpg");
            try {
                ImageIO.write(bufferedImage, "jpg",original );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//end of removable


            //Ratio of width and height of target image to original
            float widthRatio = (float)  widthTarget/width;
            float heightRatio = (float) heightTarget/height;
            float targetAspectRation = (float)widthTarget/heightTarget;

            int newHeightByWR = round(height*widthRatio);
            int newWidthByHR = round(width*heightRatio);

            int widthGapToTarget =  newWidthByHR - widthTarget;
            int heightGapToTarget = newHeightByWR - heightTarget;

            //image should be copped first
            int widthResize;
            int heightResize;


            float choosedRatio;
            if(widthGapToTarget>=0){
                //width should be cropped
                //calculate new width to have same Aspect ratio as imageTarget

                int newWidth =  round(height * (targetAspectRation));
                bufferedImage = CropOuterEdge( bufferedImage,newWidth, height );
                choosedRatio = heightRatio;
            }else{
                //height should be cropped
                int newHeight =  round(width * (1/targetAspectRation));
                bufferedImage = CropOuterEdge( bufferedImage,width, newHeight );
                choosedRatio = widthRatio;
            }

//removable temporal
            File cropped = new File("cropped.jpg");
            try {
                ImageIO.write(bufferedImage, "jpg",cropped );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//end of removable



            BufferedImage resizedImage = new BufferedImage(widthTarget,heightTarget, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = resizedImage.createGraphics();

            graphics2D.drawImage(bufferedImage, 0, 0, widthTarget, heightTarget, null);
            graphics2D.dispose();

//removable temporal
            File resized = new File("resized.jpg");
            try {
                ImageIO.write(resizedImage, "jpg",resized );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//end of removable

            }

        private static BufferedImage CropOuterEdge(BufferedImage image, int targetWidth, int targetHeight) {

            int x = round( ((float) image.getWidth() - targetWidth) /2);
            int y = round( ((float) image.getHeight() - targetHeight) /2);
            return image.getSubimage(x,y,targetWidth,targetHeight);

        }

    }
}
