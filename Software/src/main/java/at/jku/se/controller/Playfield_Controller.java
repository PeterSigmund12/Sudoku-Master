package at.jku.se.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javax.naming.PartialResultException;
import java.io.IOException;

public class Playfield_Controller {

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

        TextField[][]textFields =  new TextField[9][9];
        playfield.setGridLinesVisible(false);
        playfield.setAlignment(Pos.CENTER);
        playfield.setHgap(5);
        playfield.setVgap(5);
        //playfield.setPadding(new Insets(25,25,25,25));
        for (int c=0; c<9;c++){
            for (int r=0; r<9;r++){
                TextField t = new TextField();
                t.setPrefSize(100,100);
                t.setAlignment(Pos.CENTER);
                t.setFont(Font.font("Calibri", FontWeight.EXTRA_BOLD,20));
                //allow only numbers in textfield from:
                //https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
                t.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("^\\d{1}$")) {
                        t.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                    try {
                        if (Integer.parseInt(newValue) > 9 && Integer.parseInt(newValue) == 0) {
                            t.setText(newValue.replaceAll(newValue, oldValue));
                        }
                    }
                    catch (Exception e){}
                    //System.out.println(observable.toString()+"\t"+oldValue+"\t"+newValue);
                });
                //t.setPadding(new Insets(15,15,15,15));
                textFields[c][r] = t;
                t.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-faint-focus-color: -fx-control-inner-background ;");
                t.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                if ((c<3||c>=6) && (r<3||r>=6) || (r>=3 && r<6) && (c>=3 && c<6)) {
                    t.setStyle("-fx-background-color:rgb(220,240,255)");
                }
                playfield.add(t,c,r);
            }
        }
    }
    @FXML
    public void handleButton_Solve(ActionEvent event) throws IOException {

    }



    public void initData(String version, String generateType, String diffculty) {
        this.generateType = generateType;
        this.diffculty = diffculty;
        this.version = version;


    }
}
