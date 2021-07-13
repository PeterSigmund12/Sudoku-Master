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



public class NewGameController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Button btnContinue;

    @FXML
    private RadioButton rbSaRegulaer;
    @FXML
    private RadioButton rbSaSamurai;
    @FXML
    private RadioButton rbSaFreiform;
    @FXML
    private RadioButton rbSgManuell;
    @FXML
    private RadioButton rbSgAuto;
    @FXML
    private RadioButton rbSkEasy;
    @FXML
    private RadioButton rbSkMedium;
    @FXML
    private RadioButton rbSkHard;
    @FXML
    private Label lbDifficulty;

    String version ="";
    String generateType="";
    String difficulty ="";


    /**
     * Nachdem der Continue Button gedrückt wurde, werden die ausgewählten Radiobutton-IDs mithilfe der InitData Methode
     * an den Playfield-Controller übergeben und ruft die neue fxml auf.
     * @param event
     * @throws IOException
     *
     */
    @FXML
    public void handleButtonContinue(ActionEvent event) throws IOException { // aufgerufen wenn auf diesen Button geklicked wird

        //NewScreen.openNewScreen(event,"/fxml/spielfeld.fxml");
        Node node = (Node) event.getSource();
        Stage oldStage = (Stage)node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/spielfeld.fxml"));
        Parent root2 = null;
        try {
            root2 = (Parent) fxmlLoader.load();
            PlayfieldController controller = fxmlLoader.getController();
            controller.initData(version, generateType, difficulty);

        } catch (IOException ex) {
        }
        Stage stage = new Stage();

        stage.sizeToScene();
        stage.setScene(new Scene(root2));
        stage.show();
        //stage.setMaximized(true);
        stage.setMinHeight(960);
        stage.setMinWidth(1280);
        oldStage.close();

    }

    @FXML
    public void handleButtonSelfNewGame(ActionEvent actionEvent) {
        Platform.exit();
    }


    /**
     * Alle RadioButtons werden initialisiert und zu den entsprechenden Gruppen hinzugefügt.
     * Außerdem werden zu den einzelnen Gruppen ChangeListener hinzugefügt, damit sobald ein anderer
     * RadioButton ausgewählt wurde, abgespeichert wird.
     * Ebenfalls werden die RadioButtons der Schwierigkeitsgruppe enabled, wenn bei dem Generierungstyp
     * automatisch ausgewählt wurde.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup sudokuVersion = new ToggleGroup();
        rbSaFreiform.setToggleGroup(sudokuVersion);
        rbSaRegulaer.setToggleGroup(sudokuVersion);
        rbSaSamurai.setToggleGroup(sudokuVersion);

        sudokuVersion.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                version = ((RadioButton)sudokuVersion.getSelectedToggle()).getId();
            }
        });
        ToggleGroup sudokuGenerate = new ToggleGroup();
        rbSgAuto.setToggleGroup(sudokuGenerate);
        rbSgManuell.setToggleGroup(sudokuGenerate);

        sudokuGenerate.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                generateType = ((RadioButton)sudokuGenerate.getSelectedToggle()).getText();
            }
        });

        ToggleGroup sudokuDifficulty = new ToggleGroup();
        rbSkMedium.setToggleGroup(sudokuDifficulty);
        rbSkEasy.setToggleGroup(sudokuDifficulty);
        rbSkHard.setToggleGroup(sudokuDifficulty);


        sudokuDifficulty.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                difficulty = ((RadioButton)sudokuDifficulty.getSelectedToggle()).getText();
            }
        });
        rbSgManuell.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(rbSgManuell.isSelected()){
                    rbSkEasy.setDisable(true);
                    rbSkMedium.setDisable(true);
                    rbSkHard.setDisable(true);
                    lbDifficulty.setDisable(true);
                }
            }
        });
        rbSgAuto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(rbSgAuto.isSelected()){
                    rbSkEasy.setDisable(false);
                    rbSkMedium.setDisable(false);
                    rbSkHard.setDisable(false);
                    lbDifficulty.setDisable(false);
                }

            }
        });


    }


    /**
     * Diese Methode ruft die Klasse NewScreen auf, welche dafür sorgt, dass der MainMenü-Screen aufgerufen
     * und angezeigt wird. Dabei wird das event und den Pfad wo die fxml-Datei liegt, übergeben.
     * @param event
     * @throws IOException
     */
    public void handleButtonBackNewMainMen(ActionEvent event) throws IOException{

        NewScreen.openNewScreen(event,"/fxml/mainmenue.fxml");


    }
}
