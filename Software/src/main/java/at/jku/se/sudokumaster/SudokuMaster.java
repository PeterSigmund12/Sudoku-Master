package at.jku.se.sudokumaster;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App Main Method, Starts GUI Stage
 */
public class SudokuMaster extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainmenue.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Sudoku-Master");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }
    @Override
    public void stop() throws Exception
    {
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }

}