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
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class LoadGame_Controller implements Initializable {


    @FXML
    private AnchorPane root;

    @FXML
    ListView lv_SaveGames;

    ObservableList<String> saveGameList;

    String selected;
    @FXML
    private Button btn_Import, btn_Export, btn_Continue, btn_backSavedMainMen;

    @FXML
    private Label lb_gameName,lb_generate,lb_difficulty,lb_version;

    @FXML
    private ImageView iv_savegame;

    @FXML
    public void handleButton_backSavedMainMen(ActionEvent event) throws IOException {

        NewScreen.openNewScreen(event,"/fxml/mainmenue.fxml");

    }

    @FXML
    public void handleButton_importGame(ActionEvent event) {
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


            FillListView();
        }
        else {

            alert.setTitle("Error Message");
            alert.setHeaderText("Falschen Dateitypen ausgewählt!!");
            alert.showAndWait();
        }

    }

    @FXML
    public void handleButton_DeleteGame(ActionEvent event) throws IOException {
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
        FillListView();
    }


    public void FillListView(){
        saveGameList = FXCollections.observableArrayList();
        File file = new File(Paths.get("./savegames/JSON").toString());
        File[] files = file.listFiles();
        if(files != null){
            for(File f : files){
                saveGameList.add(f.getName().substring(0,f.getName().length()-5));
            }
        }

        JSONParser jsonparser = new JSONParser();


        lv_SaveGames.setItems(saveGameList);
        lv_SaveGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                btn_Continue.setDisable(false);
                selected = newValue;

                    try {
                        Object obj =   jsonparser.parse(new FileReader("./savegames/JSON/"+newValue+".json"));
                        JSONObject gameinfos = (JSONObject) obj;
                      String name = (String)gameinfos.get("FileName");


                            File imgfile = new File("./savegames/img/"+name+".png");
                            Image image = new Image(imgfile.toURI().toString());
                            lb_gameName.setText(name);
                            lb_version.setText((String)gameinfos.get("version"));
                            lb_generate.setText((String)gameinfos.get("generateType"));
                            lb_difficulty.setText((String)gameinfos.get("difficulty"));
                        iv_savegame.setImage(image);

                    } catch (IOException e) {
                        e.printStackTrace();

                } catch (ParseException e) {
                        e.printStackTrace();
                    }
            }
        });
    }
}
