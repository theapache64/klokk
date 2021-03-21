package com.theapache64.kineticwallclock.movement

import com.theapache64.kineticwallclock.COLUMNS
import com.theapache64.kineticwallclock.ROWS
import com.theapache64.kineticwallclock.model.ClockData
import com.theapache64.kineticwallclock.movement.core.MatrixGenerator
import com.theapache64.kineticwallclock.movement.core.Movement

class StandByMatrixGenerator(data: Movement.StandBy) : MatrixGenerator<Movement.StandBy>(data) {
    companion object {

        /**
         * Creating a ROWSxCOLUMN matrix with given degree as ClockData
         */
        private fun getStandByMatrix(
            standBy: Movement.StandBy,
        ): MutableList<MutableList<ClockData>> {
            return mutableListOf<MutableList<ClockData>>().apply {
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
    }

    override fun generateMatrix(): List<List<ClockData>> {
        return getStandByMatrix(data)
    }

}