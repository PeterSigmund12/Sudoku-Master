package at.jku.se.controller.HighScore;


import java.util.Date;

public class CalculateScore {


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

    /**
     *
     *Returns a long value containing the high score a player has achieved
     * calculated by using the provided input variables and in this class defined
     * constants.
     * @param time Conveys how much time the player has spend on the Game
     * @param clicks Contains how many moves the player has made
     * @param hints Contains how many hints the player has used
     * @param difficulty Contains the difficulty level of the game
     * @param version des
     * @return the high score a user has achieved
     */
    public static Long calculateScore(Long time, int clicks, int hints, String difficulty, String version) {
        Long availablePoints;
        //Points for each difficulty
        switch(difficulty)
        {
            case "leicht":
                availablePoints = Long.valueOf(SCORE_LEICHT);
                break;
            case "mittel":
                availablePoints = Long.valueOf(SCORE_MITTEL);
                break;
            case "schwer":
                availablePoints = Long.valueOf(SCORE_SCHWER);
                break;
            default:
                availablePoints = Long.valueOf(0);
        }
        switch(version)
        {
            case "rbSaRegulaer":
                availablePoints =  availablePoints*MULTIP_FREI;
                break;
            case "rbSaFreiform":
                availablePoints =  availablePoints*MULTIP_FREIF;
                break;
            case "rbSaSamurai":
                availablePoints =  availablePoints * MULTIP_SAM;
                break;
            default:
                availablePoints = Long.valueOf(0);
        }
        //Reduction of points
        //Reduction
        availablePoints = availablePoints - clicks * POINTS_MINUS_CLICKS;
        //points deducted for time played

        //time
        final Date timerCurrent = new Date(0);
        final Date timerData = new Date(time);
        Date diff2 = new Date( timerCurrent.getTime() -timerData.getTime() );
        Long timePoints = Long.valueOf(diff2.getMinutes() + diff2.getHours()/60 + (timerCurrent.getDay() -timerData.getDay())/24/60);
        availablePoints = availablePoints - timePoints * POINTS_MINUS_TIME;


        availablePoints = availablePoints - hints* POINTS_MINUS_HINTS;
        if (availablePoints < 0) {
            availablePoints = Long.valueOf(0);
        }
        return availablePoints;
    }
}
