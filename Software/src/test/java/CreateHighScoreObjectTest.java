import at.jku.se.controller.HighScore.CreateHighScoreObject;
import at.jku.se.utility.HighScoreObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CreateHighScoreObjectTest {
    @Test
    void validHighScoreObjectHighScore() {
        JSONObject saveGame = new JSONObject();
        JSONObject newFreiform = new JSONObject();
        String file = "Test";
        String playerName = "Test Player";
        String version = "rbSaRegulaer";
        String generateType = "automatic";
        String difficulty = "leicht";
        int hints = 1;
        int clicks = 1;
        Long time = Long.valueOf(3516743);

        saveGame.put("FileName", file);
        saveGame.put("version", version);
        saveGame.put("generateType", generateType);
        saveGame.put("difficulty", difficulty);
        saveGame.put("hints", hints);
        saveGame.put("Clicks", clicks);
        saveGame.put("player", playerName);
        saveGame.put("time", "" + time);
        try (
                FileWriter saveFile = new FileWriter("savegames/JSON/" + "Test" + ".json")
        ) {
            saveFile.write(saveGame.toJSONString());
        } catch (IOException e) {
        }

        JSONParser jsonparser = new JSONParser();
        Object obj = null;
        try {
            obj = jsonparser.parse(new FileReader("savegames/JSON/" + "Test" + ".json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject gameinfos = (JSONObject) obj;
        HighScoreObject hSO = CreateHighScoreObject.fillListView(gameinfos);
        assertEquals(file, hSO.getGameName());
        assertEquals(version, hSO.getVersion());
        assertEquals(difficulty, hSO.getDifficulty());
        assertEquals(hints, hSO.getHints());
        assertEquals(clicks, hSO.getClicks());
        assertEquals(playerName, hSO.getPlayer());
        assertEquals(time, hSO.getZeit());
    }

    @Test
    void validHighScoreObjectHighScoreNoClicks() {
        JSONObject saveGame = new JSONObject();
        JSONObject newFreiform = new JSONObject();
        String file = "Test";
        String playerName = "Test Player";
        String version = "rbSaRegulaer";
        String generateType = "automatic";
        String difficulty = "leicht";
        int hints = 1;
        Long time = Long.valueOf(3516743);

        saveGame.put("FileName", file);
        saveGame.put("version", version);
        saveGame.put("generateType", generateType);
        saveGame.put("difficulty", difficulty);
        saveGame.put("hints", hints);
        saveGame.put("player", playerName);
        saveGame.put("time", "" + time);
        try (
                FileWriter saveFile = new FileWriter("savegames/JSON/" + "Test" + ".json")
        ) {
            saveFile.write(saveGame.toJSONString());
        } catch (IOException e) {
        }

        JSONParser jsonparser = new JSONParser();
        Object obj = null;
        try {
            obj = jsonparser.parse(new FileReader("savegames/JSON/" + "Test" + ".json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject gameinfos = (JSONObject) obj;
        HighScoreObject hSO = CreateHighScoreObject.fillListView(gameinfos);
        assertEquals(file, hSO.getGameName());
        assertEquals(version, hSO.getVersion());
        assertEquals(difficulty, hSO.getDifficulty());
        assertEquals(hints, hSO.getHints());
        assertEquals(0, hSO.getClicks());
        assertEquals(playerName, hSO.getPlayer());
        assertEquals(time, hSO.getZeit());
    }

    @Test
    void validHighScoreObjectNoHints() {
        JSONObject saveGame = new JSONObject();
        JSONObject newFreiform = new JSONObject();
        String file = "Test";
        String playerName = "Test Player";
        String version = "rbSaRegulaer";
        String generateType = "automatic";
        String difficulty = "leicht";

        int clicks = 1;
        Long time = Long.valueOf(3516743);

        saveGame.put("FileName", file);
        saveGame.put("version", version);
        saveGame.put("generateType", generateType);
        saveGame.put("difficulty", difficulty);

        saveGame.put("Clicks", clicks);
        saveGame.put("player", playerName);
        saveGame.put("time", "" + time);
        try (
                FileWriter saveFile = new FileWriter("savegames/JSON/" + "Test" + ".json")
        ) {
            saveFile.write(saveGame.toJSONString());
        } catch (IOException e) {
        }

        JSONParser jsonparser = new JSONParser();
        Object obj = null;
        try {
            obj = jsonparser.parse(new FileReader("savegames/JSON/" + "Test" + ".json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject gameinfos = (JSONObject) obj;
        HighScoreObject hSO = CreateHighScoreObject.fillListView(gameinfos);
        assertEquals(file, hSO.getGameName());
        assertEquals(version, hSO.getVersion());
        assertEquals(difficulty, hSO.getDifficulty());
        assertEquals(0, hSO.getHints());
        assertEquals(clicks, hSO.getClicks());
        assertEquals(playerName, hSO.getPlayer());
        assertEquals(time, hSO.getZeit());
    }

    @Test
    void validHighScoreObjectNoTime() {
        JSONObject saveGame = new JSONObject();
        JSONObject newFreiform = new JSONObject();
        String file = "Test";
        String playerName = "Test Player";
        String version = "rbSaRegulaer";
        String generateType = "automatic";
        String difficulty = "leicht";
        int hints = 1;
        int clicks = 1;

        saveGame.put("FileName", file);
        saveGame.put("version", version);
        saveGame.put("generateType", generateType);
        saveGame.put("difficulty", difficulty);
        saveGame.put("hints", hints);
        saveGame.put("Clicks", clicks);
        saveGame.put("player", playerName);

        try (
                FileWriter saveFile = new FileWriter("savegames/JSON/" + "Test" + ".json")
        ) {
            saveFile.write(saveGame.toJSONString());
        } catch (IOException e) {
        }

        JSONParser jsonparser = new JSONParser();
        Object obj = null;
        try {
            obj = jsonparser.parse(new FileReader("savegames/JSON/" + "Test" + ".json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject gameinfos = (JSONObject) obj;
        HighScoreObject hSO = CreateHighScoreObject.fillListView(gameinfos);
        assertEquals(file, hSO.getGameName());
        assertEquals(version, hSO.getVersion());
        assertEquals(difficulty, hSO.getDifficulty());
        assertEquals(hints, hSO.getHints());
        assertEquals(clicks, hSO.getClicks());
        assertEquals(playerName, hSO.getPlayer());
        assertEquals(Long.valueOf(0), hSO.getZeit());
    }

    @Test
    void validHighScoreObjectNoDifficulty() {
        JSONObject saveGame = new JSONObject();
        JSONObject newFreiform = new JSONObject();
        String file = "Test";
        String playerName = "Test Player";
        String version = "rbSaRegulaer";
        String generateType = "automatic";
        int hints = 1;
        int clicks = 1;
        Long time = Long.valueOf(3516743);

        saveGame.put("FileName", file);
        saveGame.put("version", version);
        saveGame.put("generateType", generateType);
        saveGame.put("hints", hints);
        saveGame.put("Clicks", clicks);
        saveGame.put("player", playerName);
        saveGame.put("time", "" + time);
        try (
                FileWriter saveFile = new FileWriter("savegames/JSON/" + "Test" + ".json")
        ) {
            saveFile.write(saveGame.toJSONString());
        } catch (IOException e) {
        }

        JSONParser jsonparser = new JSONParser();
        Object obj = null;
        try {
            obj = jsonparser.parse(new FileReader("savegames/JSON/" + "Test" + ".json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject gameinfos = (JSONObject) obj;
        HighScoreObject hSO = CreateHighScoreObject.fillListView(gameinfos);
        assertEquals(file, hSO.getGameName());
        assertEquals(version, hSO.getVersion());
        assertEquals("na", hSO.getDifficulty());
        assertEquals(hints, hSO.getHints());
        assertEquals(clicks, hSO.getClicks());
        assertEquals(playerName, hSO.getPlayer());
        assertEquals(time, hSO.getZeit());
    }

    @Test
    void validHighScoreObjectHighScoreNoVersion() {
        JSONObject saveGame = new JSONObject();
        JSONObject newFreiform = new JSONObject();
        String file = "Test";
        String playerName = "Test Player";
        String generateType = "automatic";
        String difficulty = "leicht";
        int hints = 1;
        int clicks = 1;
        Long time = Long.valueOf(3516743);

        saveGame.put("FileName", file);
        saveGame.put("generateType", generateType);
        saveGame.put("difficulty", difficulty);
        saveGame.put("hints", hints);
        saveGame.put("Clicks", clicks);
        saveGame.put("player", playerName);
        saveGame.put("time", "" + time);
        try (
                FileWriter saveFile = new FileWriter("savegames/JSON/" + "Test" + ".json")
        ) {
            saveFile.write(saveGame.toJSONString());
        } catch (IOException e) {
        }

        JSONParser jsonparser = new JSONParser();
        Object obj = null;
        try {
            obj = jsonparser.parse(new FileReader("savegames/JSON/" + "Test" + ".json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject gameinfos = (JSONObject) obj;
        HighScoreObject hSO = CreateHighScoreObject.fillListView(gameinfos);
        assertEquals(file, hSO.getGameName());
        assertEquals("na", hSO.getVersion());
        assertEquals(difficulty, hSO.getDifficulty());
        assertEquals(hints, hSO.getHints());
        assertEquals(clicks, hSO.getClicks());
        assertEquals(playerName, hSO.getPlayer());
        assertEquals(time, hSO.getZeit());
    }

    @Test
    void validHighScoreObjectHighScoreNoPlayer() {
        JSONObject saveGame = new JSONObject();
        JSONObject newFreiform = new JSONObject();
        String file = "Test";
        String version = "rbSaRegulaer";
        String generateType = "automatic";
        String difficulty = "leicht";
        int hints = 1;
        int clicks = 1;
        Long time = Long.valueOf(3516743);

        saveGame.put("FileName", file);
        saveGame.put("version", version);
        saveGame.put("generateType", generateType);
        saveGame.put("difficulty", difficulty);
        saveGame.put("hints", hints);
        saveGame.put("Clicks", clicks);
        saveGame.put("time", "" + time);
        try (
                FileWriter saveFile = new FileWriter("savegames/JSON/" + "Test" + ".json")
        ) {
            saveFile.write(saveGame.toJSONString());
        } catch (IOException e) {
        }

        JSONParser jsonparser = new JSONParser();
        Object obj = null;
        try {
            obj = jsonparser.parse(new FileReader("savegames/JSON/" + "Test" + ".json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject gameinfos = (JSONObject) obj;
        HighScoreObject hSO = CreateHighScoreObject.fillListView(gameinfos);
        assertEquals(file, hSO.getGameName());
        assertEquals(version, hSO.getVersion());
        assertEquals(difficulty, hSO.getDifficulty());
        assertEquals(hints, hSO.getHints());
        assertEquals(clicks, hSO.getClicks());
        assertEquals("na", hSO.getPlayer());
        assertEquals(time, hSO.getZeit());
    }

    @AfterEach
    public void afterEach() {
        try {

            File jfile = new File("savegames/JSON/" + "Test" + ".json");
            if (jfile.exists()) {
                Files.delete(jfile.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();

        }


    }


}


