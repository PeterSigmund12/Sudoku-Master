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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public void handleButton_backSavedMainMen(ActionEvent event) throws IOException {

        NewScreen.openNewScreen(event,"/fxml/mainmenue.fxml");

        /*
        Node node = (Node) event.getSource();
        Stage oldStage = (Stage)node.getScene().getWindow();;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/mainmenue.fxml"));
        Parent root2 = null;

        try {
            root2 = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
        }

        Stage stage = new Stage();
        stage.setTitle("Start new game");
        stage.setScene(new Scene(root2));

        stage.show();
        oldStage.close();

         */

    }

    @FXML
    public void handleButton_importGame(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Import Game");
        Stage stage = new Stage();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        File f =fc.showOpenDialog(stage);
        if(f.getName().substring(f.getName().length()-5).equals(".json")){


                File newFile = new File("savegames/"+ f.getName());
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

        /*
        Node node = (Node) event.getSource();
        Stage oldStage = (Stage)node.getScene().getWindow();;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/mainmenue.fxml"));
        Parent root2 = null;

        try {
            root2 = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
        }

        Stage stage = new Stage();
        stage.setTitle("Start new game");
        stage.setScene(new Scene(root2));

        stage.show();
        oldStage.close();

         */

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FillListView();
    }


    public void FillListView(){
        saveGameList = FXCollections.observableArrayList();
        File file = new File(Paths.get("./savegames").toString());
        File[] files = file.listFiles();
        if(files != null){
            for(File f : files){
                saveGameList.add(f.getName().substring(0,f.getName().length()-5));
            }
        }

        lv_SaveGames.setItems(saveGameList);
        lv_SaveGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                btn_Continue.setDisable(false);
                selected = newValue;
            }
        });
    }
}
