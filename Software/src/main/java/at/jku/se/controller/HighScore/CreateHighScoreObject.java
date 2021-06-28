package at.jku.se.controller.HighScore;

import at.jku.se.utility.HighScoreObject;
import com.google.gson.JsonObject;
import org.json.simple.JSONObject;



public class CreateHighScoreObject {

    public static HighScoreObject fillListView(JSONObject gameinfos) {


        Long highScore;
        String gameName;
        Long zeit;
        int clicks;
        int hints;
        String difficulty;
        String version;

        gameName = (String) gameinfos.get("FileName");

        if(gameinfos.containsKey("Clicks")){
            //wenn existiert
            clicks =   Integer.parseInt(String.valueOf(gameinfos.get("Clicks")));
        } else {
            // If doesn't exist, do nothing
            clicks = 0;
            System.out.println("does not exist c");
        }


        if(gameinfos.containsKey("time")){
            //wenn existiert
            zeit =   Long.valueOf(String.valueOf(gameinfos.get("time")));
        } else {
            // If doesn't exist, do nothing
            zeit = Long.valueOf(0);
            System.out.println("doesnt exist t");
        }


        if(gameinfos.containsKey("hints")){
            //wenn existiert
            hints =   Integer.parseInt(String.valueOf(gameinfos.get("hints")));
        } else {
            // If doesn't exist, do nothing
            hints = 0;
            System.out.println("doesnt exist h");
        }


        if(gameinfos.containsKey("difficulty")){
            //wenn existiert
            difficulty =   String.valueOf(gameinfos.get("difficulty"));
        } else {
            // If doesn't exist, do nothing
            difficulty = "na";
            System.out.println("doesnt exist d");
        }

        if(gameinfos.containsKey("version")){
            //wenn existiert
            version =   String.valueOf(gameinfos.get("version"));
        } else {
            // If doesn't exist, do nothing
            version = "na";
            System.out.println("doesnt exist v");
        }

        
        highScore = CalculateScore.calculateScore( zeit,  clicks,  hints,  difficulty, version);

       HighScoreObject hSO = new HighScoreObject( highScore,  gameName ,  zeit,  clicks,  hints,  difficulty, version );
        return hSO;

    }
}
