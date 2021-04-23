package at.jku.se.sudokumaster;

public class SimpleBoard {

    private Cell[][] board =  new Cell[9][9];
    //private String generateType = "";


    public SimpleBoard(){

    }
    public SimpleBoard(SimpleBoard init){
        for (int c=0; c<9;c++){
            for (int r=0; r<9;r++){
                board[c][r] =  init.get(c,r);
            }
        }
    }

    public Cell get(int c, int r) {return board[c][r]; }

    public void setValue(int c, int r, Integer x){
        try{
        board[c][r].setValue(x);
        }catch (NullPointerException e){
            board[c][r] = new Cell(x,"",1);
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
