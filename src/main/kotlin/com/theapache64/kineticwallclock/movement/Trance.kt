package com.theapache64.kineticwallclock.movement

import com.theapache64.kineticwallclock.COLUMNS
import com.theapache64.kineticwallclock.ROWS
import com.theapache64.kineticwallclock.model.ClockData

/**
 * Flower matrix is a 2x2 matrix pattern spanned to ROWSxCOLUMN
 */
fun getFlowerMatrix(trance: Movement.Trance): List<List<ClockData>> {

    // 0,0
    val evenDegreeOne = when (trance.to) {
        Movement.Trance.To.SQUARE -> {
            ClockData(
                degreeOne = 90,
                degreeTwo = 180
            )
        }


        Movement.Trance.To.FLOWER -> {
            ClockData(
                degreeOne = 135,
                degreeTwo = 135
            )
        }
        Movement.Trance.To.FLY -> {
            ClockData(
                degreeOne = 45,
                degreeTwo = 225
            )
        }
        Movement.Trance.To.STAR -> {
            ClockData(
                degreeOne = -45,
                degreeTwo = 315
            )
        }
    }

    // 0,1
    val evenDegreeTwo = when (trance.to) {
        Movement.Trance.To.SQUARE -> {
            // Square
            ClockData(
                degreeOne = 180,
                degreeTwo = 270
            )
        }
        Movement.Trance.To.FLOWER -> {
            ClockData(
                degreeOne = 225,
                degreeTwo = 225
            )
        }
        Movement.Trance.To.FLY -> {
            ClockData(
                degreeOne = 135,
                degreeTwo = 315
            )
        }
        Movement.Trance.To.STAR -> {
            ClockData(
                degreeOne = 45,
                degreeTwo = 45 + 360
            )
        }
    }


    // 1,0
    val oddDegreeOne = when (trance.to) {
        Movement.Trance.To.SQUARE -> {
            // Square
            ClockData(
                degreeOne = 0,
                degreeTwo = 90
            )
        }
        Movement.Trance.To.FLOWER -> {
            ClockData(
                degreeOne = 45,
                degreeTwo = 45
            )
        }
        Movement.Trance.To.FLY -> {
            ClockData(
                degreeOne = 135,
                degreeTwo = 315
            )
        }
        Movement.Trance.To.STAR -> {
            ClockData(
                degreeOne = -135,
                degreeTwo = 225
            )
        }
    }

    // 1,1
    val oddDegreeTwo = when (trance.to) {
        Movement.Trance.To.SQUARE -> {
            // Square
            ClockData(
                degreeOne = 270,
                degreeTwo = 360
            )
        }
        Movement.Trance.To.FLOWER -> {
            ClockData(
                degreeOne = 315,
                degreeTwo = 315
            )
        }
        Movement.Trance.To.FLY -> {
            ClockData(
                degreeOne = 45,
                degreeTwo = 225
            )
        }
        Movement.Trance.To.STAR -> {
            ClockData(
                degreeOne = 135,
                degreeTwo = 135 + 360
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
