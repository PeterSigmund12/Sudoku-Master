package at.jku.se.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

public class PlayfieldControllerTest extends ApplicationTest{


    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/newGame.fxml"));
        stage.setTitle("Sudoku Test");
        stage.setScene(new Scene(root));
        stage.show();
    }


    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void clickNewGameTest() {
        clickOn("#rbSaRegulaer");
        clickOn("#rbSgAuto");
        //FxAssert.verifyThat(".selected", (RadioButton b) -> b.getId());

    }
}