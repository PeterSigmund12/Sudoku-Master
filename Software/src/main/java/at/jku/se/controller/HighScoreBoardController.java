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


    static final int SCORE_LEICHT = 3000;
    static final int SCORE_MITTEL = 6000;
    static final int SCORE_SCHWER = 9000;
    static final int POINTS_MINUS_TIME = 5;
    static final int POINTS_MINUS_CLICKS = 10;
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
     * @return
     */
    public int calculateScore( int time, int clicks, int hints, String difficulty) {
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

        availablePoints = availablePoints - clicks* POINTS_MINUS_CLICKS;

        //TODO -Zeit
        availablePoints = availablePoints - time* POINTS_MINUS_TIME;

        availablePoints = availablePoints - hints* POINTS_MINUS_HINTS;

        // TODO verify if ok
        if (availablePoints < 0) {
            availablePoints = 0;
        }
        return availablePoints;
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
                    /*String points = "0";
                    points = (String) gameinfos.get("Score");

                    int pointsInt = Optional.ofNullable(points)
                            .map(Ints::tryParse)
                            .orElse(0);
                     */
                    //FIXME
                    // -Remove points
                    // -Sicherstellen, dass alle werte existieren
                    // -Aufruf Methode


                  Integer i =   Integer.parseInt(String.valueOf(gameinfos.get("Clicks")));

                    int clicks;
                    if (i != null ){
                        clicks = i;
                    } else {clicks = 0;}
                     /*
                  int i =   Integer.parseInt(String.valueOf(gameinfos.get("Zeit")));
                    if ( i != 0){
                         = (int) gameinfos.get("Zeit");
                    } else {i = 0;}
                    int hints;
                    if (gameinfos.get("hints")!= null){
                   //     hints = (int) gameinfos.get("hints");
                    } {hints = 0;}
                    int difficulty;
                    if (gameinfos.get("difficulty")!= null){
                      //  difficulty = (int) gameinfos.get("difficulty");
                    }  {difficulty = 0;}
                    System.out.println(time);
                    System.out.println(clicks);
                    System.out.println(hints);
                    System.out.println(difficulty);


 */
         //           System.out.println(time);


                  //  System.out.println(time);
                    int pointsInt=0;

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


                   File imgfile = new File("./savegames/img/"+name+".png");
                   Image image = new Image(imgfile.toURI().toString());
                   // lbGameName.setText(name);

                    //TODO search in liste for namen und rufe dann in der liste den score auf.

                    lbVersion.setText((String)gameinfos.get("version"));
                    lbScore.setText((String)gameinfos.get("Score"));
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






