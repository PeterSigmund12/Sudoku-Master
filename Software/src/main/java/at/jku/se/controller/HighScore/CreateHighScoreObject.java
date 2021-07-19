package at.jku.se.controller.HighScore;

import at.jku.se.utility.HighScoreObject;
import org.json.simple.JSONObject;



public class CreateHighScoreObject {

    /**
     * This method is responsible for taking the individual JSONObjects that have been read in and creating a high
     * @param gameinfos Contains a JSONObject that was crerated frorm a JSON game File.
     * @return returns a HighScoreObject
     */
    public static HighScoreObject fillListView(JSONObject gameinfos) {
        Long highScore;
        String gameName;
        Long zeit;
        int clicks;
        int hints;
        String difficulty;
        String version;
        String player;

        gameName = (String) gameinfos.get("FileName");

        if(gameinfos.containsKey("Clicks")){
            //wenn existiert
            clicks =   Integer.parseInt(String.valueOf(gameinfos.get("Clicks")));
        } else {
            // If doesn't exist, do nothing
            clicks = 0;
        }
        if(gameinfos.containsKey("time")){
            //wenn existiert
            zeit =   Long.valueOf(String.valueOf(gameinfos.get("time")));
        } else {
            // If doesn't exist, do nothing
            zeit = Long.valueOf(0);
        }

        if(gameinfos.containsKey("hints")){
            //wenn existiert
            hints =   Integer.parseInt(String.valueOf(gameinfos.get("hints")));
        } else {
            // If doesn't exist, do nothing
            hints = 0;
        }

        if(gameinfos.containsKey("difficulty")){
            //wenn existiert
            difficulty =   String.valueOf(gameinfos.get("difficulty"));
        } else {
            // If doesn't exist, do nothing
            difficulty = "na";
        }

        if(gameinfos.containsKey("version")){
            //wenn existiert
            version =   String.valueOf(gameinfos.get("version"));
        } else {
            // If doesn't exist, do nothing
            version = "na";
        }
        if(gameinfos.containsKey("player")){
            //wenn existiert
            player =   String.valueOf(gameinfos.get("player"));
        } else {
            // If doesn't exist, do nothing
            player = "na";
        }

        highScore = CalculateScore.calculateScore( zeit,  clicks,  hints,  difficulty, version);
        HighScoreObject hSO = new HighScoreObject( highScore,  gameName ,  zeit,  clicks,  hints,  difficulty, version, player);
        return hSO;

    }
}
