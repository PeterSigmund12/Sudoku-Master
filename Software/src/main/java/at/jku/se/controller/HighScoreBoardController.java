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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    //Initiale Punkte
    //Punkte für schwierigkeit
    static final int SCORE_LEICHT = 3000;
    static final int SCORE_MITTEL = 6000;
    static final int SCORE_SCHWER = 9000;
    //Punkte für Komplexität
    static final int MULTIP_FREI = 1;
    static final int MULTIP_SAM = 2;
    static final int MULTIP_FREIF = 3;

    //Abzüge
    //Wieviel abzug pro Minute (Sekunde/60)
    static final int POINTS_MINUS_TIME = 5;
    //Abzüge für clicks
    static final int POINTS_MINUS_CLICKS = 10;
    //Abzüge für Hints
    static final int POINTS_MINUS_HINTS = 250;



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
    public void handleButtonBackHighMainMen(ActionEvent event) throws IOException {
        NewScreen.openNewScreen(event, "/fxml/mainmenue.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillListView();
    }

    /**
     *
     *
     * @param time Conveys how much time the player has spend on the Game
     * @param clicks Contains how many moves the player has made
     * @param hints Contains how many hints the player has used
     * @param difficulty Contains the difficulty level of the game
     * @return the high score a user achieved
     */
    public int calculateScore( int time, int clicks, int hints, String difficulty, String version) {
        int availablePoints;

        //Points for each difficulty
        switch(difficulty)
        {
            case "leicht":
                System.out.println("leicht");
              availablePoints =  SCORE_LEICHT;
                break;
            case "mittel":
                System.out.println("mittel");
                availablePoints =  SCORE_MITTEL;
                break;
            case "schwer":
                System.out.println("schwer");
                availablePoints =  SCORE_SCHWER;
                break;
                default:
                System.out.println("no match");
                availablePoints = 0;
        }
        System.out.println(availablePoints + " 4");
        switch(version)
        {
            case "rb_Sa_regulaer":
                System.out.println("rb_Sa_regulaer");
                availablePoints =  availablePoints*MULTIP_FREI;
                break;
            case "rb_Sa_Freiform":
                System.out.println("rb_Sa_Freiform");
                availablePoints =  availablePoints*MULTIP_FREIF;
                break;
            case "rb_Sa_Samurai":
                System.out.println("rb_Sa_Samurai");
                availablePoints =  availablePoints * MULTIP_SAM;
                break;
            default:
                System.out.println("no match");
                availablePoints = 0;
        }

        System.out.println(availablePoints + " 1");
        //Reduction of points
        //Reduction
        availablePoints = availablePoints - clicks * POINTS_MINUS_CLICKS;
        System.out.println(availablePoints+ " 2");
        //points deducted for time played
        availablePoints = availablePoints - (time/60) * POINTS_MINUS_TIME;
        System.out.println(availablePoints + " 3");
        //reduction for hints that were used in the game
        availablePoints = availablePoints - hints* POINTS_MINUS_HINTS;
        System.out.println(availablePoints + " 4");
        // TODO verify if ok
        if (availablePoints < 0) {
            availablePoints = 0;
        }
        System.out.println(availablePoints + " total points");
        return availablePoints;
    }


    /**
     *
     */
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

                    int clicks;
                    if(gameinfos.containsKey("Clicks")){
                        //wenn existiert
                        clicks =   Integer.parseInt(String.valueOf(gameinfos.get("Clicks")));
                    } else {
                        // If doesn't exist, do nothing
                        clicks = 0;
                    }

                    //long
                    int zeit;
                    if(gameinfos.containsKey("Zeit")){
                        //wenn existiert
                         zeit =   Integer.parseInt(String.valueOf(gameinfos.get("Zeit")));
                    } else {
                        // If doesn't exist, do nothing
                         zeit = 0;
                        System.out.println("doesnt exist");
                    }
                    int hints;
                    if(gameinfos.containsKey("hints")){
                        //wenn existiert
                        hints =   Integer.parseInt(String.valueOf(gameinfos.get("hints")));
                    } else {
                        // If doesn't exist, do nothing
                        hints = 0;
                        System.out.println("doesnt exist");
                    }
                    String difficulty;
                    if(gameinfos.containsKey("difficulty")){
                        //wenn existiert
                        difficulty =   String.valueOf(gameinfos.get("difficulty"));
                    } else {
                        // If doesn't exist, do nothing
                        difficulty = "na";
                        System.out.println("doesnt exist");
                    }
                    String version;
                    if(gameinfos.containsKey("version")){
                        //wenn existiert
                        version =   String.valueOf(gameinfos.get("version"));
                    } else {
                        // If doesn't exist, do nothing
                        version = "na";
                        System.out.println("doesnt exist");
                    }

                   int pointsInt = calculateScore( zeit,  clicks,  hints,  difficulty, version);

                    System.out.println(pointsInt + "total points");
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
            highScoreGameList.add(str.getGameName() ); //+ ": " + str.getHighScore());
        }


        lvHighScoreGames.setItems(highScoreGameList);
        lvHighScoreGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               // brauche ich herzlich wenig btnContinue.setDisable(false);
                selected = newValue;
                System.out.println(newValue);

                try {
                    Object obj =   jsonparser.parse(new FileReader("./savegames/JSON/"+newValue+".json"));
                    JSONObject gameinfos = (JSONObject) obj;
                    String name = (String)gameinfos.get("FileName");

                    HighScoreObject customer;
                            HighScoreObject test = higScoreList.stream()
                            .filter(HighScoreObject -> name.equals(HighScoreObject.getGameName()))
                            .findAny()
                            .orElse(null);

                            System.out.println(test.getGameName());
                            System.out.println(test.getHighScore());

                    System.out.println(test.getGameName());


                   File imgfile = new File("./savegames/img/"+name+".png");
                   Image image = new Image(imgfile.toURI().toString());
                   // lbGameName.setText(name);

                    //TODO search in liste for namen und rufe dann in der liste den score auf.
                    lbVersion.setText((String)gameinfos.get("version"));
                    lbScore.setText(String.valueOf(test.getHighScore()));
                    lbDiff.setText((String)gameinfos.get("difficulty"));
                    lbTime.setText((String)gameinfos.get("Zeit"));
                    lbGen.setText((String)gameinfos.get("generateType"));
                    lbPlayer.setText((String)gameinfos.get("Player"));
                    ivSavegame.setImage(image);

                } catch (IOException|ParseException e) {
                    e.printStackTrace();
                }


                selected = newValue;
            }
        });


    }


}






