package at.jku.se.sudokumaster;

import at.jku.se.sudokumaster.SimpleBoard;
import at.jku.se.sudokumaster.SimpleSolver;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimpleSolverTest {

    @org.junit.jupiter.api.Test
    void validRow() {
        SimpleSolver s = new SimpleSolver(9);
        SimpleBoard b = new SimpleBoard(9);
        b.setValue(0,0,9);
        assertTrue(s.validRow(b,0,0,0));

        b.setValue(0,1,9);
        b.setValue(1,1,9);
        assertTrue(!s.validRow(b,1,0,0));
    }

    @org.junit.jupiter.api.Test
    void validColumn() {
        SimpleSolver s = new SimpleSolver(9);
        SimpleBoard b = new SimpleBoard(9);
        b.setValue(0,0,9);
        assertTrue(s.validColumn(b,0,0,0));

        b.setValue(1,0,9);
        b.setValue(1,1,9);
        assertTrue(!s.validColumn(b,1,0,0));
    }
    @Test
    void ValidAllRows() {
        SimpleSolver s = new SimpleSolver(9);
        SimpleBoard b = new SimpleBoard(9);
        b.setValue(0,0,9);
        b.setValue(1,1,9);
        b.setValue(2,2,9);
        b.setValue(3,3,9);
        b.setValue(4,4,9);
        b.setValue(5,5,9);
        b.setValue(6,6,9);
        b.setValue(7,7,9);
        b.setValue(8,8,9);
        assertTrue(s.allRowsValid(b));
        b.setValue(7,8,9);
        assertTrue(!s.allRowsValid(b));
    }

    @Test
    void ValidAllColumns() {
        SimpleSolver s = new SimpleSolver(9);
        SimpleBoard b = new SimpleBoard(9);
        b.setValue(0,0,9);
        b.setValue(1,1,9);
        b.setValue(2,2,9);
        b.setValue(3,3,9);
        b.setValue(4,4,9);
        b.setValue(5,5,9);
        b.setValue(6,6,9);
        b.setValue(7,7,9);
        b.setValue(8,8,9);
        assertTrue(s.allColumnsValid(b));
        b.setValue(8,7,9);
        assertTrue(!s.allColumnsValid(b));
    }

    @Test
    void ValidBox(){
        SimpleSolver s = new SimpleSolver(9);
        SimpleBoard b = new SimpleBoard(9);
        b.setValue(0,0,9);
        assertTrue(s.validBox(b,0,0));
        b.setValue(1,1,9);
        assertTrue(!s.validBox(b,0,0));

    }
    @Test
    void ValidAllBox(){
        SimpleSolver s = new SimpleSolver(9);
        SimpleBoard b = new SimpleBoard(9);
        b.setValue(0,0,9);
        b.setValue(3,0,9);
        b.setValue(6,0,9);
        b.setValue(0,3,9);
        b.setValue(3,3,9);
        b.setValue(6,3,9);
        b.setValue(0,6,9);
        b.setValue(3,6,9);
        b.setValue(6,6,9);
        assertTrue(s.allBoxesValid(b));
        b.setValue(5,5,9);
        assertTrue(!s.allBoxesValid(b));

    }

    @Test
    void validAndComplete(){
        SimpleSolver s = new SimpleSolver(9);
        SimpleBoard b = new SimpleBoard(9);
        b.setValue(0,0,5);
        b.setValue(1,0,3);
        b.setValue(2,0,4);
        b.setValue(3,0,6);
        b.setValue(4,0,7);
        b.setValue(5,0,8);
        b.setValue(6,0,9);
        b.setValue(7,0,1);
        b.setValue(8,0,2);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0,1,6);
        b.setValue(1,1,7);
        b.setValue(2,1,2);
        b.setValue(3,1,1);
        b.setValue(4,1,9);
        b.setValue(5,1,5);
        b.setValue(6,1,3);
        b.setValue(7,1,4);
        b.setValue(8,1,8);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0,2,1);
        b.setValue(1,2,9);
        b.setValue(2,2,8);
        b.setValue(3,2,3);
        b.setValue(4,2,4);
        b.setValue(5,2,2);
        b.setValue(6,2,5);
        b.setValue(7,2,6);
        b.setValue(8,2,7);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0,3,8);
        b.setValue(1,3,5);
        b.setValue(2,3,9);
        b.setValue(3,3,7);
        b.setValue(4,3,6);
        b.setValue(5,3,1);
        b.setValue(6,3,4);
        b.setValue(7,3,2);
        b.setValue(8,3,3);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0,4,4);
        b.setValue(1,4,2);
        b.setValue(2,4,6);
        b.setValue(3,4,8);
        b.setValue(4,4,5);
        b.setValue(5,4,3);
        b.setValue(6,4,7);
        b.setValue(7,4,9);
        b.setValue(8,4,1);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0,5,7);
        b.setValue(1,5,1);
        b.setValue(2,5,3);
        b.setValue(3,5,9);
        b.setValue(4,5,2);
        b.setValue(5,5,4);
        b.setValue(6,5,8);
        b.setValue(7,5,5);
        b.setValue(8,5,6);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0,6,9);
        b.setValue(1,6,6);
        b.setValue(2,6,1);
        b.setValue(3,6,5);
        b.setValue(4,6,3);
        b.setValue(5,6,7);
        b.setValue(6,6,2);
        b.setValue(7,6,8);
        b.setValue(8,6,4);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0,7,2);
        b.setValue(1,7,8);
        b.setValue(2,7,7);
        b.setValue(3,7,4);
        b.setValue(4,7,1);
        b.setValue(5,7,9);
        b.setValue(6,7,6);
        b.setValue(7,7,3);
        b.setValue(8,7,5);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0,8,3);
        b.setValue(1,8,4);
        b.setValue(2,8,5);
        b.setValue(3,8,2);
        b.setValue(4,8,8);
        b.setValue(5,8,6);
        b.setValue(6,8,1);
        b.setValue(7,8,7);
        b.setValue(8,8,9);
        assertTrue(s.valid(b));
        assertTrue(s.validAndFull(b));
    }
    @Test
    void testSolve() {
        SimpleSolver s = new SimpleSolver(9);
        SimpleBoard b = new SimpleBoard(9);
        b.setValue(0, 0, 5);
        b.setValue(1, 0, 3);
        b.setValue(2, 0, 4);
        b.setValue(3, 0, 6);
        b.setValue(4, 0, 7);
        b.setValue(5, 0, 8);
        b.setValue(6, 0, 9);
        b.setValue(7, 0, 1);
        b.setValue(8, 0, 2);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 1, 6);
        b.setValue(1, 1, 7);
        b.setValue(2, 1, 2);
        b.setValue(3, 1, 1);
        b.setValue(4, 1, 9);
        b.setValue(5, 1, 5);
        b.setValue(6, 1, 3);
        b.setValue(7, 1, 4);
        b.setValue(8, 1, 8);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 2, 1);
        b.setValue(1, 2, 9);
        b.setValue(2, 2, 8);
        b.setValue(3, 2, 3);
        b.setValue(4, 2, 4);
        b.setValue(5, 2, 2);
        b.setValue(6, 2, 5);
        b.setValue(7, 2, 6);
        b.setValue(8, 2, 7);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 3, 8);
        b.setValue(1, 3, 5);
        b.setValue(2, 3, 9);
        b.setValue(3, 3, 7);
        b.setValue(4, 3, 6);
        b.setValue(5, 3, 1);
        b.setValue(6, 3, 4);
        b.setValue(7, 3, 2);
        b.setValue(8, 3, 3);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 4, 4);
        b.setValue(1, 4, 2);
        b.setValue(2, 4, 6);
        b.setValue(3, 4, 8);
        b.setValue(4, 4, 5);
        b.setValue(5, 4, 3);
        b.setValue(6, 4, 7);
        b.setValue(7, 4, 9);
        b.setValue(8, 4, 1);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 5, 7);
        b.setValue(1, 5, 1);
        b.setValue(2, 5, 3);
        b.setValue(3, 5, 9);
        b.setValue(4, 5, 2);
        b.setValue(5, 5, 4);
        b.setValue(6, 5, 8);
        b.setValue(7, 5, 5);
        b.setValue(8, 5, 6);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 6, 9);
        b.setValue(1, 6, 6);
        b.setValue(2, 6, 1);
        b.setValue(3, 6, 5);
        b.setValue(4, 6, 3);
        b.setValue(5, 6, 7);
        b.setValue(6, 6, 2);
        b.setValue(7, 6, 8);
        b.setValue(8, 6, 4);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 7, 2);
        b.setValue(1, 7, 8);
        b.setValue(2, 7, 7);
        b.setValue(3, 7, 4);
        b.setValue(4, 7, 1);
        b.setValue(5, 7, 9);
        b.setValue(6, 7, 6);
        b.setValue(7, 7, 3);
        b.setValue(8, 7, 5);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 8, 3);
        b.setValue(1, 8, 4);
        b.setValue(2, 8, 5);
        b.setValue(3, 8, 2);
        b.setValue(4, 8, 8);
        b.setValue(5, 8, 6);
        b.setValue(6, 8, 1);
        b.setValue(7, 8, 7);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));
        SimpleBoard sol = s.solve(b);
        assertTrue(sol.get(8, 8).getValue() == 9);
    }
        @Test
        void testSolveSmall(){
            SimpleSolver s = new SimpleSolver(9);
            SimpleBoard b = new SimpleBoard(9);
            b.setValue(0,0,5);
            b.setValue(1,0,3);
            b.setValue(2,0,4);
            b.setValue(3,0,6);
            b.setValue(4,0,7);
            b.setValue(5,0,8);
            b.setValue(6,0,9);
            b.setValue(7,0,1);
            b.setValue(8,0,2);
            assertTrue(s.valid(b));
            assertTrue(!s.validAndFull(b));

            b.setValue(0,8,3);
            b.setValue(1,8,4);
            b.setValue(2,8,5);
            b.setValue(3,8,2);
            b.setValue(4,8,8);
            b.setValue(5,8,6);
            b.setValue(6,8,1);
            b.setValue(7,8,7);
            assertTrue(s.valid(b));
            assertTrue(!s.validAndFull(b));
            SimpleBoard sol = s.solve(b);
            assertTrue(sol.get(8,8).getValue() == 9);
    }
    @Test
    void validFreeFormBox() {
        SimpleSolver s = new SimpleSolver(9);
        SimpleBoard b = new SimpleBoard(9);

        b.setGroup(0,0,0);
        b.setGroup(1,0,0);
        b.setGroup(2,0,0);
        b.setGroup(2,1,0);
        b.setGroup(1,1,0);
        b.setGroup(0,1,0);
        b.setGroup(0,2,0);
        b.setGroup(1,2,0);
        b.setGroup(2,2,0);
        b.setGroup(3,0,1);
        b.setGroup(4,0,1);
        b.setGroup(5,0,1);
        b.setGroup(5,1,1);
        b.setGroup(4,1,1);
        b.setGroup(3,1,1);
        b.setGroup(3,2,1);
        b.setGroup(4,2,1);
        b.setGroup(5,2,1);
        b.setGroup(6,0,2);
        b.setGroup(7,0,2);
        b.setGroup(8,0,2);
        b.setGroup(8,1,2);
        b.setGroup(7,1,2);
        b.setGroup(6,1,2);
        b.setGroup(6,2,2);
        b.setGroup(7,2,2);
        b.setGroup(8,2,2);
        b.setGroup(0,3,3);
        b.setGroup(1,3,3);
        b.setGroup(2,3,3);
        b.setGroup(2,4,3);
        b.setGroup(1,4,3);
        b.setGroup(0,4,3);
        b.setGroup(0,5,3);
        b.setGroup(1,5,3);
        b.setGroup(2,5,3);
        b.setGroup(3,3,4);
        b.setGroup(4,3,4);
        b.setGroup(5,3,4);
        b.setGroup(5,4,4);
        b.setGroup(4,4,4);
        b.setGroup(3,4,4);
        b.setGroup(3,5,4);
        b.setGroup(4,5,4);
        b.setGroup(5,5,4);
        b.setGroup(6,3,5);
        b.setGroup(7,3,5);
        b.setGroup(8,3,5);
        b.setGroup(8,4,5);
        b.setGroup(7,4,5);
        b.setGroup(6,4,5);
        b.setGroup(6,5,5);
        b.setGroup(7,5,5);
        b.setGroup(8,5,5);
        b.setGroup(0,6,6);
        b.setGroup(1,6,6);
        b.setGroup(2,6,6);
        b.setGroup(2,7,6);
        b.setGroup(1,7,6);
        b.setGroup(0,7,6);
        b.setGroup(0,8,6);
        b.setGroup(1,8,6);
        b.setGroup(2,8,6);
        b.setGroup(3,6,7);
        b.setGroup(4,6,7);
        b.setGroup(5,6,7);
        b.setGroup(5,7,7);
        b.setGroup(4,7,7);
        b.setGroup(3,7,7);
        b.setGroup(3,8,7);
        b.setGroup(4,8,7);
        b.setGroup(5,8,7);
        b.setGroup(6,6,8);
        b.setGroup(7,6,8);
        b.setGroup(8,6,8);
        b.setGroup(8,7,8);
        b.setGroup(7,7,8);
        b.setGroup(6,7,8);
        b.setGroup(6,8,8);
        b.setGroup(7,8,8);
        b.setGroup(8,8,8);
        for (int i = 0; i < 9; i++){
            assertTrue(s.validBox(b,i));
        }
        b.setValue(0, 0, 5);
        b.setValue(1, 0, 3);
        b.setValue(2, 0, 4);
        b.setValue(3, 0, 6);
        //b.setValue(4, 0, 7);
        //b.setValue(5, 0, 8);
        b.setValue(6, 0, 9);
        b.setValue(7, 0, 1);
        b.setValue(8, 0, 2);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 1, 6);
        b.setValue(1, 1, 7);
        b.setValue(2, 1, 2);
        b.setValue(3, 1, 1);
        b.setValue(4, 1, 9);
        b.setValue(5, 1, 5);
        b.setValue(6, 1, 3);
        b.setValue(7, 1, 4);
        b.setValue(8, 1, 8);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 2, 1);
        b.setValue(1, 2, 9);
        b.setValue(2, 2, 8);
        b.setValue(3, 2, 3);
        b.setValue(4, 2, 4);
        b.setValue(5, 2, 2);
        b.setValue(6, 2, 5);
        b.setValue(7, 2, 6);
        b.setValue(8, 2, 7);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 3, 8);
        b.setValue(1, 3, 5);
        b.setValue(2, 3, 9);
        b.setValue(3, 3, 7);
        //b.setValue(4, 3, 6);
        //b.setValue(5, 3, 1);
        b.setValue(6, 3, 4);
        b.setValue(7, 3, 2);
        b.setValue(8, 3, 3);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 4, 4);
        b.setValue(1, 4, 2);
        b.setValue(2, 4, 6);
        b.setValue(3, 4, 8);
        b.setValue(4, 4, 5);
        b.setValue(5, 4, 3);
        b.setValue(6, 4, 7);
        b.setValue(7, 4, 9);
        b.setValue(8, 4, 1);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 5, 7);
        b.setValue(1, 5, 1);
        b.setValue(2, 5, 3);
        b.setValue(3, 5, 9);
        b.setValue(4, 5, 2);
        b.setValue(5, 5, 4);
        b.setValue(6, 5, 8);
        b.setValue(7, 5, 5);
        b.setValue(8, 5, 6);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 6, 9);
        b.setValue(1, 6, 6);
        b.setValue(2, 6, 1);
        b.setValue(3, 6, 5);
       //b.setValue(4, 6, 3);
       //b.setValue(5, 6, 7);
        b.setValue(6, 6, 2);
        b.setValue(7, 6, 8);
        b.setValue(8, 6, 4);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 7, 2);
        b.setValue(1, 7, 8);
        b.setValue(2, 7, 7);
        b.setValue(3, 7, 4);
        b.setValue(4, 7, 1);
        b.setValue(5, 7, 9);
        b.setValue(6, 7, 6);
        b.setValue(7, 7, 3);
        b.setValue(8, 7, 5);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 8, 3);
        b.setValue(1, 8, 4);
        b.setValue(2, 8, 5);
        b.setValue(3, 8, 2);
        //b.setValue(4, 8, 8);
        b.setValue(5, 8, 6);
        b.setValue(6, 8, 1);
        b.setValue(7, 8, 7);
        assertTrue(s.valid(b));
        SimpleBoard sol = s.solve(b);
        assertTrue(sol != null);
        assertTrue(sol.get(0,0).getValue() > 0);
    }
    @Test
    void validFreeFormBoxRow() {
        SimpleSolver s = new SimpleSolver(9);
        SimpleBoard b = new SimpleBoard(9);
        b.setGroup(0, 1, 2);
        b.setGroup(1, 1, 2);
        b.setGroup(2, 1, 2);
        b.setGroup(3, 1, 2);
        b.setGroup(4, 1, 2);
        b.setGroup(5, 1, 2);
        b.setGroup(6, 1, 2);
        b.setGroup(7, 1, 2);
        b.setGroup(8, 1, 2);
        //b.setValue(0, 1, 1);
        b.setValue(1, 1, 1);
        b.setValue(2, 1, 2);
        b.setValue(3, 1, 3);
        b.setValue(4, 1, 4);
        b.setValue(5, 1, 5);
        b.setValue(6, 1, 6);
        b.setValue(7, 1, 7);
        b.setValue(8, 1, 8);
        assertTrue(s.validBox(b, 2));

        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));
    }
    @Test
    void validFreeFormBoxEmpty() {
        SimpleSolver s = new SimpleSolver(9);
        SimpleBoard b = new SimpleBoard(9);

        b.setGroup(0, 0, 0);
        b.setGroup(1, 0, 0);
        b.setGroup(2, 0, 0);
        b.setGroup(2, 1, 0);
        b.setGroup(1, 1, 0);
        b.setGroup(0, 1, 0);
        b.setGroup(0, 2, 0);
        b.setGroup(1, 2, 0);
        b.setGroup(2, 2, 0);
        b.setGroup(3, 0, 1);
        b.setGroup(4, 0, 1);
        b.setGroup(5, 0, 1);
        b.setGroup(5, 1, 1);
        b.setGroup(4, 1, 1);
        b.setGroup(3, 1, 1);
        b.setGroup(3, 2, 1);
        b.setGroup(4, 2, 1);
        b.setGroup(5, 2, 1);
        b.setGroup(6, 0, 2);
        b.setGroup(7, 0, 2);
        b.setGroup(8, 0, 2);
        b.setGroup(8, 1, 2);
        b.setGroup(7, 1, 2);
        b.setGroup(6, 1, 2);
        b.setGroup(6, 2, 2);
        b.setGroup(7, 2, 2);
        b.setGroup(8, 2, 2);
        b.setGroup(0, 3, 3);
        b.setGroup(1, 3, 3);
        b.setGroup(2, 3, 3);
        b.setGroup(2, 4, 3);
        b.setGroup(1, 4, 3);
        b.setGroup(0, 4, 3);
        b.setGroup(0, 5, 3);
        b.setGroup(1, 5, 3);
        b.setGroup(2, 5, 3);
        b.setGroup(3, 3, 4);
        b.setGroup(4, 3, 4);
        b.setGroup(5, 3, 4);
        b.setGroup(5, 4, 4);
        b.setGroup(4, 4, 4);
        b.setGroup(3, 4, 4);
        b.setGroup(3, 5, 4);
        b.setGroup(4, 5, 4);
        b.setGroup(5, 5, 4);
        b.setGroup(6, 3, 5);
        b.setGroup(7, 3, 5);
        b.setGroup(8, 3, 5);
        b.setGroup(8, 4, 5);
        b.setGroup(7, 4, 5);
        b.setGroup(6, 4, 5);
        b.setGroup(6, 5, 5);
        b.setGroup(7, 5, 5);
        b.setGroup(8, 5, 5);
        b.setGroup(0, 6, 6);
        b.setGroup(1, 6, 6);
        b.setGroup(2, 6, 6);
        b.setGroup(2, 7, 6);
        b.setGroup(1, 7, 6);
        b.setGroup(0, 7, 6);
        b.setGroup(0, 8, 6);
        b.setGroup(1, 8, 6);
        b.setGroup(2, 8, 6);
        b.setGroup(3, 6, 7);
        b.setGroup(4, 6, 7);
        b.setGroup(5, 6, 7);
        b.setGroup(5, 7, 7);
        b.setGroup(4, 7, 7);
        b.setGroup(3, 7, 7);
        b.setGroup(3, 8, 7);
        b.setGroup(4, 8, 7);
        b.setGroup(5, 8, 7);
        b.setGroup(6, 6, 8);
        b.setGroup(7, 6, 8);
        b.setGroup(8, 6, 8);
        b.setGroup(8, 7, 8);
        b.setGroup(7, 7, 8);
        b.setGroup(6, 7, 8);
        b.setGroup(6, 8, 8);
        b.setGroup(7, 8, 8);
        b.setGroup(8, 8, 8);
        for (int i = 0; i < 9; i++) {
            assertTrue(s.validBox(b, i));
        }
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));
        b.setValue(0, 0, 5);
        b.setValue(1, 0, 3);
        b.setValue(2, 0, 4);
        b.setValue(3, 0, 6);
        b.setValue(4, 0, 7);
        b.setValue(5, 0, 8);
        b.setValue(6, 0, 9);
        b.setValue(7, 0, 1);
        b.setValue(8, 0, 2);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 1, 6);
        b.setValue(1, 1, 7);
        b.setValue(2, 1, 2);
        b.setValue(3, 1, 1);
        b.setValue(4, 1, 9);
        b.setValue(5, 1, 5);
        b.setValue(6, 1, 3);
        b.setValue(7, 1, 4);
        b.setValue(8, 1, 8);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 2, 1);
        b.setValue(1, 2, 9);
        b.setValue(2, 2, 8);
        b.setValue(3, 2, 3);
        b.setValue(4, 2, 4);
        b.setValue(5, 2, 2);
        b.setValue(6, 2, 5);
        b.setValue(7, 2, 6);
        b.setValue(8, 2, 7);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));

        b.setValue(0, 3, 8);
        b.setValue(1, 3, 5);
        b.setValue(2, 3, 9);
        b.setValue(3, 3, 7);
        b.setValue(4, 3, 6);
        b.setValue(5, 3, 1);
        b.setValue(6, 3, 4);
        b.setValue(7, 3, 2);
        b.setValue(8, 3, 3);
        assertTrue(s.valid(b));
        assertTrue(!s.validAndFull(b));
        //SimpleBoard sol = s.solve(b);
        //assertTrue(sol != null);

    }
}