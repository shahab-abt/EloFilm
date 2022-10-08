package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManager extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        StageManager.staticClass.mainStage=stage;
        //UpdateStage("mainMenu");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainMenu.fxml"));
        FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("FilmEntry.fxml"));
        Scene scene1 = new Scene(fxmlLoader.load(), 500, 800);

        Scene scene2 = new Scene(fxmlLoader2.load(), 500, 800);

        StageManager.staticClass.scene1 = scene1;
        StageManager.staticClass.scene2 = scene2;

        stage.setTitle("Hello!");
        stage.setScene(scene1);
        stage.show();
    }

    public void UpdateStage(String fxmlName) throws IOException {

    ;

    }

    public static class staticClass{
        private static Scene scene1;
        private static Scene scene2;
        private static Stage mainStage;
        /*
        public static void setScene1(Scene scene1) {
            staticClass.scene1 = scene1;
        }

        public static void setScene2(Scene scene2) {
            staticClass.scene2 = scene2;
        }
        */


        public static void Switch(){
            Scene scene= scene1;
            scene1 = scene2;
            scene2= scene;
            mainStage.setScene(scene1);
            mainStage.show();

        }
    }
}
