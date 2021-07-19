import at.jku.se.controller.HighScore.CalculateScore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

    class CalculateScoreTest{
        //Test high score


        // testen der Unterschiedlichen Schwierigkeiten -> Samurai
        @Test
        void validDifficultyHard() {
            Long time = Long.valueOf(3516743);
            Long points = CalculateScore.calculateScore(time, 2, 0, "schwer",  "rbSaSamurai");
            assertEquals(17975, points);
        }
        @Test
        void validDifficultyEasy() {
            Long time = Long.valueOf(3516743);
            Long points = CalculateScore.calculateScore(time, 2, 0, "leicht",  "rbSaSamurai");
            assertEquals(5975, points);
        }
        @Test
        void validDifficultyMedium() {
            Long time = Long.valueOf(3516743);
            Long points = CalculateScore.calculateScore(time, 2, 0, "mittel",  "rbSaSamurai");
            assertEquals(11975, points);
        }
        @Test
        void validSpieltypNoDifficulty() {
            Long time = Long.valueOf(3516743);
            Long points = CalculateScore.calculateScore(time, 0, 0, "other",  "rbSaSamurai");
            assertEquals(0, points);
        }



        //Testen Game type
        @Test
        void validSpieltypRegulaer() {
            Long time = Long.valueOf(3516743);
            Long points = CalculateScore.calculateScore(time, 0, 0, "leicht",  "rbSaRegulaer");
            assertEquals(2995, points);
        }
        @Test
        void validSpieltypSamurai() {
            Long time = Long.valueOf(3516743);
            Long points = CalculateScore.calculateScore(time, 0, 0, "leicht",  "rbSaSamurai");
            assertEquals(5995, points);
        }
        @Test
        void validSpieltypFreiform() {
            Long time = Long.valueOf(3516743);
            Long points = CalculateScore.calculateScore(time, 0, 0, "leicht",  "rbSaFreiform");
            assertEquals(8995, points);
        }
        @Test
        void validSpieltypNoForm() {
            Long time = Long.valueOf(3516743);
            Long points = CalculateScore.calculateScore(time, 0, 0, "leicht",  "other");
            assertEquals(0, points);
        }


        // Clicks deduction
        @Test
        void validHighScoreClicks() {
            Long time = Long.valueOf(3516743);
            Long points = CalculateScore.calculateScore(time, 0, 0, "mittel",  "rbSaSamurai");
            assertEquals(11995, points);
        }
        @Test
        void validHighScoreClicks1() {
            Long time = Long.valueOf(3516743);
            Long points = CalculateScore.calculateScore(time, 1, 0, "mittel",  "rbSaSamurai");
            assertEquals(11985, points);
        }

        @Test
        void deductionTime() {
            Long time =  Long.valueOf(3516743);
            Long points = CalculateScore.calculateScore(time, 2, 0, "leicht",  "rbSaSamurai");
            assertEquals(5975, points);
        }


        @Test
        void deductionHintsNo() {
            Long time = Long.valueOf(3516743);
            Long points = CalculateScore.calculateScore(time, 0, 0, "leicht",  "rbSaSamurai");
            assertEquals(5995, points);
        }
        @Test
        void deductionHintsYes() {
            Long time = Long.valueOf(3516743);
            Long points = CalculateScore.calculateScore(time, 0, 1, "leicht",  "rbSaSamurai");
            assertEquals(5745, points);
        }

        @Test
        void negativePoints() {
            Long time = Long.valueOf(3516743);
            Long points = CalculateScore.calculateScore(time, 0, 999, "leicht",  "rbSaRegulaer");
            assertEquals(0, points);
        }






        @Test
        void SaveInTest() {

            /*
            JSONObject saveGame = new JSONObject();
            JSONObject newFreiform = new JSONObject();
            String file ="ttest";
            String playerName="teest";

            saveGame.put("FileName", file);
            saveGame.put("version", "");
            saveGame.put("generateType", "automatic");
            saveGame.put("difficulty","easy");
            saveGame.put("player", "JUnit");
            saveGame.put("time","" + "1234");

            try(
                    FileWriter saveFile=new FileWriter("savegames/JSON/"+file+".json")

            ) {saveFile.write(saveGame.toJSONString());
            } catch (IOException e) {
            }




             */
        }



    }

