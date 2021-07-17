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

        //Reduction of points
        //Reduction
        availablePoints = availablePoints - clicks * POINTS_MINUS_CLICKS;
        //points deducted for time played


        final Date timerCurrent = new Date(0);
        final Date timerData = new Date(time);


        Date diff2 = new Date( timerCurrent.getTime() -timerData.getTime() );
        System.out.println(diff2);

        long now = ((diff2.getMinutes()));
        System.out.println("testete" + diff2.getTime());
        System.out.println("testete" + now);

        //time
        availablePoints = availablePoints - 1 * POINTS_MINUS_TIME;


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
}
