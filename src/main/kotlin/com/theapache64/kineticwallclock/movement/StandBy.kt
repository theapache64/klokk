package com.theapache64.kineticwallclock.movement

import COLUMNS
import ClockData
import Movement
import ROWS


/**
 * Creating a ROWSxCOLUMN matrix with given degree as ClockData
 */
fun getStandByMatrix(
    standBy: Movement.StandBy,
): List<List<ClockData>> {
    return mutableListOf<List<ClockData>>().apply {
        repeat(ROWS) {
            val list = mutableListOf<ClockData>()
            repeat(COLUMNS) {
                list.add(ClockData(standBy.degree, standBy.degree))
            }
            add(list)
        }
    }
}