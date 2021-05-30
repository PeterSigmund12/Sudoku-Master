package at.jku.se.utility;
import java.util.Comparator;

public class HighScoreObject {
    Long highScore;
    String gameName;
    //Zeit
    //schwierigkeit


    public HighScoreObject(Long highScore, String gameName) {
        this.highScore = highScore;
        this.gameName = gameName;
    }

    public Long getHighScore() {
        return highScore;
    }

    public void setHighScore(Long highScore) {
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
            Long highScore1 = o1.getHighScore();
            Long highScore2 = o2.getHighScore();

            /*For ascending order*/
            // return highScore1-highScore2;

            /*For descending order*/
            return Math.toIntExact(highScore2 - highScore1);

        }
    };

}




