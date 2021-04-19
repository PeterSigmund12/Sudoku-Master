package at.jku.se.sudokuMaster;

import java.util.HashSet;
import java.util.Set;

public class SimpleSolver {
    private Set<Integer> numbers;
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
    }

    boolean validRow(SimpleBoard b, int r){
        Set<Integer>num = new HashSet<>(numbers);
        for (int c = 0; c<9;c++){
                Cell i = b.get(c,r);
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
        for (int r=0;r<9;r++){
            if (!validRow(b,r)){
                return false;
            }
        }
        return true;
    }

    boolean validColumn(SimpleBoard b, int c){
        Set<Integer>num = new HashSet<>(numbers);
        for (int r= 0; r<9;r++){
            Cell i = b.get(c,r);
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
        for (int c=0;c<9;c++){
            if (!validColumn(b,c)){
                return false;
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
        for (int r = 0; r<9;r+=3){
            for (int c=0;c<9;c+=3){
                if (!validBox(b,c,r))return false;
            }
        }
        return true;
    }
    boolean valid(SimpleBoard b){
        return allColumnsValid(b) && allRowsValid(b) && allBoxesValid(b);
    }
    boolean full(SimpleBoard b){
        for (int r = 0; r<9;r++){
            for (int c=0;c<9;c++) {
                if (b.get(c,r)==null)return false;
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
        SimpleBoard b = new SimpleBoard(part);
        for (int r = 0; r<9;r++){
            for (int c=0;c<9;c++) {
                Cell cell = b.get(c,r);
                if (cell == null){
                    Set<Integer> num = new HashSet<>(numbers);
                    for (Integer number:num){
                        b.setValue(c,r,number);
                        SimpleBoard solution = solve(b);
                        if (solution!= null){
                            return solution;
                        }
                    }
                    return null;
                }
            }
        }
        return null;
    }
    //TODO: getHint
}
