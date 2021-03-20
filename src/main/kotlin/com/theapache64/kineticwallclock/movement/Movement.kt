package com.theapache64.kineticwallclock.movement

import com.theapache64.kineticwallclock.COLUMNS
import com.theapache64.kineticwallclock.ROWS
import com.theapache64.kineticwallclock.model.ClockData

/**
 * To hold all set of animations
 */
sealed class Movement(
    val durationInMillis: Int,
) {
    abstract fun generateMatrix(): List<List<ClockData>>

    /**
     * To move clocks to stand by position
     */
    data class StandBy(
        val degree: Int = DEFAULT_STAND_BY_DEGREE,
    ) : Movement(durationInMillis = 4000) {
        companion object {
            const val DEFAULT_STAND_BY_DEGREE = 315
        }

        override fun generateMatrix(): List<List<ClockData>> {
            return verifyIntegrityAndReturn(getStandByMatrix(this))
        }
    }

    /**
     * To play trance looking animations
     */
    data class Trance(
        val to: To = To.SQUARE,
    ) : Movement(
        durationInMillis = 4000
    ) {
        enum class To {
            SQUARE, FLOWER, FLY, STAR
        }

        override fun generateMatrix(): List<List<ClockData>> {
            return verifyIntegrityAndReturn(getFlowerMatrix(this))
        }
    }


    /**
     * To check if the returned matrix match the clock matrix
     */
    fun verifyIntegrityAndReturn(degreeMatrix: List<List<ClockData>>): List<List<ClockData>> {
        for (matrix in degreeMatrix) {
            require(matrix.size == COLUMNS) { "Column size should be $COLUMNS but found ${matrix.size}" }
        }
        require(degreeMatrix.size == ROWS) { "Row size should be $ROWS but found ${degreeMatrix.size}" }
        return degreeMatrix
    }
}