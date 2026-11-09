# Sudoku CLI (Java)

A command-line Sudoku game built with Java 17. It supports the supplied 30-clue puzzle, moves, clearing editable cells, hints, validation, quit, and replay.

## Environment
- Java 17+
- Maven 3.8+
- Windows, macOS, or Linux (terminal)

## Run
```bash
mvn test
mvn package
java -jar target/sudoku-cli-1.0.0.jar
```
Maven's default jar is not configured as executable; run with:
```bash
java -cp target/classes com.assignment.sudoku.Main
```

## Commands
- `A3 4` — set row A, column 3 to 4
- `C5 clear` — clear an editable cell
- `hint` — fill one empty cell with its solution value
- `check` — report row, column, and 3x3 box conflicts
- `quit` — exit

Rows are A–I and columns 1–9. Clues are immutable. Move values are range-checked, while conflicts are intentionally allowed during play so `check` can identify them (as shown in the assignment examples). Hints use a backtracking solver to find a correct value. The game completes only when all cells match the solution.

## Design
- `SudokuBoard` owns board state and clue immutability.
- `SudokuValidator` identifies row, column, and box duplicates.
- `SudokuSolver` solves a puzzle using backtracking and minimum-remaining-values cell selection.
- `SudokuGame` handles command parsing and game rules.
- `Main` provides the terminal loop and rendering.

The starting puzzle is the exact puzzle provided in the statement. A fixed puzzle is used because the statement specifies its 30 pre-filled cells; puzzle generation beyond this example is not otherwise defined.
