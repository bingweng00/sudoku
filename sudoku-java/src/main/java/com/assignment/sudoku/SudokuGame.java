package com.assignment.sudoku;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Application rules and command parsing for the Sudoku CLI. */
public final class SudokuGame {
    private static final Pattern MOVE = Pattern.compile("^([A-Ia-i])([1-9])\\s+(\\d|clear)$", Pattern.CASE_INSENSITIVE);
    private final SudokuBoard board;
    private final SudokuValidator validator = new SudokuValidator();
    private final SudokuSolver solver = new SudokuSolver();
    private final int[][] solution;

    public SudokuGame(int[][] puzzle) {
        board = new SudokuBoard(puzzle);
        solution = board.copyCells();
        if (!solver.solve(solution)) throw new IllegalArgumentException("Starting puzzle has no solution.");
    }

    public SudokuBoard board() { return board; }
    public SudokuValidator validator() { return validator; }
    public boolean isWon() { return board.isComplete() && validator.isValid(board) && matchesSolution(); }

    public String execute(String input) {
        if (input == null) return "Please enter a command.";
        String command = input.trim();
        if (command.equalsIgnoreCase("quit")) return "QUIT";
        if (command.equalsIgnoreCase("check")) {
            var issues = validator.violations(board);
            return issues.isEmpty() ? "No rule violations detected." : String.join("\n", issues);
        }
        if (command.equalsIgnoreCase("hint")) return hint();
        Matcher matcher = MOVE.matcher(command);
        if (!matcher.matches()) return "Invalid command. Use A3 4, C5 clear, hint, check, or quit.";
        int row = Character.toUpperCase(matcher.group(1).charAt(0)) - 'A';
        int col = Integer.parseInt(matcher.group(2)) - 1;
        if (board.isClue(row, col)) return "Invalid move. " + Character.toUpperCase(matcher.group(1).charAt(0)) + matcher.group(2) + " is pre-filled.";
        String value = matcher.group(3).toLowerCase(Locale.ROOT);
        try {
            if (value.equals("clear")) board.clear(row, col);
            else board.set(row, col, Integer.parseInt(value));
        } catch (IllegalArgumentException exception) {
            return "Invalid move. " + exception.getMessage();
        }
        return "Move accepted.";
    }

    private String hint() {
        for (int r = 0; r < 9; r++) for (int c = 0; c < 9; c++) {
            if (board.isEmpty(r, c)) {
                board.set(r, c, solution[r][c]);
                return "Hint: Cell " + (char)('A' + r) + (c + 1) + " = " + solution[r][c];
            }
        }
        return "No empty cells remain.";
    }

    private boolean matchesSolution() {
        for (int r = 0; r < 9; r++) for (int c = 0; c < 9; c++) if (board.get(r, c) != solution[r][c]) return false;
        return true;
    }
}
