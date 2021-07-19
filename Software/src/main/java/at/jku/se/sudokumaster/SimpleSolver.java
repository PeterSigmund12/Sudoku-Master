package at.jku.se.sudokumaster;


import java.util.HashSet;
import java.util.Set;
import java.util.Random;

/**
 * The Simple solver class.
 */
public class SimpleSolver {
    private Set<Integer> numbers;
    private Set<AnchorPoint> anchorpoints;
    private int cnt = 0;
    /**
     * The Sudoku Field size.
     */
    public int fieldSize;

    /**
     * Instantiates a new Simple solver.
     *
     * @param size the size of the Sudoku Array
     *             eg. Normal Sudoku. Size = 9
     *                 Samurai Sudoku. Size = 21
     */
    public SimpleSolver(int size){
        numbers = new HashSet<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(6);
        numbers.add(7);
        numbers.add(8);
        numbers.add(9);
        anchorpoints = new HashSet<>();
        fieldSize = size;
        if (size == 9){
            anchorpoints.add(new AnchorPoint(0,0));
        }else {
            anchorpoints.add(new AnchorPoint(0,0));
            anchorpoints.add(new AnchorPoint(12,0));
            anchorpoints.add(new AnchorPoint(0,12));
            anchorpoints.add(new AnchorPoint(6,6));
            anchorpoints.add(new AnchorPoint(12,12));

        }
    }

    /**
     * Get all valid anchorpoints.
     *
     * @return the anchor point [ ]
     */
    public AnchorPoint[] getAnchorpoints(){
        return anchorpoints.toArray(new AnchorPoint[anchorpoints.size()]);
    }

    /**
     * Valid row boolean.
     *
     * @param b       the current Board
     * @param r       the row to check
     * @param anchorC the anchor column
     * @param anchorR the anchor row
     * @return the boolean
     */
    public boolean validRow(SimpleBoard b, int r, int anchorC, int anchorR){
        Set<Integer>num = new HashSet<>(numbers);
        for (int c = 0; c<9;c++){
            int i = 0;
            try{
                i = b.get(c+anchorC,r+anchorR).getValue();
            }catch (NullPointerException e){}
            if (i > 0){
                if (!num.contains(i)){
                    return false;
                }else {
                    num.remove(i);
                }
            }
        }
        return true;
    }

