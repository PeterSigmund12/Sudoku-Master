package at.jku.se.controller.HighScore;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateScoreTest {

    @Test
    void validHighScore() {
        Long time = new Long(Long.valueOf(3591991));
        Long points = CalculateScore.calculateScore(time, 0, 0, "mittel",  "rbSaSamurai");


        assertEquals(11995, points);
    }
    @Test
    void validHighScoreClicks() {
        Long time = new Long(Long.valueOf(3591991));
        Long points = CalculateScore.calculateScore(time, 1, 0, "mittel",  "rbSaSamurai");


        assertEquals(11985, points);
    }
}