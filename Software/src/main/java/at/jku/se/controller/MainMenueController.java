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

    /**
     * Diese Methode ruft die Klasse NewScreen auf, welche dafür sorgt, dass der NewGame-Screen aufgerufen
     * und angezeigt wird. Dabei wird das event und den Pfad wo die fxml-Datei liegt, übergeben.
     * @param event
     * @throws IOException
     */
    @FXML
    public void handleButtonNewGame(ActionEvent event) throws IOException{

        NewScreen.openNewScreen(event,"/fxml/newGame.fxml");
    }

    /**
     * Diese Methode ruft die Klasse NewScreen auf, welche dafür sorgt, dass der LoadGame-Screen aufgerufen
     * und angezeigt wird. Dabei wird das event und den Pfad wo die fxml-Datei liegt, übergeben.
     * @param event
     * @throws IOException
     */
    @FXML
    public void handleButtonLoadGame(ActionEvent event) throws IOException {
        NewScreen.openNewScreen(event,"/fxml/loadGame.fxml");
    }

    /**
     * Diese Methode ruft die Klasse NewScreen auf, welche dafür sorgt, dass der Highscore-Screen aufgerufen
     * und angezeigt wird. Dabei wird das event und den Pfad wo die fxml-Datei liegt, übergeben.
     * @param event
     * @throws IOException
     */
    @FXML
    public void handleButtonMainHighScores(ActionEvent event) throws IOException {
        NewScreen.openNewScreen(event,"/fxml/highScore.fxml");
    }


    /**
     * Schhließt sobald der Benutzer den Button drückt, die Platform und beendet das Programm.
     * @param event
     */
    @FXML
    public void handleButtonExit(ActionEvent event){
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
