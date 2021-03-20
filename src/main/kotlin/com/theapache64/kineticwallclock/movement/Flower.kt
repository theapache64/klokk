package com.theapache64.kineticwallclock.movement

import com.theapache64.kineticwallclock.COLUMNS
import com.theapache64.kineticwallclock.ROWS
import com.theapache64.kineticwallclock.model.ClockData

/**
 * Flower matrix is a 2x2 matrix pattern spanned to ROWSxCOLUMN
 */
fun getFlowerMatrix(flower: Movement.Flower): List<List<ClockData>> {

    // 0,0
    val evenDegreeOne = when (flower.to) {
        Movement.Flower.To.SQUARE -> {
            ClockData(
                degreeOne = 90 + 360,
                degreeTwo = 180
            )
        }


        Movement.Flower.To.FLOWER -> {
            ClockData(
                degreeOne = 135,
                degreeTwo = 135
            )
        }
        Movement.Flower.To.MID -> {
            ClockData(
                degreeOne = 45,
                degreeTwo = 225
            )
        }
        Movement.Flower.To.STAR -> {
            ClockData(
                degreeOne = -45,
                degreeTwo = 315
            )
        }
    }

    // 0,1
    val evenDegreeTwo = when (flower.to) {
        Movement.Flower.To.SQUARE -> {
            // Square
            ClockData(
                degreeOne = 180 + 360,
                degreeTwo = 270
            )
        }
        Movement.Flower.To.FLOWER -> {
            ClockData(
                degreeOne = 225,
                degreeTwo = 225
            )
        }
        Movement.Flower.To.MID -> {
            ClockData(
                degreeOne = 135,
                degreeTwo = 315
            )
        }
        Movement.Flower.To.STAR -> {
            ClockData(
                degreeOne = 45,
                degreeTwo = 45 + 360
            )
        }
    }


    // 1,0
    val oddDegreeOne = when (flower.to) {
        Movement.Flower.To.SQUARE -> {
            // Square
            ClockData(
                degreeOne = 0 + 360,
                degreeTwo = 90
            )
        }
        Movement.Flower.To.FLOWER -> {
            ClockData(
                degreeOne = 45,
                degreeTwo = 45
            )
        }
        Movement.Flower.To.MID -> {
            ClockData(
                degreeOne = 135,
                degreeTwo = 315
            )
        }
        Movement.Flower.To.STAR -> {
            ClockData(
                degreeOne = -135,
                degreeTwo = 225
            )
        }
    }

    // 1,1
    val oddDegreeTwo = when (flower.to) {
        Movement.Flower.To.SQUARE -> {
            // Square
            ClockData(
                degreeOne = 270 + 360,
                degreeTwo = 360
            )
        }
        Movement.Flower.To.FLOWER -> {
            ClockData(
                degreeOne = 315,
                degreeTwo = 315
            )
        }
        Movement.Flower.To.MID -> {
            ClockData(
                degreeOne = 45,
                degreeTwo = 225
            )
        }
        Movement.Flower.To.STAR -> {
            ClockData(
                degreeOne = 135,
                degreeTwo = 135 + 360
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
