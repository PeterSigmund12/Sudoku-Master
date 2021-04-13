package at.jku.se.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.io.IOException;

public class Samurai_Controller {
    TextField[][] textFields =  new TextField[21][21];

    @FXML
    private AnchorPane root;

    @FXML
    private GridPane playfield;

    @FXML
    private Button btn_Solve;

    //anchorpoints 0/0 , 12/0 , 0/12 , 6/6 , 12/12
    String version ="";
    String generateType="";
    String diffculty ="false";

    public void initialize() {
        //x.setGridLinesVisible(true);
        playfield.setAlignment(Pos.CENTER);
        playfield.setHgap(1);
        playfield.setVgap(1);
        //playfield.setPadding(new Insets(25,25,25,25));
        for (int c=0; c<21;c++){
            for (int r=0; r<21;r++){
                //show coordinates
                //t.setText(c+" "+r);
                TextField t = new SudokuHelper().invoke();
                //t.setPadding(new Insets(15,15,15,15));
                textFields[c][r] = t;
                //Corner Boxes
                if ((c<=2||(c>=6 && c<=8)||(c>=12 && c<=14)|| c>=18) && ((r<=2||(r>=6 && r<=8))||(r>=12 && r<=14)||r>=18)) {
                    t.setStyle("-fx-background-color:rgb(220,240,255)");
                }
                //Middle Boxes
                if ((((c>2 && c<6)||(c>14 && c<18))&&((r>2 && r<6)||r>14 && r<18)||((c>8 && c<12)&&(r>8 && r<12)) )){
                    t.setStyle("-fx-background-color:rgb(220,240,240)");

                }
                if(((c>=9 && c<12) && (r<6 || r>=15))||((c<6 || c>=15)&&(r>=9 && r<12))){
                    t.setVisible(false);
                }
                playfield.add(t,c,r);
            }
        }
    }

    @FXML
    public void handleButton_Solve(ActionEvent event) throws IOException {
        SudokuHelper h = new SudokuHelper();
        h.solveBoard(textFields,6,6);
        h.solveBoard(textFields,0,0);
        h.solveBoard(textFields,12,0);
        h.solveBoard(textFields,0,12);
        h.solveBoard(textFields,12,12);
        btn_Solve.setDisable(true);

    }




    public void initData(String version, String generateType, String diffculty) {
        //System.out.println(version +"v "+diffculty+" d "+ generateType);
        this.generateType = generateType;
        this.diffculty = diffculty;
        this.version = version;
    }

}
