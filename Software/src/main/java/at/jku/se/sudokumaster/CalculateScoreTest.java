package at.jku.se.sudokumaster;

import at.jku.se.controller.HighScore.CalculateScore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculateScoreTest{

    @Test
    void validHighScore() {
        Long time = new Long(Long.valueOf(3591991));
        Long points = CalculateScore.calculateScore(time, 0, 0, "mittel",  "rbSaSamurai");


        assertEquals(11995, points);
    }
    

}