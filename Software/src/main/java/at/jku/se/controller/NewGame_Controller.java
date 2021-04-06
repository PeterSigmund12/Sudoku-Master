package at.jku.se.controller;

import at.jku.se.utility.NewScreen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class NewGame_Controller  implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Button btn_autoNewGame, btn_selfNewGame, btn_backNewMainMen;

    @FXML
    public void handleButton_autoNewGame(ActionEvent actionEvent)  { // aufgerufen wenn auf diesen Button geklicked wird
        Platform.exit();
    }

    @FXML
    public void handleButton_selfNewGame(ActionEvent actionEvent) {
        Platform.exit();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void handleButton_backNewMainMen(ActionEvent event) throws IOException{

        NewScreen.openNewScreen(event,"/fxml/mainmenue.fxml");

        /*
        Node node = (Node) event.getSource();
        Stage oldStage = (Stage)node.getScene().getWindow();;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/mainmenue.fxml"));
        Parent root2 = null;

        try {
            root2 = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
        }

        Stage stage = new Stage();
        stage.setTitle("Start new game");
        stage.setScene(new Scene(root2));

        stage.show();
        oldStage.close();

         */

    }
}
