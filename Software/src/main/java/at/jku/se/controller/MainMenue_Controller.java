package at.jku.se.controller;

import at.jku.se.utility.NewScreen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MainMenue_Controller implements Initializable {


    @FXML
    private AnchorPane root;

    @FXML
    private Button btn_NewGame,btn_LoadGame,btn_Exit;

    // refractor into one methode, pass over String for fxml and for Action event
    @FXML
    public void handleButton_NewGame(ActionEvent event) throws IOException{

        NewScreen.openNewScreen(event,"/fxml/newGame.fxml");
    }

    @FXML
    public void handleButton_LoadGame(ActionEvent event) throws IOException {
        NewScreen.openNewScreen(event,"/fxml/loadGame.fxml");
    }

    @FXML
    public void handleButton_MainHighScores(ActionEvent event) throws IOException {
        NewScreen.openNewScreen(event,"/fxml/highScore.fxml");
    }



    @FXML
    public void handleButton_Exit(ActionEvent event){
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
