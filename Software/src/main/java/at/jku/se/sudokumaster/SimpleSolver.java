package at.jku.se.sudokumaster;

import java.util.HashSet;
import java.util.Set;

public class SimpleSolver {
    private Set<Integer> numbers;
    private Set<AnchorPoint> anchorpoints;
    public int fieldSize;
    /*
    public SimpleSolver(){
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
        anchorpoints.add(new AnchorPoint(0,0));
        anchorpoints.add(new AnchorPoint(12,0));
        anchorpoints.add(new AnchorPoint(0,12));
        anchorpoints.add(new AnchorPoint(6,6));
        anchorpoints.add(new AnchorPoint(12,12));
    }

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
    public AnchorPoint[] getAnchorpoints(){
        return anchorpoints.toArray(new AnchorPoint[anchorpoints.size()]);
    }
    //anchorpoints 0/0 , 12/0 , 0/12 , 6/6 , 12/12

    boolean validRow(SimpleBoard b, int r,int anchorC, int anchorR){
        Set<Integer>num = new HashSet<>(numbers);
        for (int c = 0; c<9;c++){
            Cell i = b.get(c+anchorC,r+anchorR);
            if (i != null){
                if (!num.contains(i.getValue())){
                    return false;
                }else {
                    num.remove(i.getValue());
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
            Cell i = b.get(c+anchorC,r+anchorR);
            if (i != null){
                if (!num.contains(i.getValue())){
                    return false;
                }else {
                    num.remove(i.getValue());
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
                Cell i = b.get(anchorC +c , anchorR +r);
                if (i != null){
                    if (!num.contains(i.getValue())){
                        return false;
                    }else {
                        num.remove(i.getValue());
                    }
                }
        }
        }
        return true;
    }

    boolean allBoxesValid(SimpleBoard b){
        for (AnchorPoint ap : anchorpoints) {
            for (int r = 0; r<9;r+=3) {
                for (int c = 0; c < 9; c += 3) {
                    if (!validBox(b, c+ap.getCol(), r+ap.getRow())) return false;
                }

            }
        }
        return true;
    }
    boolean valid(SimpleBoard b){
        return allColumnsValid(b) && allRowsValid(b) && allBoxesValid(b);
    }
    boolean full(SimpleBoard b){
        for (AnchorPoint ap : anchorpoints) {
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (b.get(c+ap.getCol(), r+ap.getRow()) == null) return false;
                }
            }
        }
        return true;
    }

    public boolean validAndFull(SimpleBoard b){
        return valid(b) && full(b);
    }

    public SimpleBoard solve(SimpleBoard part){
        if (!valid(part)){
            return null;
        }
        if (validAndFull(part)){
            return part;
        }
        SimpleBoard b = new SimpleBoard(part,fieldSize);
        for (AnchorPoint ap : anchorpoints) {
            int apC = ap.getCol();
            int apR = ap.getRow();
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    Cell cell = b.get(c+apC, r+apR);
                    if (cell == null) {
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
        return null;
    }
    //TODO: getHint
}
