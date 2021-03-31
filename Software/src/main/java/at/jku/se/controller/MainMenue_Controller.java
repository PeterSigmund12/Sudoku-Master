package at.jku.se.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenue_Controller {


    @FXML
    private AnchorPane root;

    @FXML
    private Button btn_NewGame,btn_LoadGame,btn_Exit;


    @FXML
    public void handleButton_NewGame(ActionEvent event){
        Platform.exit();
    }

    @FXML
    public void handleButton_LoadGame(ActionEvent event) throws IOException {
        Platform.exit();
        /*Stage oldStage = (Stage)root.getScene().getWindow();;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("fxml/loadGame.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 400);
        //Parent root2 = (Parent) fxmlLoader.load();

        Stage stage = new Stage();
        stage.setTitle("Load game");
        stage.setScene(scene);

        stage.show();
        //oldStage.close();*/
    }


    @FXML
    public void handleButton_Exit(ActionEvent event){
        Platform.exit();
    }
}
