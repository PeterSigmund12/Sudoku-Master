package at.jku.se.controller;

import at.jku.se.sudokumaster.SimpleBoard;
import at.jku.se.sudokumaster.SimpleSolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleSolverTestMainMethods {

    @Test
    void validRow() {
        SimpleSolver s = new SimpleSolver(9);
        SimpleBoard b = new SimpleBoard(9);
        b.setValue(0,0,9);
        assertTrue(s.validRow(b,0,0,0));

        b.setValue(0,1,9);
        b.setValue(1,1,9);
        assertFalse(s.validRow(b, 1, 0, 0));
    }

    @Test
    void validColumn() {
        SimpleSolver s = new SimpleSolver(9);
        SimpleBoard b = new SimpleBoard(9);
        b.setValue(0,0,9);
        assertTrue(s.validColumn(b,0,0,0));

        b.setValue(1,0,9);
        b.setValue(1,1,9);
        assertFalse(s.validColumn(b, 1, 0, 0));
    }

    @Test
    void validBox() {
        SimpleSolver s = new SimpleSolver(9);
        SimpleBoard b = new SimpleBoard(9);
        b.setValue(0,0,9);
        assertTrue(s.validBox(b,0,0));
        b.setValue(1,1,9);
        assertFalse(s.validBox(b, 0, 0));
    }

    @Test
    void validFreefromBox() {
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
        assertTrue(s.validBox(b,0));
        b.setValue(0, 0, 5);
        b.setValue(1, 0, 3);
        b.setValue(2, 0, 4);
        b.setValue(3, 0, 6);
        assertTrue(s.validBox(b,0));
        b.setValue(4, 0, 6);
        assertFalse(s.validBox(b, 0));

    }
}