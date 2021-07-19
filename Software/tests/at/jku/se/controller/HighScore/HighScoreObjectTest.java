package at.jku.se.controller.HighScore;

import at.jku.se.utility.HighScoreObject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class HighScoreObjectTest implements Initializable {

    @FXML
    ObservableList<String> highScoreGameList;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    @Test
        void validHighScoreObjectHighScore()  {
        String difficulty = "leicht";
        String gameName = "Testing Game";
        int clicks = 0;
        int hints = 0;
        String version = "rbSaRegulaer";
        String player = "Test Player";

        Long time = Long.valueOf(3516743);
        Long highScore = CalculateScore.calculateScore(time, clicks, hints, difficulty,  version);
        assertEquals(2995, highScore);
        HighScoreObject hSO = new HighScoreObject( highScore,  gameName ,  time,  clicks,  hints,  difficulty, version, player);

            assertEquals(2995, hSO.getHighScore());
            hSO.setHighScore((long) 2994);
            assertEquals(2994, hSO.getHighScore());
    }
    @Test
    void validHighScoreObjectDifficulty()  {
        String difficulty = "leicht";
        String gameName = "Testing Game";
        int clicks = 0;
        int hints = 0;
        String version = "rbSaRegulaer";
        String player = "Test Player";

        Long time = Long.valueOf(3516743);
        Long highScore = CalculateScore.calculateScore(time, clicks, hints, difficulty,  version);
        assertEquals(2995, highScore);
        HighScoreObject hSO = new HighScoreObject( highScore,  gameName ,  time,  clicks,  hints,  difficulty, version, player);
        assertEquals("leicht", hSO.getDifficulty());
        hSO.setDifficulty("medium");
        assertEquals("medium", hSO.getDifficulty());
    }
    @Test
    void validHighScoreObjectVersion()  {
        String difficulty = "leicht";
        String gameName = "Testing Game";
        int clicks = 0;
        int hints = 0;
        String version = "rbSaRegulaer";
        String player = "Test Player";

        Long time = Long.valueOf(3516743);
        Long highScore = CalculateScore.calculateScore(time, clicks, hints, difficulty,  version);
        assertEquals(2995, highScore);
        HighScoreObject hSO = new HighScoreObject( highScore,  gameName ,  time,  clicks,  hints,  difficulty, version, player);
        assertEquals("rbSaRegulaer", hSO.getVersion());
        hSO.setVersion("rbSaSamurai");
        assertEquals("rbSaSamurai", hSO.getVersion());
    }
    @Test
    void validHighScorePlayer()  {
        String difficulty = "leicht";
        String gameName = "Testing Game";
        int clicks = 0;
        int hints = 0;
        String version = "rbSaRegulaer";
        String player = "Test Player";

        Long time = Long.valueOf(3516743);
        Long highScore = CalculateScore.calculateScore(time, clicks, hints, difficulty,  version);
        assertEquals(2995, highScore);
        HighScoreObject hSO = new HighScoreObject( highScore,  gameName ,  time,  clicks,  hints,  difficulty, version, player);
        assertEquals("Test Player", hSO.getPlayer());
        hSO.setPlayer("set Player test");
        assertEquals("set Player test", hSO.getPlayer());
    }
    @Test
    void validHighScoreZeit()  {
        String difficulty = "leicht";
        String gameName = "Testing Game";
        int clicks = 0;
        int hints = 0;
        String version = "rbSaRegulaer";
        String player = "Test Player";

        Long time = Long.valueOf(3516743);
        Long highScore = CalculateScore.calculateScore(time, clicks, hints, difficulty,  version);
        assertEquals(2995, highScore);
        HighScoreObject hSO = new HighScoreObject( highScore,  gameName ,  time,  clicks,  hints,  difficulty, version, player);
        assertEquals(Long.valueOf(3516743), hSO.getZeit());
        hSO.setZeit(Long.valueOf(3516742));
        assertEquals(Long.valueOf(3516742), hSO.getZeit());
    }
    @Test
    void validHighScoreHint()  {
        String difficulty = "leicht";
        String gameName = "Testing Game";
        int clicks = 0;
        int hints = 0;
        String version = "rbSaRegulaer";
        String player = "Test Player";

        Long time = Long.valueOf(3516743);
        Long highScore = CalculateScore.calculateScore(time, clicks, hints, difficulty,  version);
        assertEquals(2995, highScore);
        HighScoreObject hSO = new HighScoreObject( highScore,  gameName ,  time,  clicks,  hints,  difficulty, version, player);
        assertEquals(0, hSO.getHints());
        hSO.setHints(1);
        assertEquals(1, hSO.getHints());;
    }
    @Test
    void validHighScoreClicks()  {
        String difficulty = "leicht";
        String gameName = "Testing Game";
        int clicks = 0;
        int hints = 0;
        String version = "rbSaRegulaer";
        String player = "Test Player";

        Long time = Long.valueOf(3516743);
        Long highScore = CalculateScore.calculateScore(time, clicks, hints, difficulty,  version);
        assertEquals(2995, highScore);
        HighScoreObject hSO = new HighScoreObject( highScore,  gameName ,  time,  clicks,  hints,  difficulty, version, player);
        assertEquals(0, hSO.getClicks());
        hSO.setClicks(1);
        assertEquals(1, hSO.getClicks());
    }

    @Test
    void validHighScoreTime()  {
        String difficulty = "leicht";
        String gameName = "Testing Game";
        int clicks = 0;
        int hints = 0;
        String version = "rbSaRegulaer";
        String player = "Test Player";

        Long time = Long.valueOf(3516743);
        Long highScore = CalculateScore.calculateScore(time, clicks, hints, difficulty,  version);
        assertEquals(2995, highScore);
        HighScoreObject hSO = new HighScoreObject( highScore,  gameName ,  time,  clicks,  hints,  difficulty, version, player);
        assertEquals("00:01:23", hSO.getTime());
        hSO.setTime("00:02:23");
        assertEquals("00:02:23", hSO.getTime());
    }
    @Test
    void validHighScoreGameName()  {
        String difficulty = "leicht";
        String gameName = "Testing Game";
        int clicks = 0;
        int hints = 0;
        String version = "rbSaRegulaer";
        String player = "Test Player";

        Long time = Long.valueOf(3516743);
        Long highScore = CalculateScore.calculateScore(time, clicks, hints, difficulty,  version);
        assertEquals(2995, highScore);
        HighScoreObject hSO = new HighScoreObject( highScore,  gameName ,  time,  clicks,  hints,  difficulty, version, player);
        assertEquals("Testing Game", hSO.getGameName());
        hSO.setGameName("Testing Game SET");
        assertEquals("Testing Game SET", hSO.getGameName());
    }
    @Test
    void validHighScoreHumanReadable()  {
        String difficulty = "leicht";
        String gameName = "Testing Game";
        int clicks = 0;
        int hints = 0;
        String version = "rbSaRegulaer";
        String player = "Test Player";

        Long time = Long.valueOf(3516743);
        Long highScore = CalculateScore.calculateScore(time, clicks, hints, difficulty,  version);
        assertEquals(2995, highScore);
        HighScoreObject hSO = new HighScoreObject( highScore,  gameName ,  time,  clicks,  hints,  difficulty, version, player);
        assertEquals("Regulaer", hSO.getVersionReadable());
        hSO.setVersionReadable("Samurai");
        assertEquals("Samurai", hSO.getVersionReadable());
    }
    @Test
    void validHighScoreHumanReadableSamurai()  {
        String difficulty = "leicht";
        String gameName = "Testing Game";
        int clicks = 0;
        int hints = 0;
        String version = "rbSaSamurai";
        String player = "Test Player";

        Long time = Long.valueOf(3516743);
        Long highScore = CalculateScore.calculateScore(time, clicks, hints, difficulty,  version);
        HighScoreObject hSO = new HighScoreObject( highScore,  gameName ,  time,  clicks,  hints,  difficulty, version, player);
        assertEquals("Samurai", hSO.getVersionReadable());
    }

    @Test
    void validHighScoreHumanReadableFreiform()  {
        String difficulty = "leicht";
        String gameName = "Testing Game";
        int clicks = 0;
        int hints = 0;
        String version = "rbSaFreiform";
        String player = "Test Player";

        Long time = Long.valueOf(3516743);
        Long highScore = CalculateScore.calculateScore(time, clicks, hints, difficulty,  version);
        HighScoreObject hSO = new HighScoreObject( highScore,  gameName ,  time,  clicks,  hints,  difficulty, version, player);
        assertEquals("Freiform", hSO.getVersionReadable());
    }


            /*
            List<HighScoreObject> higScoreList = new ArrayList<>();
            highScoreGameList.add(hSO.getGameName()); //+ ": " + str.getHighScore());
            HighScoreObject hSO0 = new HighScoreObject( Long.valueOf(0),  gameName ,  time,  clicks,  hints,  difficulty, version, player);
            Collections.sort(higScoreList, HighScoreObject.aHighScore);
            for (HighScoreObject str : higScoreList) {
                highScoreGameList.add(str.getGameName()); //+ ": " + str.getHighScore());
            }
            System.out.println(higScoreList.get(1));
            //assertEquals(higScoreList.get(0), hSO0.getGameName());


        }
  */

}
