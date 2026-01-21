package com.theapache64.klokk.movement

import com.theapache64.klokk.COLUMNS
import com.theapache64.klokk.ROWS
import com.theapache64.klokk.model.ClockData
import com.theapache64.klokk.movement.core.MatrixGenerator
import com.theapache64.klokk.movement.core.Movement

/**
 * Responsible to show Square, Flower, Wings and Star transition
 */
class TranceMatrixGenerator(data: Movement.Trance) : MatrixGenerator<Movement.Trance>(data) {
    companion object {
        /**
         * Trance matrix is a 2x2 matrix pattern spanned to ROWSxCOLUMN
         */
        fun getTranceMatrix(trance: Movement.Trance): List<List<ClockData>> {

            // 0,0
            val evenDegreeOne = when (trance.to) {
                Movement.Trance.To.SQUARE -> {
                    ClockData(
                        degreeOne = 90f,
                        degreeTwo = 180f
                    )
                }

                Movement.Trance.To.FLOWER -> {
                    ClockData(
                        degreeOne = 135f,
                        degreeTwo = 135f
                    )
                }
                Movement.Trance.To.FLY -> {
                    ClockData(
                        degreeOne = 45f,
                        degreeTwo = 225f
                    )
                }
                Movement.Trance.To.STAR -> {
                    ClockData(
                        degreeOne = -45f,
                        degreeTwo = 315f
                    )
                }
            }

            // 0,1
            val evenDegreeTwo = when (trance.to) {
                Movement.Trance.To.SQUARE -> {
                    // Square
                    ClockData(
                        degreeOne = 180f,
                        degreeTwo = 270f
                    )
                }
                Movement.Trance.To.FLOWER -> {
                    ClockData(
                        degreeOne = 225f,
                        degreeTwo = 225f
                    )
                }
                Movement.Trance.To.FLY -> {
                    ClockData(
                        degreeOne = 135f,
                        degreeTwo = 315f
                    )
                }
                Movement.Trance.To.STAR -> {
                    ClockData(
                        degreeOne = 45f,
                        degreeTwo = 45f + 360
                    )
                }
            }


            // 1,0
            val oddDegreeOne = when (trance.to) {
                Movement.Trance.To.SQUARE -> {
                    // Square
                    ClockData(
                        degreeOne = 0f,
                        degreeTwo = 90f
                    )
                }
                Movement.Trance.To.FLOWER -> {
                    ClockData(
                        degreeOne = 45f,
                        degreeTwo = 45f
                    )
                }
                Movement.Trance.To.FLY -> {
                    ClockData(
                        degreeOne = 135f,
                        degreeTwo = 315f
                    )
                }
                Movement.Trance.To.STAR -> {
                    ClockData(
                        degreeOne = -135f,
                        degreeTwo = 225f
                    )
                }
            }

            // 1,1
            val oddDegreeTwo = when (trance.to) {
                Movement.Trance.To.SQUARE -> {
                    // Square
                    ClockData(
                        degreeOne = 270f,
                        degreeTwo = 360f
                    )
                }
                Movement.Trance.To.FLOWER -> {
                    ClockData(
                        degreeOne = 315f,
                        degreeTwo = 315f
                    )
                }
                Movement.Trance.To.FLY -> {
                    ClockData(
                        degreeOne = 45f,
                        degreeTwo = 225f
                    )
                }
                Movement.Trance.To.STAR -> {
                    ClockData(
                        degreeOne = 135f,
                        degreeTwo = 135f + 360
                    )
                }
            }

            return mutableListOf<List<ClockData>>().apply {
                val columnRepeat = (COLUMNS / 2)

                // Building matrix
                repeat(ROWS) { rowIndex ->
                    val row = mutableListOf<ClockData>()
                    repeat(columnRepeat) {
                        if (rowIndex % 2 == 0) {
                            // even row
                            row.add(evenDegreeOne)
                            row.add(evenDegreeTwo)
                        } else {
                            // odd row
                            row.add(oddDegreeOne)
                            row.add(oddDegreeTwo)
                        }
                    }

                    // filling remaining columns
                    val remColumn = COLUMNS % 2
                    if (remColumn > 0) {
                        require(remColumn == 1) { "Expected remaining column is 1, but found $remColumn" }

                        // even
                        val lastColumn = if (rowIndex % 2 == 0) {
                            evenDegreeOne
                        } else {
                            oddDegreeOne
                        }

                        row.add(lastColumn)
                    }

                    add(row)
                }

                // has rem row
                val remRow = ROWS % 2

                // Add remaining row
                if (remRow > 1) {
                    require(remRow == 1) { "Expected 1 remaining row, but found $remRow" }
                    add(get(0))
                }
            }
        }
    }

    override fun generateMatrix(): List<List<ClockData>> {
        return getTranceMatrix(data)
    }
}