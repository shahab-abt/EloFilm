package controller;

import Model.ModelDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManager extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        ModelDB db = new ModelDB("t1.db");
        stage.setMaximized(true);
        SM.mainStage=stage;
        stage.setTitle("Hello!");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        SM.NewScene("mainMenu", "Main Menu");

        /*
        UpdateStage("mainMenu");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainMenu.fxml"));
        FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("FilmEntry.fxml"));
        Scene scene1 = new Scene(fxmlLoader.load(), 500, 800);

        Scene scene2 = new Scene(fxmlLoader2.load(), 500, 800);

        SM.scene1 = scene1;
        SM.scene2 = scene2;
 */



    }





    public static class SM {
        /*
        private static Scene scene1;
        private static Scene scene2;
        
         */
        private static Stage mainStage;
        /*
        public static void setScene1(Scene scene1) {
            staticClass.scene1 = scene1;
        }

        public static void setScene2(Scene scene2) {
            staticClass.scene2 = scene2;
        }
        */
        public static void NewScene(String viewName, String title) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(viewName+".fxml"));
            Parent page = (Parent) new FXMLLoader(HelloApplication.class.getResource(viewName+".fxml")).load();

            Stage stage = SM.mainStage;
            Scene currentScene = stage.getScene();
            Scene scene = new Scene(page,currentScene.getWidth(),currentScene.getHeight());
            stage.setTitle(title);
            stage.setScene(scene);

        }

    /*
        public static void Switch(){
            Scene scene= scene1;
            scene1 = scene2;
            scene2= scene;
            mainStage.setScene(scene1);
            mainStage.show();

        }

     */
    }
}
