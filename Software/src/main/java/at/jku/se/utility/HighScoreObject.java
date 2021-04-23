package at.jku.se.utility;
import java.util.Comparator;

public class HighScoreObject {
    int HighScore;
    String GameName;
    //Zeit
    //schwierigkeit


    public HighScoreObject(int highScore, String gameName) {
        HighScore = highScore;
        GameName = gameName;
    }

    public int getHighScore() {
        return HighScore;
    }

    public void setHighScore(int highScore) {
        HighScore = highScore;
    }

    public String getGameName() {
        return GameName;
    }

    public void setGameName(String gameName) {
        GameName = gameName;
    }

    public static Comparator<HighScoreObject> aHighScore = new Comparator<HighScoreObject>() {
        public int compare(HighScoreObject o1, HighScoreObject o2) {
            int highScore1 = o1.getHighScore();
            int highScore2 = o2.getHighScore();

            /*For ascending order*/
            // return highScore1-highScore2;

            /*For descending order*/
            return highScore2 - highScore1;

        }
    };

}




