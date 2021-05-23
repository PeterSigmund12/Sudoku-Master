package at.jku.se.controller;

import at.jku.se.sudokumaster.AnchorPoint;
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
    public void solveBoard(TextField[][] fields) {
        SimpleBoard solution = getBoardSolution(fields);
        for (int r = 0; r<fields.length;r++){
            for (int c=0;c<fields.length;c++) {
                if (fields[c][r].getText().trim().equals("") && fields[c][r].isVisible()){
                    if (solution == null){
                        // Empty Cells with no Solution
                        fields[c][r].setStyle("-fx-background-color:rgb(255,200,200);");
                    }else {
                        // Empty Cells with Solution
                        fields[c][r].setText(""+solution.get(c,r).getValue());
                        fields[c][r].setStyle("-fx-background-color:rgb(160,240,130);");
                    }
                }//else {
                //textFields[c][r].setStyle("-fx-background-color:rgb(255,255,255)");
                //}
            }
        }
    }
    public SimpleBoard getBoardSolution(TextField[][] fields) {
        SimpleSolver s = new SimpleSolver(fields.length);
        SimpleBoard part = getCurrentBoard(fields);
        return s.solve(part);
    }

    public SimpleBoard getCurrentBoard(TextField[][] fields) {
        SimpleBoard part = new SimpleBoard(fields.length);
        for (int r = 0; r< fields.length;r++){
            for (int c=0;c<fields.length;c++) {
                try{
                    Integer i = Integer.valueOf(fields[c][r].getText());
                    part.setValue(c,r,i);
                }catch (NumberFormatException e){}
            }
        }
        return part;
    }

    public void getHint(TextField[][] fields) {
        SimpleBoard solution = getBoardSolution(fields);
        SimpleSolver s = new SimpleSolver(fields.length);
        SimpleBoard board = getCurrentBoard(fields);
        AnchorPoint[] points = s.getAnchorpoints();
        if (solution != null && !s.validAndFull(board)){
            while (true){
                AnchorPoint point = points[new Random().nextInt(points.length)];
                int randC = new Random().nextInt(9)+point.getCol();
                int randR = new Random().nextInt(9)+point.getRow();
                if (fields[randC][randR].getText().trim().equals("") && fields[randC][randR].isVisible()){
                    fields[randC][randR].setText(""+solution.get(randC,randR).getValue());
                    fields[randC][randR].setStyle("-fx-background-color:rgb(160,240,130)");
                    break;
                }
            }
        }
    }

    public static void hideUnusedBoxes(int c, int r, TextField t) {
        if(((c>=9 && c<12) && (r<6 || r>=15))||((c<6 || c>=15)&&(r>=9 && r<12))){
            t.setVisible(false);
        }
    }

    public void setNormalBoxesStyle(int c, int r, TextField t, String style) {
        if ((((c > 2 && c < 6) || (c > 14 && c < 18)) && ((r > 2 && r < 6) || r > 14 && r < 18) || ((c > 8 && c < 12) && (r > 8 && r < 12)))) {
            t.setStyle("-fx-opacity: 1;-fx-background-color:rgb(220,240,240);"+style);
        }else if((c <= 2 || (c >= 6 && c <= 8) || (c >= 12 && c <= 14) || c >= 18) && ((r <= 2 || (r >= 6 && r <= 8)) || (r >= 12 && r <= 14) || r >= 18)){
            t.setStyle("-fx-opacity: 1;-fx-background-color:rgb(220,240,240);"+style);
        }else {
            t.setStyle("-fx-opacity: 1;"+style);

        }
    }
}
