package at.jku.se.utility;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NewScreenDropDown {


        @FXML
        public static void handleButtonBacktoMain(ActionEvent event, String source, Stage oldStage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(NewScreen.class.getResource("/fxml/mainmenue.fxml"));
            Parent root2 = null;
            try {
                root2 = (Parent) fxmlLoader.load();
            } catch (IOException ex) {
            }
            Stage stage = new Stage();
            stage.setTitle("Load game");
            stage.setScene(new Scene(root2));

            stage.show();
            oldStage.close();

        }


    }

