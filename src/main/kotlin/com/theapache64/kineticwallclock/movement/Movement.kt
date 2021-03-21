package com.theapache64.kineticwallclock.movement

import com.theapache64.kineticwallclock.COLUMNS
import com.theapache64.kineticwallclock.ROWS
import com.theapache64.kineticwallclock.model.ClockData
import java.util.*

/**
 * Dynamic animation can't be supported until this fixed -> https://issuetracker.google.com/issues/183220315
 */
const val DEFAULT_ANIMATION_DURATION = 4000

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
        val degree: Float = DEFAULT_STAND_BY_DEGREE,
    ) : Movement(durationInMillis = DEFAULT_ANIMATION_DURATION) {
        companion object {
            const val DEFAULT_STAND_BY_DEGREE = 315f
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
        durationInMillis = DEFAULT_ANIMATION_DURATION
    ) {
        enum class To {
            SQUARE, FLOWER, FLY, STAR
        }

        override fun generateMatrix(): List<List<ClockData>> {
            return verifyIntegrityAndReturn(getTranceMatrix(this))
        }
    }

    /**
     * Ripple effect
     */
    data class Ripple(
        val to: To = To.START,
    ) : Movement(durationInMillis = DEFAULT_ANIMATION_DURATION) {

        override fun generateMatrix(): List<List<ClockData>> {
            return verifyIntegrityAndReturn(getRippleMatrix(this))
        }

        enum class To {
            START, END
        }
    }

    data class Time(
        val date : Date
    ) : Movement(durationInMillis = DEFAULT_ANIMATION_DURATION) {
        override fun generateMatrix(): List<List<ClockData>> {
            return verifyIntegrityAndReturn(getTimeMatrix(this))
        }
    }

    /**
     * To check if the returned matrix match the clock matrix
     */
    protected fun verifyIntegrityAndReturn(degreeMatrix: List<List<ClockData>>): List<List<ClockData>> {
        for (matrix in degreeMatrix) {
            require(matrix.size == COLUMNS) { "Column size should be $COLUMNS but found ${matrix.size}" }
        }
        require(degreeMatrix.size == ROWS) { "Row size should be $ROWS but found ${degreeMatrix.size}" }
        return degreeMatrix
    }
}