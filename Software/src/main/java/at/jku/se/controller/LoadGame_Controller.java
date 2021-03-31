package at.jku.se.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class LoadGame_Controller {


    @FXML
    private AnchorPane root;

    @FXML
    ListView lv_SaveGames;

    @FXML
    private Button btn_Import, btn_Export, btn_Continue;
}
