package com.theapache64.kineticwallclock.movement

import COLUMNS
import ROWS
import com.theapache64.kineticwallclock.model.ClockData

/**
 * Flower matrix is a 2x2 matrix pattern spanned to ROWSxCOLUMN
 */
fun getFlowerMatrix(flower: Movement.Flower): List<List<ClockData>> {

    // 0,0
    val evenDegreeOne = when (flower.state) {
        Movement.Flower.State.STAND_BY -> {
            ClockData(
                degreeOne = 90,
                degreeTwo = 180
            )
        }
        Movement.Flower.State.OPEN -> {
            ClockData(
                degreeOne = 135,
                degreeTwo = 135
            )
        }
        Movement.Flower.State.MID -> {
            ClockData(
                degreeOne = 45,
                degreeTwo = 225
            )
        }
        Movement.Flower.State.CLOSE -> {
            ClockData(
                degreeOne = 315,
                degreeTwo = 315
            )
        }
    }

    // 0,1
    val evenDegreeTwo = when (flower.state) {
        Movement.Flower.State.STAND_BY -> {
            // Square
            ClockData(
                degreeOne = 270,
                degreeTwo = 180
            )
        }
        Movement.Flower.State.OPEN -> {
            ClockData(
                degreeOne = 225,
                degreeTwo = 225
            )
        }
        Movement.Flower.State.MID -> {
            ClockData(
                degreeOne = 135,
                degreeTwo = 315
            )
        }
        Movement.Flower.State.CLOSE -> {
            ClockData(
                degreeOne = 45,
                degreeTwo = 45
            )
        }
    }


    // 1,0
    val oddDegreeOne = when (flower.state) {
        Movement.Flower.State.STAND_BY -> {
            // Square
            ClockData(
                degreeOne = 90,
                degreeTwo = 0
            )
        }
        Movement.Flower.State.OPEN -> {
            ClockData(
                degreeOne = 45,
                degreeTwo = 45
            )
        }
        Movement.Flower.State.MID -> {
            ClockData(
                degreeOne = 135,
                degreeTwo = 315
            )
        }
        Movement.Flower.State.CLOSE -> {
            ClockData(
                degreeOne = 225,
                degreeTwo = 225
            )
        }
    }

    // 1,1
    val oddDegreeTwo = when (flower.state) {
        Movement.Flower.State.STAND_BY -> {
            // Square
            ClockData(
                degreeOne = 270,
                degreeTwo = 360
            )
        }
        Movement.Flower.State.OPEN -> {
            ClockData(
                degreeOne = 315,
                degreeTwo = 315
            )
        }
        Movement.Flower.State.MID -> {
            ClockData(
                degreeOne = 225,
                degreeTwo = 45
            )
        }
        Movement.Flower.State.CLOSE -> {
            ClockData(
                degreeOne = 135,
                degreeTwo = 135
            )
        }
    }

    return mutableListOf<List<ClockData>>().apply {
        val columnRepeat = (COLUMNS / 2)
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
                repeat(remColumn) { remColumnIndex ->
                    val lastColumn = if (remColumnIndex % 2 == 0) {
                        // even
                        if (rowIndex % 2 == 0) {
                            evenDegreeOne
                        } else {
                            oddDegreeOne
                        }
                    } else {
                        // odd
                        if (rowIndex % 2 == 0) {
                            evenDegreeTwo
                        } else {
                            oddDegreeTwo
                        }
                    }
                    row.add(lastColumn)
                }
            }

            add(row)
        }

        // has rem row
        val remRow = ROWS % 2

        // Add remaining row
        if (remRow == 1) {
            add(get(0))
        }
    }
}
