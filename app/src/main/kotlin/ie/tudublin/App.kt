package ie.tudublin

import java.io.File
import kotlin.system.exitProcess

/** Formats a board as text, with lines between the 3x3 boxes. Empty cells are shown as '.' */
fun formatBoard(board: List<List<Int>>): String {
    val sb = StringBuilder()
    for (i in 0..8) {
        for (j in 0..8) {
            sb.append(if (board[i][j] == 0) '.' else ('0' + board[i][j]))
            sb.append(" ")
            if (j == 2 || j == 5) sb.append("| ")
        }
        sb.append("\n")
        if (i == 2 || i == 5) sb.append("------+-------+------\n")
    }
    return sb.toString()
}

/** Reads a board from a text file: one row per line, blank lines and spaces are ignored */
fun readBoard(file: File): List<String> =
    file.readLines()
        .map { line -> line.filterNot { it.isWhitespace() } }
        .filter { it.isNotEmpty() }

fun main(args: Array<String>) {
    if (args.size != 1) {
        System.err.println("Usage: sudoku <board-file>")
        System.err.println("The file should contain 9 lines of 9 characters, using 1-9 for given numbers and 0 or . for empty cells.")
        exitProcess(1)
    }

    val file = File(args[0])
    if (!file.isFile) {
        System.err.println("Error: could not find file '${args[0]}'")
        exitProcess(1)
    }

    val sudoku = try {
        Sudoku(readBoard(file))
    } catch (e: IllegalArgumentException) {
        System.err.println("Error: invalid board - ${e.message}")
        exitProcess(1)
    }

    println("Input board:\n")
    println(formatBoard(sudoku.board()))

    if (sudoku.solve()) {
        println("Solution:\n")
        println(formatBoard(sudoku.board()))
    } else {
        println("Could not find a solution for this board.")
        exitProcess(2)
    }
}
