package at.jku.se.controller;

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


    @FXML
    public void handleButton_NewGame(ActionEvent event){
        Platform.exit();
    }

    @FXML
    public void handleButton_LoadGame(ActionEvent event) throws IOException {
        //Platform.exit();
        Node node = (Node) event.getSource();
      //  Platform.runLater();
        Stage oldStage = (Stage)node.getScene().getWindow();;


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/loadGame.fxml"));
        Parent root2 = null;
        try {
            root2 = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
          //  Logger.getLogger(Mainmenu_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setTitle("Load game");
        stage.setScene(new Scene(root2));

        stage.show();
        oldStage.close();
    }


    @FXML
    public void handleButton_Exit(ActionEvent event){
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
