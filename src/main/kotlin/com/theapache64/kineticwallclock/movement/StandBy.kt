package com.theapache64.kineticwallclock.movement

import com.theapache64.kineticwallclock.COLUMNS
import com.theapache64.kineticwallclock.ROWS
import com.theapache64.kineticwallclock.model.ClockData


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
                list.add(ClockData(
                    degreeOne = standBy.degree,
                    degreeTwo = standBy.degree
                ))
            }
            add(list)
        }
    }
}