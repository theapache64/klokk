package com.theapache64.klokk.movement.core

import com.theapache64.klokk.movement.MosaicMatrixGenerator
import com.theapache64.klokk.movement.StandByMatrixGenerator
import com.theapache64.klokk.movement.TranceMatrixGenerator
import com.theapache64.klokk.movement.alphabet.TextMatrixGenerator
import com.theapache64.klokk.movement.ripple.RippleMatrixGenerator
import com.theapache64.klokk.movement.snake.WaveMatrixGenerator
import com.theapache64.klokk.movement.time.TimeMatrixGenerator
import java.util.*

/**
 * Dynamic animation can't be supported until this fixed -> https://issuetracker.google.com/issues/183220315
 */
const val DEFAULT_ANIMATION_DURATION = 4000


sealed class Movement(
    val durationInMillis: Int,
) {

    abstract fun getMatrixGenerator(): MatrixGenerator<Movement>

    /**
     * To move clocks to stand by position
     */
    object StandBy : Movement(durationInMillis = DEFAULT_ANIMATION_DURATION) {
        override fun getMatrixGenerator(): MatrixGenerator<Movement> {
            return StandByMatrixGenerator(this)
        }

        override fun toString(): String {
            return "StandBy"
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
            START, END, TIME_TABLE
        }
    }

    /**
     * To show time
     */
    data class Time(
        val date: Date = Date(),
    ) : Movement(durationInMillis = DEFAULT_ANIMATION_DURATION) {
        override fun getMatrixGenerator(): MatrixGenerator<Time> {
            return TimeMatrixGenerator(this)
        }
    }

    /**
     * TODO :WIP
     */
    data class Text(
        val text: String,
    ) : Movement(durationInMillis = DEFAULT_ANIMATION_DURATION) {
        override fun getMatrixGenerator(): MatrixGenerator<Movement> {
            return TextMatrixGenerator(this)
        }

        enum class Alignment {
            CENTER, LEFT
        }
    }

    data class Wave(
        val state: State,
    ) : Movement(durationInMillis = DEFAULT_ANIMATION_DURATION) {
        override fun getMatrixGenerator(): MatrixGenerator<Movement> {
            return WaveMatrixGenerator(this)
        }

        enum class State {
            START, END
        }
    }

    /**
     * Mosaic patterns - complex mesmerizing artistic arrangements
     */
    data class Mosaic(
        val to: To = To.MONA_LISA,
    ) : Movement(durationInMillis = DEFAULT_ANIMATION_DURATION) {
        enum class To {
            // Original patterns
            MONA_LISA,        // Portrait face pattern inspired by the famous painting
            MANDALA,          // Complex 8-fold radial symmetry meditation pattern
            GALAXY,           // Spiral galaxy with logarithmic arms like Milky Way
            HYPNOTIC_RINGS,   // Concentric rings creating rotation illusion
            WAVE_INTERFERENCE,// Two wave sources creating interference pattern
            INFINITY,         // Mathematical lemniscate (figure 8) pattern
            PEACOCK,          // Peacock feather fan with eye-spot patterns
            VORTEX,           // Deep whirlpool with sense of depth
            FIBONACCI,        // Golden ratio spiral (sunflower/shell pattern)
            OPTICAL_DEPTH,    // 3D sphere illusion with lighting
            // New hypnotic patterns
            KALEIDOSCOPE,     // 12-fold symmetry like looking through a kaleidoscope
            AURORA,           // Northern lights wave effect
            TORNADO,          // Funnel-shaped rotating vortex
            HEARTBEAT,        // Pulsing heart shape
            BLACK_HOLE,       // Event horizon with gravitational lensing
            ELECTRIC,         // Lightning/electric field lines between charges
            OCEAN_WAVES,      // Rolling ocean waves toward shore
            BLOOM,            // Flower petals unfurling from center
            TESSERACT,        // 4D hypercube projection illusion
            STARBURST         // Exploding star with dramatic rays
        }

        override fun getMatrixGenerator(): MatrixGenerator<Movement> {
            return MosaicMatrixGenerator(this)
        }
    }
}