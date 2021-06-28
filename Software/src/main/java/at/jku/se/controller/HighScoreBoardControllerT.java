package at.jku.se.controller;

import at.jku.se.controller.HighScore.CreateHighScoreObject;
import at.jku.se.utility.HighScoreObject;
import at.jku.se.utility.NewScreenDropDown;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class HighScoreBoardControllerT implements Initializable {

    @FXML
    public TextField screen;
    @FXML
    public Label label;
    @FXML
    private TableView<HighScoreObject> highScore;
    @FXML
    TableColumn<HighScoreObject, Long> scoreColumn;
    @FXML
    TableColumn<HighScoreObject,String> nameColumn;


    @FXML
    TableColumn<HighScoreObject, String> timeplayed;
    @FXML
    TableColumn<HighScoreObject, String> gameVersion;

    @FXML
    private MenuBar menuBar;

    @FXML
    public void handleButtonBacktoMain(ActionEvent event) throws IOException {

        Stage oldStage = (Stage) menuBar.getScene().getWindow();

        NewScreenDropDown.handleButtonBacktoMain(event, "/fxml/mainmenue.fxml", oldStage);

    }

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







    //gets all the highscores
    public ObservableList<HighScoreObject> getHighscorer(){
        ObservableList<String> highScoreGameList = FXCollections.observableArrayList();

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set up collumns
        nameColumn.setCellValueFactory(new PropertyValueFactory<HighScoreObject, String>("gameName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<HighScoreObject, Long>("highScore"));





        timeplayed.setCellValueFactory(new PropertyValueFactory<HighScoreObject, String>("time"));
        gameVersion.setCellValueFactory(new PropertyValueFactory<HighScoreObject, String>("version"));

        //load dummy datat
        highScore.setItems(getHighscorer());



       // HighScoreObject higScoreTest = highScore.getSelectionModel().getSelectedItem();
      //  System.out.println(higScoreTest.getGameName());


        // fillListView();
    }

    /**
     *
     */
    public void fillListView() {
        //Highscorre column
        TableColumn<HighScoreObject,Long> scoreColumn= new TableColumn<>("Points");
        //minimum with scoreColumn.setMinWidth(229)
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("highScore"));
        //Name column
        TableColumn<HighScoreObject,Long> nameColumn= new TableColumn<>("gameName");
        //minimum with scoreColumn.setMinWidth(229)
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("gameName"));


        TableColumn<HighScoreObject,Long> timeplayed= new TableColumn<>("Time Played");
        //minimum with scoreColumn.setMinWidth(229)
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("zeit"));
        TableColumn<HighScoreObject,Long> gameVersion= new TableColumn<>("Type of Playfield");
        //minimum with scoreColumn.setMinWidth(229)
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("gameVersion"));





        highScore = new TableView<>();
        highScore.setItems(getHighscorer());
        highScore.getColumns().addAll(nameColumn, scoreColumn, timeplayed, gameVersion);

    }


    public void displaySelected(MouseEvent mouseEvent) {
        HighScoreObject hSO = highScore.getSelectionModel().getSelectedItem();
        if(hSO==null){
            screen.setText("nothinng selected");
        }else {
            System.out.println(hSO.getGameName());
            screen.setText(hSO.getGameName());


            label.setText(hSO.getGameName());
            System.out.println(hSO.getHints());
        }
    }
}