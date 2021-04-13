package at.jku.se.controller;

import at.jku.se.sudokuMaster.SimpleBoard;
import at.jku.se.sudokuMaster.SimpleSolver;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SudokuHelper {
    public TextField invoke() {
        TextField t = new TextField();
        t.setPrefSize(38,38);
        t.setAlignment(Pos.CENTER);
        t.setFont(Font.font("Calibri", FontWeight.EXTRA_BOLD,18));
        t.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-faint-focus-color: -fx-control-inner-background ;");
        t.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        //allow only numbers in textfield from:
        //https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
        t.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\d{1}$")) {
                t.setText(newValue.replaceAll("[^\\d]", ""));
            }
            try {
                if (Integer.parseInt(newValue) > 9 || Integer.parseInt(newValue) == 0) {
                    t.setText(newValue.replaceAll(newValue, oldValue));
                }
            }
            catch (Exception e){}
        });
        return t;
    }
    public void solveBoard(TextField[][] fields,int anchorC,int anchorR) {
        SimpleSolver s = new SimpleSolver();
        SimpleBoard part = new SimpleBoard();
        for (int r = 0; r<9;r++){
            for (int c=0;c<9;c++) {
                try{
                    Integer i = Integer.valueOf(fields[c+anchorC][r+anchorR].getText());
                    part.setValue(c,r,i);
                }catch (NumberFormatException e){}
            }
        }
        SimpleBoard solution = s.solve(part);
        for (int r = 0; r<9;r++){
            for (int c=0;c<9;c++) {
                if (fields[c+anchorC][r+anchorR].getText().trim().equals("")){
                    if (solution == null){
                        fields[c+anchorC][r+anchorR].setStyle("-fx-background-color:rgb(255,200,200)");
                    }else {
                        fields[c+anchorC][r+anchorR].setStyle("-fx-text-fill: green");
                        fields[c+anchorC][r+anchorR].setText(""+solution.get(c,r).getValue());
                    }
                }//else {
                //textFields[c][r].setStyle("-fx-background-color:rgb(255,255,255)");
                //}
            }
        }
    }
}
