package at.jku.se.controller;

import at.jku.se.utility.NewScreen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenueController implements Initializable {


    @FXML
    private AnchorPane root;

    @FXML
    private Button btnNewGame;
    @FXML
    private Button btnLoadGame;
    @FXML
    private Button btnExit;

    // refractor into one methode, pass over String for fxml and for Action event
    @FXML
    public void handleButtonNewGame(ActionEvent event) throws IOException{

        NewScreen.openNewScreen(event,"/fxml/newGame.fxml");
    }

    @FXML
    public void handleButtonLoadGame(ActionEvent event) throws IOException {
        NewScreen.openNewScreen(event,"/fxml/loadGame.fxml");
    }

    @FXML
    public void handleButtonMainHighScores(ActionEvent event) throws IOException {
        NewScreen.openNewScreen(event,"/fxml/highScoreT.fxml");
    }



    @FXML
    public void handleButtonExit(ActionEvent event){
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
