package Elo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;


//Controller Class for UserMainView
//it will be the Main Menu of a logged user. from this window can the user navigate to other windows
public class UserMainViewController implements Initializable  {
    @FXML
    ImageView compareImageRight;
    @FXML
    ImageView compareImageLeft;
    @FXML
    private Label title;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageController.Current.MakeCornerRound(compareImageRight, 30,30);
        ImageController.Current.MakeCornerRound(compareImageLeft, 30,30);


        var test1 = Font.getFamilies();

        Font.loadFont(UserMainViewController.class.getResourceAsStream("/Style/Fonts/BAD GRUNGE.ttf"),10);

        var test2 = Font.getFamilies();
        int a =2;

    }

    //Temporal just for testing, should be removed
    public void DoCrop(ActionEvent event) {
        ImageController.Current.MakeCornerRound(compareImageRight, 100,20);
    }
}
