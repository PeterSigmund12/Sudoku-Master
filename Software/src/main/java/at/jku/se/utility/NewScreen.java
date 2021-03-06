package at.jku.se.utility;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Logger;

public class NewScreen {

    @FXML
    public static void openNewScreen(ActionEvent event, String source) throws IOException {


        Node node = (Node) event.getSource();
        Stage oldStage = (Stage)node.getScene().getWindow();
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(NewScreen.class.getResource(source));
        Parent root2 = null;
        try {
            root2 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            logger.warning(""+e);

        }
        Stage stage = new Stage();

        stage.setScene(new Scene(root2));

        stage.show();
        oldStage.close();


    }





}
