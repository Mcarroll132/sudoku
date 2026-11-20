package ie.tudublin

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class SudokuTest {

    /** Converts expected solution strings into the same shape as Sudoku.board() */
    private fun toBoard(rows: List<String>): List<List<Int>> =
        rows.map { row -> row.map { it - '0' } }

    @Test
    fun solvesRosettaCodeBoard() {
        val sudoku = Sudoku(
            listOf(
                "850002400",
                "720000009",
                "004000000",
                "000107002",
                "305000900",
                "040000000",
                "000080070",
                "017000000",
                "000036040",
            )
        )
        val expected = listOf(
            "859612437",
            "723854169",
            "164379528",
            "986147352",
            "375268914",
            "241593786",
            "432981675",
            "617425893",
            "598736241",
        )

        assertTrue(sudoku.solve())
        assertEquals(toBoard(expected), sudoku.board())
    }

    @Test
    fun solvesWikipediaBoard() {
        // Uses '.' for empty cells to check that format is accepted too
        val sudoku = Sudoku(
            listOf(
                "53..7....",
                "6..195...",
                ".98....6.",
                "8...6...3",
                "4..8.3..1",
                "7...2...6",
                ".6....28.",
                "...419..5",
                "....8..79",
            )
        )
        val expected = listOf(
            "534678912",
            "672195348",
            "198342567",
            "859761423",
            "426853791",
            "713924856",
            "961537284",
            "287419635",
            "345286179",
        )

        assertTrue(sudoku.solve())
        assertEquals(toBoard(expected), sudoku.board())
    }

    @Test
    fun givesUpOnUnsolvableBoard() {
        // The bottom-right cell can only be 9, but there is already a 9 in that column,
        // so there is no solution. The solver should give up after MAX_ITERATIONS.
        val sudoku = Sudoku(
            listOf(
                "000000009",
                "000000000",
                "000000000",
                "000000000",
                "000000000",
                "000000000",
                "000000000",
                "000000000",
                "123456780",
            )
        )

        assertFalse(sudoku.solve())
        assertTrue(sudoku.gaveUp)
        assertTrue(sudoku.iterations > Sudoku.MAX_ITERATIONS)
    }
}
