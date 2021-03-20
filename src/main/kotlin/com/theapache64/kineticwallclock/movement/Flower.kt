package com.theapache64.kineticwallclock.movement

import COLUMNS
import com.theapache64.kineticwallclock.model.ClockData
import ROWS

/**
 * Flower matrix is a 2x2 matrix pattern spanned to ROWSxCOLUMN
 */
fun getFlowerMatrix(flower: Movement.Flower): List<List<ClockData>> {

    // 0,0
    val evenDegreeOne = if (flower.isOpen) {
        ClockData(
            degreeOne = 90,
            degreeTwo = 180
        )
    } else {
        ClockData(
            degreeOne = 180,
            degreeTwo = 90
        )
    }

    // 0,1
    val evenDegreeTwo = if (flower.isOpen) {
        ClockData(
            degreeOne = 270,
            degreeTwo = 180
        )
    } else {
        ClockData(
            degreeOne = 180,
            degreeTwo = 270
        )
    }


    // 1,0
    val oddDegreeOne = if (flower.isOpen) {
        // Open flower
        ClockData(
            degreeOne = 90,
            degreeTwo = 0
        )
    } else {
        // Closed flower
        ClockData(
            degreeOne = 0,
            degreeTwo = 90
        )
    }

    // 1,1
    val oddDegreeTwo = if (flower.isOpen) {
        ClockData(
            degreeOne = 270,
            degreeTwo = 360
        )
    } else {
        ClockData(
            degreeOne = 360,
            degreeTwo = 270
        )
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
