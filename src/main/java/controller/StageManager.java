package controller;


import Model.ModelDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManager extends Application {

    // first Class to be loaded,
    // it will set up the Stage and made it possible to switch between scenes
    //

    @Override
    public void start(Stage stage) throws Exception {
        //temporal Code to check how save image into Database
        ModelDB db = new ModelDB("t1.db");
        db.InsertImage();
        db.GetImage();
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
        SM.SetScene("mainMenu", "Main Menu");

    }





    public static class SM {

        private static Stage mainStage;

        public static void SetScene(String viewName, String title) throws IOException {

            //unnecessary??
            //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(viewName+".fxml"));
            Parent page = (Parent) new FXMLLoader(StageManager.class.getResource(viewName+".fxml")).load();

            Stage stage = SM.mainStage;
            Scene currentScene = stage.getScene();
            Scene scene = new Scene(page,currentScene.getWidth(),currentScene.getHeight());

            //TODO should be in separated class not here
            if (viewName=="FilmEntry"){

                //Assign new EventHandler to scene so it would be possible to capture picture from Clipboard
                KeyCodeCombination pastKeyCombination = new KeyCodeCombination(KeyCode.V, KeyCombination.SHORTCUT_DOWN);
                scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                    if(pastKeyCombination.match(keyEvent)) {

                        Image image = Clipboard.getSystemClipboard().getImage();
                        //new ImageClipboard().SetImage(image);
                        //ImageClipboard.IMG.setImage(image);
                        FilmEntryController.Current.setImage(image);
                        FilmEntryController.Current.updateImageView();
                    }
                });


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
            stage.setScene(scene);

        }
    }
}
