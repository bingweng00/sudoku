package com.assignment.sudoku;

import java.util.Arrays;

/** Mutable Sudoku grid with immutable clue cells. */
public final class SudokuBoard {
    public static final int SIZE = 9;
    private final int[][] cells;
    private final boolean[][] clues;

    public SudokuBoard(int[][] initial) {
        if (initial == null || initial.length != SIZE) throw new IllegalArgumentException("Board must have 9 rows.");
        cells = new int[SIZE][SIZE];
        clues = new boolean[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            if (initial[r] == null || initial[r].length != SIZE) throw new IllegalArgumentException("Each row must have 9 cells.");
            for (int c = 0; c < SIZE; c++) {
                int value = initial[r][c];
                if (value < 0 || value > 9) throw new IllegalArgumentException("Cell values must be between 0 and 9.");
                cells[r][c] = value;
                clues[r][c] = value != 0;
            }
        }
    }

    public int get(int row, int col) { return cells[row][col]; }
    public boolean isClue(int row, int col) { return clues[row][col]; }
    public boolean isEmpty(int row, int col) { return cells[row][col] == 0; }

    public void set(int row, int col, int value) {
        if (clues[row][col]) throw new IllegalArgumentException("Cell is pre-filled.");
        if (value < 1 || value > 9) throw new IllegalArgumentException("Number must be between 1 and 9.");
        cells[row][col] = value;
    }

    public void clear(int row, int col) {
        if (clues[row][col]) throw new IllegalArgumentException("Cell is pre-filled.");
        cells[row][col] = 0;
    }

    public int[][] copyCells() {
        int[][] copy = new int[SIZE][];
        for (int r = 0; r < SIZE; r++) copy[r] = Arrays.copyOf(cells[r], SIZE);
        return copy;
    }

    public boolean isComplete() {
        for (int[] row : cells) for (int value : row) if (value == 0) return false;
        return true;
    }
}
