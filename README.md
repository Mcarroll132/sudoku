# Sudoku solver

A command-line sudoku solver written in Kotlin, built with Gradle (Software Testing, Lab 10).
The solver is based on the [Rosetta Code Kotlin solution](https://rosettacode.org/wiki/Sudoku#Kotlin),
with a limit of 2,000,000 iterations, after which a board is declared unsolvable.

## Running the release

1. Download `app.zip` from the latest release and unzip it.
2. Run the `sudoku` script with a board file (Java 17 or newer must be installed):

```
cd app
bin/sudoku puzzles/rosetta.txt        # macOS / Linux
bin\sudoku.bat puzzles\rosetta.txt    # Windows
```

## Board file format

Nine lines, one per row, each with nine characters. Use `1`-`9` for given numbers and `0` or `.` for empty cells:

```
53..7....
6..195...
.98....6.
8...6...3
4..8.3..1
7...2...6
.6....28.
...419..5
....8..79
```

Example boards are in the `puzzles` folder. `unsolvable.txt` has no solution, so the app prints an error for it.

## Building from source

```
./gradlew build                      # compile, test and package into app/build/distributions/app.zip
./gradlew test                       # run the unit tests
./gradlew run --args="../puzzles/rosetta.txt"
```
