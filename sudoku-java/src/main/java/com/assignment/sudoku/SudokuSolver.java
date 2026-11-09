package com.assignment.sudoku;

/** Backtracking solver with a minimum-remaining-values choice of next cell. */
public final class SudokuSolver {
    public boolean solve(int[][] grid) {
        int[] cell = findBestEmpty(grid);
        if (cell == null) return true;
        int row = cell[0], col = cell[1];
        boolean[] candidates = candidates(grid, row, col);
        for (int value = 1; value <= 9; value++) {
            if (!candidates[value]) continue;
            grid[row][col] = value;
            if (solve(grid)) return true;
            grid[row][col] = 0;
        }
        return false;
    }

    public int solutionFor(int[][] grid, int row, int col) {
        int[][] copy = new int[9][];
        for (int i = 0; i < 9; i++) copy[i] = grid[i].clone();
        if (copy[row][col] != 0) return copy[row][col];
        return solve(copy) ? copy[row][col] : 0;
    }

    private int[] findBestEmpty(int[][] grid) {
        int[] best = null;
        int bestCount = 10;
        for (int r = 0; r < 9; r++) for (int c = 0; c < 9; c++) {
            if (grid[r][c] != 0) continue;
            boolean[] options = candidates(grid, r, c);
            int count = 0;
            for (int v = 1; v <= 9; v++) if (options[v]) count++;
            if (count < bestCount) { bestCount = count; best = new int[]{r, c}; }
            if (count == 0) return new int[]{r, c};
        }
        return best;
    }

    private boolean[] candidates(int[][] grid, int row, int col) {
        boolean[] allowed = new boolean[10];
        java.util.Arrays.fill(allowed, true);
        allowed[0] = false;
        for (int i = 0; i < 9; i++) {
            allowed[grid[row][i]] = false;
            allowed[grid[i][col]] = false;
        }
        int br = row / 3 * 3, bc = col / 3 * 3;
        for (int r = br; r < br + 3; r++) for (int c = bc; c < bc + 3; c++) allowed[grid[r][c]] = false;
        return allowed;
    }
}
