package at.jku.se.sudokuMaster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class SudokuMaster extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainmenue.fxml"));

        Scene scene = new Scene(root);
       // scene.getStylesheets().add("/styles/mainmenu.css");

        stage.setTitle("Sudoku-Master");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}