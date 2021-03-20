package com.theapache64.kineticwallclock.movement

import COLUMNS
import com.theapache64.kineticwallclock.model.ClockData
import ROWS


/**
 * Creating a ROWSxCOLUMN matrix with given degree as com.theapache64.kineticwallclock.models.ClockData
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