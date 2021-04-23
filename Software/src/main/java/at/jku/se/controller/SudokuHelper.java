package at.jku.se.controller;

import at.jku.se.sudokumaster.SimpleBoard;
import at.jku.se.sudokumaster.SimpleSolver;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Random;

public class SudokuHelper {
    public TextField defaultTextField() {
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
        SimpleBoard solution = getBoardSolution(fields, anchorC, anchorR);
        for (int r = 0; r<9;r++){
            for (int c=0;c<9;c++) {
                if (fields[c+anchorC][r+anchorR].getText().trim().equals("")){
                    if (solution == null){
                        // Empty Cells with no Solution
                        fields[c+anchorC][r+anchorR].setStyle("-fx-background-color:rgb(255,200,200)");
                    }else {
                        // Empty Cells with Solution
                        fields[c+anchorC][r+anchorR].setText(""+solution.get(c,r).getValue());
                        fields[c+anchorC][r+anchorR].setStyle("-fx-background-color:rgb(160,240,130)");
                    }
                }//else {
                //textFields[c][r].setStyle("-fx-background-color:rgb(255,255,255)");
                //}
            }
        }
    }
    public void getHint(TextField[][] fields,int anchorC,int anchorR) {
        SimpleBoard solution = getBoardSolution(fields, anchorC, anchorR);
        SimpleSolver s = new SimpleSolver();
        SimpleBoard board = getCurrentBoard(fields, anchorC, anchorR);
        if (solution != null && !s.validAndFull(board)){
            while (true){
                int randC = new Random().nextInt(9);
                int randR = new Random().nextInt(9);
                if (fields[randC+anchorC][randR +anchorR].getText().trim().equals("")){
                    fields[randC+anchorC][randR+anchorR].setText(""+solution.get(randC,randR).getValue());
                    fields[randC+anchorC][randR+anchorR].setStyle("-fx-background-color:rgb(160,240,130)");
                    break;
                }
            }
        }
    }
    private SimpleBoard getBoardSolution(TextField[][] fields, int anchorC, int anchorR) {
        SimpleSolver s = new SimpleSolver();
        SimpleBoard part = getCurrentBoard(fields, anchorC, anchorR);
        return s.solve(part);
    }

    private SimpleBoard getCurrentBoard(TextField[][] fields, int anchorC, int anchorR) {
        SimpleBoard part = new SimpleBoard();
        for (int r = 0; r<9;r++){
            for (int c=0;c<9;c++) {
                try{
                    Integer i = Integer.valueOf(fields[c+anchorC][r+anchorR].getText());
                    part.setValue(c,r,i);
                }catch (NumberFormatException e){}
            }
        }
        return part;
    }
}
