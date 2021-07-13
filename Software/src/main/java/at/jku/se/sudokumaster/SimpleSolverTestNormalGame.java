package at.jku.se.sudokumaster;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleSolverTestNormalGame {
    /**
     * Valid and complete.
     */
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

    /**
     * Test solve.
     */
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
        assertFalse(s.validAndFull(b));
        SimpleBoard sol = s.solve(b);
        assertEquals(9, (int) sol.get(8, 8).getValue());
    }

    /**
     * Test solve small.
     */
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
}