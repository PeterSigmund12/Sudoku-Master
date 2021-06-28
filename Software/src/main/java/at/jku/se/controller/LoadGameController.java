package at.jku.se.controller;

import at.jku.se.utility.NewScreen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    private static final String BTN_REGULAR = "rbSaRegulaer";
    private static final String BTN_SAMURAI = "rbSaSamurai";
    private static final String BTN_FREIFORM = "rbSaFreiform";

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
    private MenuBar menuBar;

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

    String version ="";
    String generateType="";
    String difficulty ="";
    String fileName = "";

    @FXML
    public void handleButtonBackSavedMainMen(ActionEvent event) throws IOException {


        Stage oldStage = (Stage)menuBar.getScene().getWindow();


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(NewScreen.class.getResource("/fxml/mainmenue.fxml"));
        Parent root2 = null;
        try {
            root2 = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
        }
        Stage stage = new Stage();
        stage.setTitle("Load game");
        stage.setScene(new Scene(root2));

        stage.show();
        oldStage.close();
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
        ivSavegame.setImage(null);
        File jfile = new File("./savegames/JSON/"+selected+".json");
        if (jfile.exists()){

            Files.delete(jfile.toPath());
        }
        File file = new File("./savegames/img/"+selected+".png");
        file.delete();
        lvSaveGames.getItems().remove(selected);
        selected = "";


    }

    @FXML
    public void handleButtonLoadGame(ActionEvent event) throws IOException {

        //NewScreen.openNewScreen(event,"/fxml/spielfeld.fxml");
        Node node = (Node) event.getSource();
        Stage oldStage = (Stage)node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/spielfeld.fxml"));
        Parent root2 = null;
        try {
            root2 = (Parent) fxmlLoader.load();
            PlayfieldController controller = fxmlLoader.getController();
            controller.loadInitData(version, generateType, difficulty,fileName);

        } catch (IOException ex) {
        }
        Stage stage = new Stage();
        //stage.setTitle("old game");
        stage.sizeToScene();
        stage.setScene(new Scene(root2));
        stage.show();
        stage.setMaximized(true);
        //stage.setMinHeight(1200);
        //stage.setMinWidth(1600);
        oldStage.close();

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
                String labelversion = "";
                    try {
                        FileReader fileReader = new FileReader("./savegames/JSON/"+newValue+".json");
                        Object obj =   jsonparser.parse(fileReader);
                        JSONObject gameinfos = (JSONObject) obj;
                         fileName = (String)gameinfos.get("FileName");


                            File imgfile = new File("./savegames/img/"+fileName+".png");
                            Image image = new Image(imgfile.toURI().toString());
                            lbGameName.setText(fileName);
                            switch((String)gameinfos.get("version")){
                                case BTN_REGULAR:
                                    labelversion = "Regulär";
                                    break;
                                case BTN_SAMURAI:
                                    labelversion = "Samurai";
                                    break;
                                case BTN_FREIFORM:
                                    labelversion = "Freiform";
                                    break;
                            }
                            lbVersion.setText(labelversion);
                            lbGenerate.setText((String)gameinfos.get("generateType"));
                            lbDifficulty.setText((String)gameinfos.get("difficulty"));
                            difficulty = (String)gameinfos.get("difficulty");
                            version = (String)gameinfos.get("version");
                            generateType = (String)gameinfos.get("generateType");
                     //   ivSavegame.setImage(image);


                        fileReader.close();
                    } catch (IOException|ParseException e) {
                        e.printStackTrace();
                    }
            }
        });
    }
}
