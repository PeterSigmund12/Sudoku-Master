package at.jku.se.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import org.json.simple.JSONObject;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class Playfield_Controller {
    TextField[][]textFields =  new TextField[9][9];
    @FXML
    private AnchorPane root;

    @FXML
    private GridPane playfield;
    @FXML
    private Button btn_Solve;


    String version ="";
    String generateType="";
    String diffculty ="false";

    public void initialize() {
        playfield.setGridLinesVisible(false);
        playfield.setAlignment(Pos.CENTER);
        playfield.setHgap(5);
        playfield.setVgap(5);
        //playfield.setPadding(new Insets(25,25,25,25));
        for (int c=0; c<9;c++){
            for (int r=0; r<9;r++){
                TextField t = new SudokuHelper().invoke();
                //t.setPadding(new Insets(15,15,15,15));
                textFields[c][r] = t;
                if ((c<3||c>=6) && (r<3||r>=6) || (r>=3 && r<6) && (c>=3 && c<6)) {
                    t.setStyle("-fx-background-color:rgb(220,240,255)");
                }
                playfield.add(t,c,r);
            }
        }
    }
    @FXML
    public void handleButton_Solve(ActionEvent event) throws IOException {
        SudokuHelper h = new SudokuHelper();
        h.solveBoard(textFields,0,0);
        btn_Solve.setDisable(true);
    }



    @FXML
    public void handleButton_SaveGame(ActionEvent event) {
        JSONObject saveGame = new JSONObject();
        String file ="";
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save Game");
        dialog.setContentText("Filename: ");

        Optional<String> fileName = dialog.showAndWait();
        if(fileName.isPresent()){

            file = fileName.get();
        }
        saveGame.put("FileName", file);
        saveGame.put("version", version);
        saveGame.put("generateType", generateType);
        saveGame.put("difficulty",diffculty);
        String row="";
        for (int r = 0; r<9;r++){
            row="";
            for (int c=0;c<9;c++) {
                try{
                    Integer i = Integer.valueOf(textFields[c][r].getText());
                    row+= i.toString() + ";";
                }catch (NumberFormatException e){
                    row+=  " ;";

                }
            }
            saveGame.put(""+r,row);
        }

        FileWriter saveFile = null;
        try {
            saveFile = new FileWriter("savegames/"+file+".json");
            saveFile.write(saveGame.toJSONString());
            saveFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }



    public void initData(String version, String generateType, String diffculty) {
        System.out.println(version +"v "+diffculty+" d "+ generateType);
        this.generateType = generateType;
        this.diffculty = diffculty;
        this.version = version;
    }
}
