package ie.tudublin

/**
 * Backtracking sudoku solver, based on the Kotlin solution on Rosetta Code
 * (https://rosettacode.org/wiki/Sudoku#Kotlin).
 *
 * This class only contains the solving logic - all input/output is handled in App.kt.
 *
 * Each row is a 9 character string. Digits 1-9 are given cells and '0' or '.' is an empty cell.
 */
class Sudoku(rows: List<String>) {
    private val grid = IntArray(81)
    private var solved = false

    /** Number of times placeNumber() was called during the last solve() */
    var iterations = 0L
        private set

    /** True if the last solve() gave up because it hit MAX_ITERATIONS */
    var gaveUp = false
        private set

    init {
        require(rows.size == 9 && rows.all { it.length == 9 }) {
            "Grid must be 9 x 9"
        }
        for (i in 0..8) {
            for (j in 0..8) {
                val c = rows[i][j]
                grid[9 * i + j] = when (c) {
                    '.', '0' -> 0
                    in '1'..'9' -> c - '0'
                    else -> throw IllegalArgumentException("Invalid character '$c' in row ${i + 1}")
                }
            }
        }
    }

    /**
     * Tries to solve the board. Returns true if a solution was found, or false if the board
     * is unsolvable (or could not be solved within MAX_ITERATIONS attempts).
     */
    fun solve(): Boolean {
        solved = false
        gaveUp = false
        iterations = 0
        if (!givensAreValid()) return false
        placeNumber(0)
        return solved
    }

    /** Returns a copy of the board as 9 rows of 9 numbers (0 = empty cell) */
    fun board(): List<List<Int>> = (0..8).map { i -> (0..8).map { j -> grid[9 * i + j] } }

    private fun placeNumber(pos: Int) {
        if (solved || gaveUp) return
        if (++iterations > MAX_ITERATIONS) {
            gaveUp = true
            return
        }
        if (pos == 81) {
            solved = true
            return
        }
        if (grid[pos] > 0) {
            placeNumber(pos + 1)
            return
        }
        for (n in 1..9) {
            if (checkValidity(n, pos % 9, pos / 9)) {
                grid[pos] = n
                placeNumber(pos + 1)
                if (solved) return
                grid[pos] = 0
                if (gaveUp) return
            }
        }
    }

    private fun checkValidity(v: Int, x: Int, y: Int): Boolean {
        for (i in 0..8) {
            if (grid[y * 9 + i] == v || grid[i * 9 + x] == v) return false
        }
        val startX = (x / 3) * 3
        val startY = (y / 3) * 3
        for (i in startY until startY + 3) {
            for (j in startX until startX + 3) {
                if (grid[i * 9 + j] == v) return false
            }
        }
        return true
    }

    /** Checks that none of the given numbers clash with each other (e.g. two 5s in one row) */
    private fun givensAreValid(): Boolean {
        for (pos in 0 until 81) {
            val v = grid[pos]
            if (v == 0) continue
            grid[pos] = 0
            val ok = checkValidity(v, pos % 9, pos / 9)
            grid[pos] = v
            if (!ok) return false
        }
        return true
    }

    companion object {
        /** Give up and declare the board unsolvable after this many iterations */
        const val MAX_ITERATIONS = 2_000_000L
    }
}
