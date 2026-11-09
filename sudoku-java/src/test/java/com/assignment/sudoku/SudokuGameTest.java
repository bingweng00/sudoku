package com.assignment.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SudokuGameTest {
    private static final int[][] PUZZLE = {
        {5,3,0,0,7,0,0,0,0}, {6,0,0,1,9,5,0,0,0}, {0,9,8,0,0,0,0,6,0},
        {8,0,0,0,6,0,0,0,3}, {4,0,0,8,0,3,0,0,1}, {7,0,0,0,2,0,0,0,6},
        {0,6,0,0,0,0,2,8,0}, {0,0,0,4,1,9,0,0,5}, {0,0,0,0,8,0,0,7,9}
    };

    @Test void acceptsMoveAndClear() {
        SudokuGame game = new SudokuGame(PUZZLE);
        assertEquals("Move accepted.", game.execute("A3 4"));
        assertEquals(4, game.board().get(0,2));
        assertEquals("Move accepted.", game.execute("A3 clear"));
        assertEquals(0, game.board().get(0,2));
    }

    @Test void rejectsEditingClueAndInvalidCommands() {
        SudokuGame game = new SudokuGame(PUZZLE);
        
        assertTrue(game.execute("A1 6").contains("Cannot edit clue")); 
        assertTrue(game.execute("A3 0").startsWith("Invalid command"));
        assertTrue(game.execute("A3 10").startsWith("Invalid command"));
    }

    @Test void hintFillsSolutionValue() {
        SudokuGame game = new SudokuGame(PUZZLE);
        assertEquals("Hint: Cell A3 = 4", game.execute("hint"));
        assertEquals(4, game.board().get(0,2));
    }

    @Test void validatorDetectsRowColumnAndBoxConflicts() {
        SudokuGame game = new SudokuGame(PUZZLE);
        game.execute("A3 3");
        assertTrue(game.execute("check").contains("Row A"));
        game.execute("A3 clear");
        game.execute("C1 5");
        assertTrue(game.execute("check").contains("Column 1"));
        game.execute("C1 clear");
        game.execute("B3 8");
        assertTrue(game.execute("check").contains("3x3 subgrid"));
    }

    @Test void solvedPuzzleWins() {
        SudokuGame game = new SudokuGame(PUZZLE);
        int[][] solved = {
            {5,3,4,6,7,8,9,1,2},{6,7,2,1,9,5,3,4,8},{1,9,8,3,4,2,5,6,7},
            {8,5,9,7,6,1,4,2,3},{4,2,6,8,5,3,7,9,1},{7,1,3,9,2,4,8,5,6},
            {9,6,1,5,3,7,2,8,4},{2,8,7,4,1,9,6,3,5},{3,4,5,2,8,6,1,7,9}
        };
        for (int r=0;r<9;r++) for (int c=0;c<9;c++) if (!game.board().isClue(r,c)) game.execute("" + (char)('A'+r) + (c+1) + " " + solved[r][c]);
        assertTrue(game.isWon());
    }
}
