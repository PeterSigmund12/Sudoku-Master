package at.jku.se.controller;

import at.jku.se.utility.HighScoreObject;
import at.jku.se.utility.NewScreen;
import com.google.common.primitives.Ints;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;


public class HighScoreBoardController implements Initializable {
    @FXML
    private Button btnBackHighMainMen;

    @FXML
    ListView<String> lvHighScoreGames;

    ObservableList<String> highScoreGameList;

    String selected;

    @FXML
    private Label lbGameName;
    @FXML
    private Label lbGenerate;
    @FXML
    private Label lbDifficulty;
    @FXML
    private Label lbVersion;



    @FXML
    public void handleButtonBackHighMainMen(ActionEvent event) throws IOException {
        NewScreen.openNewScreen(event, "/fxml/mainmenue.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillListView();
    }

    public void fillListView() {
        highScoreGameList = FXCollections.observableArrayList();

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
                System.out.println(newValue);

                try {

                    // get values string and get points,
                    //read in the current document
                    Object obj = jsonparser.parse(new FileReader("./savegames/JSON/" + newValue + ".json"));
                    JSONObject gameinfos = (JSONObject) obj;

                    //access the file name of the document
                    String name = (String) gameinfos.get("FileName");

                    //make sure strring exists
                    String points = "0";

                    points = (String) gameinfos.get("Score");

                    int pointsInt = Optional.ofNullable(points)
                            .map(Ints::tryParse)
                            .orElse(0);

                    //create a new HighScore Object to store value
                    HighScoreObject hSObject = new HighScoreObject(pointsInt, name);
                    //add value to the highscore list
                    higScoreList.add(hSObject);

                } catch (IOException|ParseException e) {
                    e.printStackTrace();

                }


            }
        }
        //Sort with the help of implementet comparator
        Collections.sort(higScoreList, HighScoreObject.aHighScore);
        for (HighScoreObject str : higScoreList) {
            System.out.println(str.getHighScore());
            highScoreGameList.add(str.getGameName() + ": " + str.getHighScore());
        }


        lvHighScoreGames.setItems(highScoreGameList);
        lvHighScoreGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {


                selected = newValue;
            }
        });


    }


}






