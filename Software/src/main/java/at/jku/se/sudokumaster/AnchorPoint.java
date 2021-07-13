package at.jku.se.sudokumaster;

/**
 * The type Anchor point.
 */
public class AnchorPoint{

    private int col;
    private int row;

    /**
     * Instantiates a new Anchor point.
     * Anchor points are used for non standard Sudoku sizes.
     * e.g Normal Sudoku has 1 Anchor Point, starting on the top left corner
     *     Samurai Sudoku has 5 Anchor Points, each showing a 9x9 Sudoku field
     *
     * @param col the col
     * @param row the row
     */
    public AnchorPoint(int col, int row) {
        this.col = col;
        this.row = row;
    }

    /**
     * Gets col.
     *
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets col.
     *
     * @param col the col
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Gets row.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets row.
     *
     * @param row the row
     */
    public void setRow(int row) {
        this.row = row;
    }
}
