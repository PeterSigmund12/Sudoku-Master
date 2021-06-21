package at.jku.se.sudokumaster;

public class SimpleBoard {

    private Cell[][] board;

    public SimpleBoard(int size){
        board = new Cell[size][size];
    }
    public SimpleBoard(SimpleBoard init,int size){
        board = new Cell[size][size];
        for (int c=0; c<size;c++){
            for (int r=0; r<size;r++){
                board[c][r] =  init.get(c,r);
            }
        }
    }

    public Cell get(int c, int r) {return board[c][r]; }

    public void setValue(int c, int r, Integer x){
        try{
        board[c][r].setValue(x);
        }catch (NullPointerException e){
            board[c][r] = new Cell(x,"",0);
        }
    }
    public void setGroup(int c, int r, Integer x){
        try{
            board[c][r].setGroupId(x);
        }catch (NullPointerException e){
            board[c][r] = new Cell(0,"",x);
        }
    }


}
