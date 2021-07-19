package at.jku.se.controller;

import at.jku.se.controller.HighScore.CreateHighScoreObject;
import at.jku.se.utility.HighScoreObject;
import at.jku.se.utility.NewScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;


public class HighScoreBoardController implements Initializable {


    @FXML
    ListView<String> lvHighScoreGames;
    @FXML
    ObservableList<String> highScoreGameList;
    @FXML
    String selected;

    @FXML
    private Label label;

    @FXML
    private Label lbGameName;
    @FXML
    private Label lbPlayer;
    @FXML
    private Label lbVersion;
    @FXML
    private Label lbGen;
    @FXML
    private Label lbScore;

    @FXML
    private Label lbDiff;
    @FXML
    private Label lbTime;

    @FXML
    private ImageView ivSavegame;

    @FXML
    private MenuBar menuBar;


    @FXML
    private TableView<HighScoreObject> highScore;
    @FXML
    TableColumn<HighScoreObject, Long> scoreColumn;
    @FXML
    TableColumn<HighScoreObject, String> nameColumn;

    List<HighScoreObject> higScoreList = new ArrayList<>();

    @FXML
    public void handleButtonBackHighMainMen(ActionEvent event) throws IOException {
        NewScreen.openNewScreen(event, "/fxml/mainmenue.fxml");
    }

    @FXML
    public void handleButtonBacktoMain(ActionEvent event) throws IOException {

       // Stage oldStage = (Stage) menuBar.getScene().getWindow();

        //NewScreenDropDown.handleButtonBacktoMain(event, "/fxml/mainmenue.fxml", oldStage);
        NewScreen.openNewScreen(event,"/fxml/mainmenue.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      //  fillListView();

        //set up collumns
        nameColumn.setCellValueFactory(new PropertyValueFactory<HighScoreObject, String>("gameName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<HighScoreObject, Long>("highScore"));

        //load dummy datat
        highScore.setItems(getHighscorer());
    }


    /**
     *
     */


    public void fillListView() {
        //Highscorre column
        TableColumn<HighScoreObject, Long> scoreColumn = new TableColumn<>("Score");
        //minimum with scoreColumn.setMinWidth(229)
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("highScore"));


        //Name column
        TableColumn<HighScoreObject, Long> nameColumn = new TableColumn<>("Game Name");
        //minimum with scoreColumn.setMinWidth(229)
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("gameName"));

        highScore = new TableView<>();
        highScore.setItems(getHighscorer());
        highScore.getColumns().addAll(nameColumn, scoreColumn);

    }


    ObservableList<HighScoreObject> getHighscorer() {

        // highScoreGameList = FXCollections.observableArrayList();

        //get all the files on that path
        File file = new File(Paths.get("./savegames/JSON").toString());
        File[] files = file.listFiles();

        List<HighScoreObject> higScoreList = new ArrayList<>();


        String newValue;
        //use this to get values out of json file
        JSONParser jsonparser = new JSONParser();

        if (files != null) {
            //as long as file exists
            for (File f : files) {
                newValue = f.getName().substring(0, f.getName().length() - 5);

                //einlesen von werten
                try {


                    // get values string and get points,
                    //read in the current document
                    Object obj = jsonparser.parse(new FileReader("./savegames/JSON/" + newValue + ".json"));
                    JSONObject gameinfos = (JSONObject) obj;

                    //decide if game is already finished
                    /*
                    Strinng gameFinished;
                    if(gameinfos.containsKey("GameFinished")){
                        //wenn existiert
                        gameFinished =   Integer.parseInt(String.valueOf(gameinfos.get("GameFinished")));
                    } else {
                        // If doesn't exist, do nothing
                        //defnine in accordance with collegues
                        gameFinished = 0;}
                     If (gamefinisehd != 0){
                     */


                    HighScoreObject hSO = CreateHighScoreObject.fillListView(gameinfos);
                    if (hSO == null) {
                    } else {
                        higScoreList.add(hSO);
                    }

                    /* }
                     */

                } catch (IOException | ParseException e) {
                    e.printStackTrace();

                }

            }
        }
        //Sort with the help of implementet comparator
        Collections.sort(higScoreList, HighScoreObject.aHighScore);
        for (HighScoreObject str : higScoreList) {
            System.out.println(str.getHighScore());
            highScoreGameList.add(str.getGameName()); //+ ": " + str.getHighScore());
        }


        // HighScoreObject test = new HighScoreObject( Long.valueOf(10000), "testgame", Long.valueOf(1000343230),23, 3, "lol", "thislast");

        ObservableList<HighScoreObject> higScore = FXCollections.observableArrayList(higScoreList); //FXCollections.observableArrayList(higScoreList);


        return higScore;


    }

    public void displaySelected(MouseEvent mouseEvent) {
        HighScoreObject hSO = highScore.getSelectionModel().getSelectedItem();
        if (hSO == null) {
            System.out.println("nothinng selected");
        } else {
            label.setText(hSO.getGameName());

                System.out.println(hSO.getGameName());

            }
        }


    }


