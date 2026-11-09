package com.assignment.sudoku;

import java.util.Scanner;

/** Terminal entry point. */
public final class Main {
    private static final int[][] STARTING_PUZZLE = {
        {5,3,0,0,7,0,0,0,0}, {6,0,0,1,9,5,0,0,0}, {0,9,8,0,0,0,0,6,0},
        {8,0,0,0,6,0,0,0,3}, {4,0,0,8,0,3,0,0,1}, {7,0,0,0,2,0,0,0,6},
        {0,6,0,0,0,0,2,8,0}, {0,0,0,4,1,9,0,0,5}, {0,0,0,0,8,0,0,7,9}
    };

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean replay = true;
            while (replay) {
                SudokuGame game = new SudokuGame(STARTING_PUZZLE);
                System.out.println("Welcome to Sudoku!\n\nHere is your puzzle:");
                printBoard(game.board());
                while (!game.isWon()) {
                    System.out.print("\nEnter command (e.g., A3 4, C5 clear, hint, check, quit):\n");
                    if (!scanner.hasNextLine()) return;
                    String result = game.execute(scanner.nextLine());
                    if (result.equals("QUIT")) return;
                    System.out.println(result);
                    if (result.equals("Move accepted.") || result.startsWith("Hint:")) {
                        System.out.println("\nCurrent grid:");
                        printBoard(game.board());
                    }
                }
                System.out.println("\nYou have successfully completed the Sudoku puzzle!");
                System.out.print("Press Enter to play again, or type quit to exit: ");
                if (!scanner.hasNextLine() || scanner.nextLine().trim().equalsIgnoreCase("quit")) replay = false;
            }
        }
    }

    static void printBoard(SudokuBoard board) {
        System.out.println("    1 2 3 4 5 6 7 8 9");
        for (int r = 0; r < 9; r++) {
            System.out.print("  " + (char)('A' + r) + " ");
            for (int c = 0; c < 9; c++) System.out.print((board.get(r, c) == 0 ? "_" : board.get(r, c)) + (c == 8 ? "" : " "));
            System.out.println();
        }
    }
}
