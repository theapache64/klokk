package com.theapache64.kineticwallclock.movement.core

import com.theapache64.kineticwallclock.movement.RippleMatrixGenerator
import com.theapache64.kineticwallclock.movement.StandByMatrixGenerator
import com.theapache64.kineticwallclock.movement.TranceMatrixGenerator
import com.theapache64.kineticwallclock.movement.time.TimeMatrixGenerator
import java.util.*

/**
 * Dynamic animation can't be supported until this fixed -> https://issuetracker.google.com/issues/183220315
 */
const val DEFAULT_ANIMATION_DURATION = 2000


sealed class Movement(
    val durationInMillis: Int,
) {

    abstract fun getMatrixGenerator(): MatrixGenerator<Movement>

    /**
     * To move clocks to stand by position
     */
    data class StandBy(
        val degree: Float = DEFAULT_STAND_BY_DEGREE,
    ) : Movement(durationInMillis = DEFAULT_ANIMATION_DURATION) {
        companion object {
            const val DEFAULT_STAND_BY_DEGREE = 225f
        }

        override fun getMatrixGenerator(): MatrixGenerator<Movement> {
            return StandByMatrixGenerator(this)
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

        override fun getMatrixGenerator(): MatrixGenerator<Movement> {
            return TranceMatrixGenerator(this)
        }
    }

    /**
     * Ripple effect
     */
    data class Ripple(
        val to: To = To.START,
    ) : Movement(durationInMillis = DEFAULT_ANIMATION_DURATION) {

        override fun getMatrixGenerator(): MatrixGenerator<Movement> {
            return RippleMatrixGenerator(this)
        }

        enum class To {
            START, END
        }
    }

    data class Time(
        val date: Date = Date(),
    ) : Movement(durationInMillis = DEFAULT_ANIMATION_DURATION) {
        override fun getMatrixGenerator(): MatrixGenerator<Time> {
            return TimeMatrixGenerator(this)
        }
    }


}