package at.jku.se.controller;

import at.jku.se.utility.NewScreen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class LoadGameController implements Initializable {


    @FXML
    private AnchorPane root;

    @FXML
    ListView lvSaveGames;

    ObservableList<String> saveGameList;

    String selected;
    @FXML
    private Button btnImport;
    @FXML
    private Button btnExport;
    @FXML
    private Button btnContinue;
    @FXML
    private Button btnBackSavedMainMen;

    @FXML
    private Label lbGameName;
    @FXML
    private Label lbGenerate;
    @FXML
    private Label lbDifficulty;
    @FXML
    private Label lbVersion;

    @FXML
    private ImageView ivSavegame;

    @FXML
    public void handleButtonBackSavedMainMen(ActionEvent event) throws IOException {

        NewScreen.openNewScreen(event,"/fxml/mainmenue.fxml");

    }

    @FXML
    public void handleButtonImportGame(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Import Game");
        Stage stage = new Stage();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        File f =fc.showOpenDialog(stage);
        if(f.getName().substring(f.getName().length()-5).equals(".json")){


                File newFile = new File("savegames/JSON/"+ f.getName());
            try {
                Files.copy(f.toPath(),newFile.toPath());
            } catch (FileAlreadyExistsException e) {

                alert.setTitle("Error Message");
                alert.setHeaderText("Eine Datei mit diesem Namen wurde bereits hinzugefügt");
                alert.showAndWait();

                e.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }


            fillListView();
        }
        else {

            alert.setTitle("Error Message");
            alert.setHeaderText("Falschen Dateitypen ausgewählt!!");
            alert.showAndWait();
        }

    }

    @FXML
    public void handleButtonDeleteGame(ActionEvent event) throws IOException {
        /*
        File file = new File("./savegames/img/"+selected+".png");
        file.delete();
        file = new File("./savegames/JSON/"+selected+".json");
        file.delete();
        FillListView();
         */
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillListView();
    }


    public void fillListView(){
        saveGameList = FXCollections.observableArrayList();
        File file = new File(Paths.get("./savegames/JSON").toString());
        File[] files = file.listFiles();
        if(files != null){
            for(File f : files){
                saveGameList.add(f.getName().substring(0,f.getName().length()-5));
            }
        }

        JSONParser jsonparser = new JSONParser();


        lvSaveGames.setItems(saveGameList);
        lvSaveGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                btnContinue.setDisable(false);
                selected = newValue;

                    try {
                        Object obj =   jsonparser.parse(new FileReader("./savegames/JSON/"+newValue+".json"));
                        JSONObject gameinfos = (JSONObject) obj;
                      String name = (String)gameinfos.get("FileName");


                            File imgfile = new File("./savegames/img/"+name+".png");
                            Image image = new Image(imgfile.toURI().toString());
                            lbGameName.setText(name);
                            lbVersion.setText((String)gameinfos.get("version"));
                            lbGenerate.setText((String)gameinfos.get("generateType"));
                            lbDifficulty.setText((String)gameinfos.get("difficulty"));
                        ivSavegame.setImage(image);

                    } catch (IOException|ParseException e) {
                        e.printStackTrace();
                    }
            }
        });
    }
}
