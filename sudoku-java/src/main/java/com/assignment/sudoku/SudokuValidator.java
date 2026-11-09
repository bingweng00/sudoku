package com.assignment.sudoku;

import java.util.ArrayList;
import java.util.List;

/** Checks duplicate values in rows, columns, and 3x3 boxes. */
public final class SudokuValidator {
    public List<String> violations(SudokuBoard board) {
        List<String> issues = new ArrayList<>();
        int[][] cells = board.copyCells();
        for (int r = 0; r < 9; r++) findDuplicate(cells[r], "Row " + (char)('A' + r), issues);
        for (int c = 0; c < 9; c++) {
            int[] values = new int[9];
            for (int r = 0; r < 9; r++) values[r] = cells[r][c];
            findDuplicate(values, "Column " + (c + 1), issues);
        }
        for (int br = 0; br < 9; br += 3) for (int bc = 0; bc < 9; bc += 3) {
            int[] values = new int[9];
            int i = 0;
            for (int r = br; r < br + 3; r++) for (int c = bc; c < bc + 3; c++) values[i++] = cells[r][c];
            findDuplicate(values, "3x3 subgrid (" + (char)('A' + br) + "-" + (char)('A' + br + 2) + ", " + (bc + 1) + "-" + (bc + 3) + ")", issues);
        }
        return issues;
    }

    private void findDuplicate(int[] values, String unit, List<String> issues) {
        boolean[] seen = new boolean[10];
        for (int value : values) {
            if (value == 0) continue;
            if (seen[value]) {
                issues.add("Number " + value + " already exists in " + unit + ".");
                return;
            }
            seen[value] = true;
        }
    }

    public boolean isValid(SudokuBoard board) { return violations(board).isEmpty(); }
}
