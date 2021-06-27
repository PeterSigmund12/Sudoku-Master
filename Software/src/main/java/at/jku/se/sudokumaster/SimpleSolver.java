package at.jku.se.sudokumaster;


import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class SimpleSolver {
    private Set<Integer> numbers;
    private Set<AnchorPoint> anchorpoints;
    public int fieldSize;
    private int cnt = 0;

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
    public AnchorPoint[] getAnchorpoints(){
        return anchorpoints.toArray(new AnchorPoint[anchorpoints.size()]);
    }

    boolean validRow(SimpleBoard b, int r,int anchorC, int anchorR){
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

    boolean validColumn(SimpleBoard b, int c,int anchorC, int anchorR){
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

    boolean validBox(SimpleBoard b, int anchorC, int anchorR){
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
    boolean validBox(SimpleBoard b, int groupId){
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
    public boolean valid(SimpleBoard b){
        //System.out.println("Boxes: "+allBoxesValid(b) +" Columns: "+ allColumnsValid(b) +" Rows: "+ allRowsValid(b));
        return  allBoxesValid(b) && allColumnsValid(b) && allRowsValid(b) ;
    }
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

    public boolean validAndFull(SimpleBoard b){
        return valid(b) && full(b);
    }

    public SimpleBoard solve(SimpleBoard part){
        cnt++;
        if (cnt > 150000){
            cnt=0;
            return null;
        }

        if (!valid(part)){
            //System.out.println("Error");

            return null;

        }
        if (validAndFull(part)){
            //System.out.println("Continue");

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
