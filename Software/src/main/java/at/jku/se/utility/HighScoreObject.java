package at.jku.se.utility;
import java.util.Comparator;

public class HighScoreObject {
    int highScore;
    String gameName;
    //Zeit
    //schwierigkeit


    public HighScoreObject(int highScore, String gameName) {
        this.highScore = highScore;
        this.gameName = gameName;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
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




