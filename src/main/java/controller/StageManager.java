package controller;


import Model.ModelDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StageManager extends Application {

    // first Class to be loaded,
    // it will set up the Stage and made it possible to switch between scenes
    //

    @Override
    public void start(Stage stage) throws Exception {
        //temporal Code to check how save image into Database
        ModelDB db = new ModelDB("t1.db");
        //db.InsertImage();
        //db.GetImage();
        //

        //Stage will be loaded in full size
        stage.setMaximized(true);
        //assign loading Stage in static mainStage
        SM.mainStage=stage;

        //unnecessary??
        //stage.setTitle("Hello!");

        //create mainMenu Scene
        FXMLLoader fxmlLoader = new FXMLLoader(StageManager.class.getResource("mainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        //update Stage´s Scene with mainMenu Scene
        stage.setScene(scene);
        stage.show();
        SM.SetCurrentScene("mainMenu", "Main Menu");

    }





    public static class SM {

        private static Stage mainStage;
        private static Scene currentScene;

        public static void SetCurrentScene(String viewName, String title) throws IOException {

            //unnecessary??
            //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(viewName+".fxml"));
            Parent page = (Parent) new FXMLLoader(StageManager.class.getResource(viewName+".fxml")).load();

            /*
            //alternativ
            FXMLLoader fxmlLoader = new FXMLLoader(StageManager.class.getResource(viewName+".fxml"));
            Parent root = fxmlLoader.load();
            ModuleLayer.Controller controller = fxmlLoader.getController();
            Scene scene1 = new Scene(root);
            scene1.setFill(Color.TRANSPARENT);
            //controller.setStage(scene1;


             */


            page.getStylesheets().add(StageManager.class.getResource("/Style/app.css").toString());



            Stage stage = SM.mainStage;
            Scene lastScene = stage.getScene();
            currentScene = new Scene(page, lastScene.getWidth(), lastScene.getHeight());


            //TODO should be in separated class not here
            if (viewName=="FilmEntry"){
                RegisterFilmEntryEvents();
                /*
                //Assign new EventHandler to scene so it would be possible to capture picture from Clipboard
                KeyCodeCombination pastKeyCombination = new KeyCodeCombination(KeyCode.V, KeyCombination.SHORTCUT_DOWN);
                currentScene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                    if(pastKeyCombination.match(keyEvent)) {

                        Image image = Clipboard.getSystemClipboard().getImage();
                        //new ImageClipboard().SetImage(image);
                        //ImageClipboard.IMG.setImage(image);
                        FilmEntryController.Current.setImage(image);
                        //FilmEntryController.Current.updateImageView();
                    }
                });

                 */


            }



            /*  unnecessary??
            scene.setOnKeyPressed(e->{
                if(e.getCode() == KeyCode.A){
                    System.out.println("A key was pressed");


                }
            });
             */

            // stage will be updated with new Scene
            stage.setTitle(title);
            stage.setScene(currentScene);
            System.out.println("Stage has a new scene");

        }
        private static void RegisterFilmEntryEvents(){
            //Assign new EventHandler to scene so it would be possible to capture picture from Clipboard
            KeyCodeCombination pastKeyCombination = new KeyCodeCombination(KeyCode.V, KeyCombination.SHORTCUT_DOWN);
            currentScene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                if(pastKeyCombination.match(keyEvent)) {

                    Image image = Clipboard.getSystemClipboard().getImage();


                    BufferedImage bufferedImage =
                            new BufferedImage((
                                    int) image.getWidth(),
                                    (int) image.getHeight(),
                                    BufferedImage.TYPE_INT_RGB);
                    SwingFXUtils.fromFXImage(image,bufferedImage);
                    FilmEntryController.Current.setImage(bufferedImage);
                    File file =new File("Test.jpg");
                    try {
                        ImageIO.write(bufferedImage, "jpg",file );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });

        }



        public static Scene GetCurrentScene(){
            return currentScene;
        }
    }
}
