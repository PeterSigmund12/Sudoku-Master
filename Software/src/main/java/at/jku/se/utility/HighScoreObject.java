package at.jku.se.utility;
import java.util.Comparator;
import java.util.Date;

public class HighScoreObject {
    Long highScore;
    String gameName;
    Long zeit;
    int clicks;
    int hints;
    String difficulty;
    String version;
    String time;




    //Zeit
    //schwierigkeit


    public HighScoreObject(Long highScore, String gameName, Long zeit, int clicks, int hints, String difficulty, String version) {
        this.highScore = highScore;
        this.gameName = gameName;

        this.zeit = zeit;
        this.clicks = clicks;
        this.hints = hints;
        this.difficulty = difficulty;
        this.version = version;


        final Date timerCurrent = new Date(0);
        final Date timerData = new Date(zeit);
        Date diff2 = new Date(timerCurrent.getTime() - timerData.getTime());
        //Formating and priting
        long second = ((diff2.getSeconds()));
        long minute = ((diff2.getMinutes()));
        long hour = ((diff2.getHours()));
        String time = String.format("%02d:%02d:%02d", hour, minute, second);
        this.time=time;


    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getZeit() {
        return zeit;
    }

    public int getClicks() {
        return clicks;
    }

    public int getHints() {
        return hints;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getVersion() {
        return version;
    }

    public void setZeit(Long zeit) {
        this.zeit = zeit;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public void setHints(int hints) {
        this.hints = hints;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setVersion(String version) {
        this.version = version;
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




