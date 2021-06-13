package at.jku.se.controller;

import at.jku.se.utility.HighScoreObject;
import at.jku.se.utility.NewScreen;
import javafx.application.Platform;
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
import java.text.SimpleDateFormat;
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
    public Long calculateScore(Long time, int clicks, int hints, String difficulty, String version) {
        Long availablePoints;

        System.out.println(difficulty);
        //Points for each difficulty
        switch(difficulty)
        {
            case "leicht":
                System.out.println("leicht");
              availablePoints = Long.valueOf(SCORE_LEICHT);
                break;
            case "mittel":
                System.out.println("mittel");
                availablePoints = Long.valueOf(SCORE_MITTEL);
                break;
            case "schwer":
                System.out.println("schwer");
                availablePoints = Long.valueOf(SCORE_SCHWER);
                break;

                default:
                System.out.println("no match");
                availablePoints = Long.valueOf(0);
        }
        System.out.println(availablePoints + " 4");
        switch(version)
        {
            case "rbSaRegulaer":
                System.out.println("rbSaregulaer");
                availablePoints =  availablePoints*MULTIP_FREI;
                break;
            case "rbSaFreiform":
                System.out.println("rbSaFreiform");
                availablePoints =  availablePoints*MULTIP_FREIF;
                break;
            case "rbSaSamurai":
                System.out.println("rbSaSamurai");
                availablePoints =  availablePoints * MULTIP_SAM;
                break;
            default:
                System.out.println("no match");
                availablePoints = Long.valueOf(0);
        }

        System.out.println(availablePoints + " 1");
        //Reduction of points
        //Reduction
        availablePoints = availablePoints - clicks * POINTS_MINUS_CLICKS;
        System.out.println(availablePoints+ " 2");
        //points deducted for time played


        final Date timerCurrent = new Date(0);
        final Date timerData = new Date(time);

        Date diff = new Date(timerCurrent.getTime() - timerData.getTime());


        System.out.println(diff);

        System.out.println(time);
        System.out.println("sollte auslesen" + time);


        availablePoints = availablePoints - time * POINTS_MINUS_TIME;


        System.out.println(availablePoints + " 3");
        //reduction for hints that were used in the game
        availablePoints = availablePoints - hints* POINTS_MINUS_HINTS;
        System.out.println(availablePoints + " 4");
        // TODO verify if ok
        if (availablePoints < 0) {
            availablePoints = Long.valueOf(0);
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
                    System.out.println(clicks);

                    //long
                    Long zeit;
                    if(gameinfos.containsKey("time")){
                        //wenn existiert
                         zeit =   Long.valueOf(String.valueOf(gameinfos.get("time")));
                    } else {
                        // If doesn't exist, do nothing
                         zeit = Long.valueOf(0);
                        System.out.println("doesnt exist t");
                    }
                    System.out.println(zeit);


                    int hints;
                    if(gameinfos.containsKey("hints")){
                        //wenn existiert
                        hints =   Integer.parseInt(String.valueOf(gameinfos.get("hints")));
                    } else {
                        // If doesn't exist, do nothing
                        hints = 0;
                        System.out.println("doesnt exist h");
                    }
                    System.out.println(hints);

                    String difficulty;
                    if(gameinfos.containsKey("difficulty")){
                        //wenn existiert
                        difficulty =   String.valueOf(gameinfos.get("difficulty"));
                    } else {
                        // If doesn't exist, do nothing
                        difficulty = "na";
                        System.out.println("doesnt exist d");
                    }

                    String version;
                    if(gameinfos.containsKey("version")){
                        //wenn existiert
                        version =   String.valueOf(gameinfos.get("version"));
                    } else {
                        // If doesn't exist, do nothing
                        version = "na";
                        System.out.println("doesnt exist v");
                    }

                   Long pointsInt = calculateScore( zeit,  clicks,  hints,  difficulty, version);

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




                    //zeit

                    Long timestamp;
                    if(gameinfos.containsKey("time")){
                        //wenn existiert
                        timestamp =   Long.valueOf(String.valueOf(gameinfos.get("time")));
                    } else {
                        // If doesn't exist, do nothing
                        timestamp = Long.valueOf(0);
                    }

                    long millis = timestamp % 1000;
                    long second = (timestamp / 1000) % 60;
                    long minute = (timestamp / (1000 * 60)) % 60;
                    long hour = (timestamp / (1000 * 60 * 60)) % 24;
                    String time = String.format("%02d:%02d:%02d", hour, minute, second);

                    lbTime.setText(time);
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






