package at.jku.se.sudokumaster;

/**
 * The type Simple board.
 */
public class SimpleBoard {

    private Cell[][] board;

    /**
     * Instantiates a new empty Simple board.
     *
     * @param size the size of a Sudoku board.
     *             9 for Normal, Freeform
     *             21 for Samurai
     */
    public SimpleBoard(int size){
        board = new Cell[size][size];
    }

    /**
     * Instantiates a new Simple board.
     *
     * @param init the init
     * @param size the size
     */
    public SimpleBoard(SimpleBoard init,int size){
        board = new Cell[size][size];
        for (int c=0; c<size;c++){
            for (int r=0; r<size;r++){
                board[c][r] =  init.get(c,r);
            }
        }
    }

    /**
     * Get cell.
     *
     * @param c the column
     * @param r the row
     * @return the cell
     */
    public Cell get(int c, int r) {return board[c][r]; }

    /**
     * Set value.
     *
     * @param c the column
     * @param r the row
     * @param x the value
     */
    public void setValue(int c, int r, Integer x){
        try{
        board[c][r].setValue(x);
        }catch (NullPointerException e){
            board[c][r] = new Cell(x,"",0);
        }
    }

    /**
     * Set group.
     *
     * @param c the c
     * @param r the r
     * @param x the group ID
     */
    public void setGroup(int c, int r, Integer x){
        try{
            board[c][r].setGroupId(x);
        }catch (NullPointerException e){
            board[c][r] = new Cell(0,"",x);
        }
    }


}