    /**
     * Check if all rows are valid. Return boolean.
     *
     * @param b the current Board
     * @return the boolean
     */
    boolean allRowsValid(SimpleBoard b){
        for (AnchorPoint ap : anchorpoints) {
            for (int r = 0; r < 9; r++) {
                if (!validRow(b, r, ap.getCol(), ap.getRow())) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Check if a single column is valid. Return boolean.
     *
     * @param b       the current Board
     * @param c       the column to check
     * @param anchorC the anchor column
     * @param anchorR the anchor row
     * @return the boolean
     */
    public boolean validColumn(SimpleBoard b, int c, int anchorC, int anchorR){
        Set<Integer>num = new HashSet<>(numbers);
        for (int r= 0; r<9;r++){
            int i = -1;
            try{
                i = b.get(c+anchorC,r+anchorR).getValue();
            }catch (NullPointerException e){}
            if (i > 0){
                if (!num.contains(i)){
                    return false;
                }else {
                    num.remove(i);
                }
            }
    }
        return true;
    }

    /**
     * Check if all columns are valid. Return boolean.
     *
     * @param b the current board
     * @return the boolean
     */
    boolean allColumnsValid(SimpleBoard b){
        for (AnchorPoint ap : anchorpoints) {
            for (int c = 0; c < 9; c++) {
                if (!validColumn(b, c, ap.getCol(), ap.getRow())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if a default 3x3 box is valid. Return boolean.
     *
     * @param b       the current Board
     * @param anchorC the anchor column
     * @param anchorR the anchor row
     * @return the boolean
     */
    public boolean validBox(SimpleBoard b, int anchorC, int anchorR){
        Set<Integer>num = new HashSet<>(numbers);
        for (int r = 0; r<3;r++){
            for (int c=0;c<3;c++){
                int i = -1;
                try{
                    i = b.get(c+anchorC,r+anchorR).getValue();
                }catch (NullPointerException e){}
                if (i > 0){
                    if (!num.contains(i)){
                        return false;
                    }else {
                        num.remove(i);
                    }
                }
        }
        }
        return true;
    }

    /**
     * Check if a freeform Sudoku Group is valid. Return boolean.
     *
     * @param b       the current Board
     * @param groupId the group id
     * @return the boolean
     */
    public boolean validBox(SimpleBoard b, int groupId){
        Set<Integer>num = new HashSet<>(numbers);
        for (int r = 0; r<fieldSize;r++){
            for (int c=0;c<fieldSize;c++){
                int val = 0;
                int group = -1;
                try{
                    val = b.get(c,r).getValue();
                    group = b.get(c,r).getGroupId();
                }catch (NullPointerException e){}
                if (val > 0 && group == groupId){
                    if (!num.contains(val)){
                        return false;
                    }else {
                        num.remove(val);
                    }
                }
            }
        }
        return true;
    }

    /**
     * Check if all boxes are valid. Return boolean.
     *
     * Differentiate between normal Sudokus (Normal, Samurai) and Freeform Sudokus.
     * To do so get the groupID of three Random Positions from the board.
     *
     * @param b the b
     * @return the boolean
     */
    boolean allBoxesValid(SimpleBoard b){
        Random rand = new Random();
        for (AnchorPoint ap : anchorpoints) {
            int i = -1;
            try{
                i += b.get(rand.nextInt(9),rand.nextInt(9)).getGroupId();
                i += b.get(rand.nextInt(9),rand.nextInt(9)).getGroupId();
                i += b.get(rand.nextInt(9),rand.nextInt(9)).getGroupId();
            }catch (NullPointerException e){}
            if(i<0) {
                for (int r = 0; r < 9; r += 3) {
                    for (int c = 0; c < 9; c += 3) {
                        if (!validBox(b, c + ap.getCol(), r + ap.getRow())) return false;
                    }
                }
            }else {
                for (int j = 0; j < 9; j++){
                    if (!validBox(b,j))return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if a board is valid. Return boolean.
     *
     * @param b the current Board.
     * @return the boolean
     */
    public boolean valid(SimpleBoard b){
        return  allBoxesValid(b) && allColumnsValid(b) && allRowsValid(b) ;
    }

    /**
     * Check if the current board is full. Return boolean.
     *
     * @param b the current board
     * @return the boolean
     */
    boolean full(SimpleBoard b){
        for (AnchorPoint ap : anchorpoints) {
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    int val = 0;
                    try{
                        val = b.get(c+ap.getCol(), r+ap.getRow()).getValue();
                    }catch (NullPointerException e){}
                    if (b.get(c+ap.getCol(), r+ap.getRow()) == null || val == 0) return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if the current board is valid and full (Game is completed and valid). Return boolean.
     *
     * @param b the current board
     * @return the boolean
     */
    public boolean validAndFull(SimpleBoard b){
        return valid(b) && full(b);
    }

    /**
     * Solver for all boards.
     *  1. Use a counter to stop in case of an endless loop.
     *  2. Return null if the current board isn't valid.
     *  3. Return the board if the current board is valid and full.
     *  4. Enter the solve algorithm.
     *     Brute force attempt.
     *     If a cell is empty add numbers from 1-9 and start recursion with the added cell.
     *  TODO: Problem with Freeform Sudokus with a low number input. Solver cannot solve Freeform with less than ~40% of the cells filled.
     * @param part the current board
     * @return the simple board
     */
    public SimpleBoard solve(SimpleBoard part){
        cnt++;
        if (cnt > 150000){
            cnt=0;
            return null;
        }

        if (!valid(part)){
            return null;

        }
        if (validAndFull(part)){
            return part;
        }
        SimpleBoard b = new SimpleBoard(part,fieldSize);
        if(cnt < 100000){
            for (AnchorPoint ap : anchorpoints) {
                int apC = ap.getCol();
                int apR = ap.getRow();
                for (int r = 0; r < 9; r++) {
                    for (int c = 0; c < 9; c++) {
                        Cell cell = b.get(c+apC, r+apR);
                        if (cell == null || cell.getValue() == 0) {
                            Set<Integer> num = new HashSet<>(numbers);
                            for (Integer number : num) {
                                b.setValue(c+apC, r+apR, number);
                                SimpleBoard solution = solve(b);
                                if (solution != null) {
                                    return solution;
                                }
                            }
                            return null;
                        }
                    }
                }
            }
        }
        return null;
    }
}
