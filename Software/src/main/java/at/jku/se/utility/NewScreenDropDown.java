package at.jku.se.utility;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class NewScreenDropDown {

    /**
     * Returns the user back to the main
     * @param event JavaFXML event
     * @param source
     * @param oldStage The current stage that should be closed.
     * @throws IOException
     */
        @FXML
        public static void handleButtonBacktoMain(ActionEvent event, String source, Stage oldStage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            fxmlLoader.setLocation(NewScreen.class.getResource("/fxml/mainmenue.fxml"));
            Parent root2 = null;
            try {
                root2 = (Parent) fxmlLoader.load();
            } catch (IOException ex) {
                logger.warning("" + ex);
            }
            Stage stage = new Stage();

            stage.setScene(new Scene(root2));

            stage.show();
            oldStage.close();

        }


    }

