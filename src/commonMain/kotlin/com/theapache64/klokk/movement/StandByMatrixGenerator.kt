package com.theapache64.klokk.movement

import com.theapache64.klokk.COLUMNS
import com.theapache64.klokk.ROWS
import com.theapache64.klokk.model.ClockData
import com.theapache64.klokk.movement.core.MatrixGenerator
import com.theapache64.klokk.movement.core.Movement

/**
 * All clocks will be in sleep position
 */

const val DEFAULT_STAND_BY_DEGREE = 225f

val globalStandByClockData by lazy {
    ClockData(
        degreeOne = DEFAULT_STAND_BY_DEGREE,
        degreeTwo = DEFAULT_STAND_BY_DEGREE
    )
}

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
                        list.add(globalStandByClockData)
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