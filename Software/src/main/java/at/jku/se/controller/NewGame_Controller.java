package at.jku.se.controller;

import at.jku.se.utility.NewScreen;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class NewGame_Controller  implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Button btn_continue;

    @FXML
    private RadioButton rb_Sa_regulaer,rb_Sa_samurai,rb_Sa_freiform,rb_Sg_manuell,rb_Sg_auto,rb_Sk_easy,rb_Sk_medium,rb_Sk_hard;

    @FXML
    private Label lb_difficulty;

    String version ="";
    String generateType="";
    String difficulty ="";

    @FXML
    public void handleButton_Continue(ActionEvent event) throws IOException { // aufgerufen wenn auf diesen Button geklicked wird

        //NewScreen.openNewScreen(event,"/fxml/spielfeld.fxml");
        Node node = (Node) event.getSource();
        Stage oldStage = (Stage)node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        if(version.equals("rb_Sa_regulaer")) {
            fxmlLoader.setLocation(getClass().getResource("/fxml/spielfeld.fxml"));
        }else {
        fxmlLoader.setLocation(getClass().getResource("/fxml/samurai.fxml"));
        }
        Parent root2 = null;
        try {
            root2 = (Parent) fxmlLoader.load();

            if(version.equals("rb_Sa_regulaer")) {

                Playfield_Controller controller = fxmlLoader.getController();
                controller.initData(version, generateType, difficulty);
            } else {
            Samurai_Controller controller = fxmlLoader.getController();
            controller.initData(version, generateType, difficulty);
            }
        } catch (IOException ex) {
        }
        Stage stage = new Stage();
        stage.setTitle("old game");
        stage.sizeToScene();
        stage.setScene(new Scene(root2));
        stage.show();
        stage.setMaximized(true);
        //stage.setMinHeight(1200);
        //stage.setMinWidth(1600);
        oldStage.close();

    }

    @FXML
    public void handleButton_selfNewGame(ActionEvent actionEvent) {
        Platform.exit();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup sudokuVersion = new ToggleGroup();
        rb_Sa_freiform.setToggleGroup(sudokuVersion);
        rb_Sa_regulaer.setToggleGroup(sudokuVersion);
        rb_Sa_samurai.setToggleGroup(sudokuVersion);

        sudokuVersion.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                version = ((RadioButton)sudokuVersion.getSelectedToggle()).getId();
            }
        });
        ToggleGroup sudokuGenerate = new ToggleGroup();
        rb_Sg_auto.setToggleGroup(sudokuGenerate);
        rb_Sg_manuell.setToggleGroup(sudokuGenerate);

        sudokuGenerate.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                generateType = ((RadioButton)sudokuGenerate.getSelectedToggle()).getText();
            }
        });

        ToggleGroup sudokuDifficulty = new ToggleGroup();
        rb_Sk_medium.setToggleGroup(sudokuDifficulty);
        rb_Sk_easy.setToggleGroup(sudokuDifficulty);
        rb_Sk_hard.setToggleGroup(sudokuDifficulty);


        sudokuDifficulty.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                difficulty = ((RadioButton)sudokuDifficulty.getSelectedToggle()).getText();
            }
        });
        rb_Sg_manuell.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(rb_Sg_manuell.isSelected()){
                    rb_Sk_easy.setDisable(true);
                    rb_Sk_medium.setDisable(true);
                    rb_Sk_hard.setDisable(true);
                    lb_difficulty.setDisable(true);
                }
            }
        });
        rb_Sg_auto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(rb_Sg_auto.isSelected()){
                    rb_Sk_easy.setDisable(false);
                    rb_Sk_medium.setDisable(false);
                    rb_Sk_hard.setDisable(false);
                    lb_difficulty.setDisable(false);
                }

            }
        });


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
