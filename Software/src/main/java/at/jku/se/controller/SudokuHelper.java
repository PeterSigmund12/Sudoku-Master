package at.jku.se.controller;

import at.jku.se.sudokumaster.AnchorPoint;
import at.jku.se.sudokumaster.SimpleBoard;
import at.jku.se.sudokumaster.SimpleSolver;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Random;
import java.util.logging.Logger;

/**
 * The type Sudoku helper.
 */
public class SudokuHelper {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Initialize the default Textfield.
     *
     * Set default style.
     * Set default border.
     * Add Listener and allow only the numbers 1-9 to be entered.
     *
     * @return the text field
     */
    public TextField defaultTextField() {
        TextField t = new TextField();
        t.setPrefSize(38,38);
        t.setAlignment(Pos.CENTER);
        t.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-faint-focus-color: -fx-control-inner-background ;");
        t.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        //allow only numbers in textfield from:
        t.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\d{1}$")) {
                t.setText(newValue.replaceAll("[^\\d]", ""));
            }
            try {
                if (Integer.parseInt(newValue) > 9 || Integer.parseInt(newValue) == 0) {
                    t.setText(newValue.replaceAll(newValue, oldValue));
                }
            }
            catch (Exception e){logger.warning(""+e);}
        });
        return t;
    }

    /**
     * Solve board and show solved Board
     *
     * @param fields the fields
     */
    public void solveBoard(TextField[][] fields) {
        SimpleBoard solution = getBoardSolution(fields);
        for (int r = 0; r<fields.length;r++){
            for (int c=0;c<fields.length;c++) {
                fields[c][r].setEditable(false);
                if (fields[c][r].getText().trim().equals("") && fields[c][r].isVisible()){
                    if (solution == null){
                        // Empty Cells with no Solution
                        //fields[c][r].setStyle("-fx-background-color:rgb(255,200,200);");
                    }else {
                        // Empty Cells with Solution
                        fields[c][r].setText(""+solution.get(c,r).getValue());
                        //fields[c][r].setStyle("-fx-background-color:rgb(160,240,130);");
                    }
                }
            }
        }
    }

    /**
     * Gets board solution.
     *
     * @param fields the fields
     * @return the board solution
     */
    public SimpleBoard getBoardSolution(TextField[][] fields) {
        SimpleSolver s = new SimpleSolver(fields.length);
        SimpleBoard part = getCurrentBoard(fields);
        return s.solve(part);
    }

    /**
     * Gets current board.
     *
     * @param fields the fields
     * @return the current board
     */
    public SimpleBoard getCurrentBoard(TextField[][] fields) {
        SimpleBoard part = new SimpleBoard(fields.length);
        for (int r = 0; r< fields.length;r++){
            for (int c=0;c<fields.length;c++) {
                try{
                    Integer i = Integer.valueOf(fields[c][r].getText());
                    part.setValue(c,r,i);
                }catch (NumberFormatException  e){logger.warning(""+e);}
                try {
                    Integer j = Integer.valueOf(fields[c][r].getId());
                    part.setGroup(c,r,j);
                }catch (NullPointerException | NumberFormatException e){logger.warning(""+e);}
            }
        }
        return part;
    }

    /**
     * Gets a hint for the board on a random position.
     *
     * @param fields the fields
     */
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
                    fields[randC][randR].setEditable(false);
                    break;
                }
            }
        }
    }

    /**
     * Hide unused boxes.
     *
     * @param c the column
     * @param r the row
     * @param t the textfield
     */
    public static void hideUnusedBoxes(int c, int r, TextField t) {
        if(((c>=9 && c<12) && (r<6 || r>=15))||((c<6 || c>=15)&&(r>=9 && r<12))){
            t.setVisible(false);
        }
    }

    /**
     * Sets normal boxes style.
     * Used for Normal and Samurai Sudokus
     *
     * @param c     the column
     * @param r     the row
     * @param t     the textfield
     * @param style the style
     */
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
