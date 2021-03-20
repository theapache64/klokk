package com.theapache64.kineticwallclock.movement

sealed class Movement(
    val durationInMillis: Int,
) {
    data class StandBy(
        val degree: Int = 270,
    ) : Movement(durationInMillis = 2000)

    data class Flower(
        val to: To = To.SQUARE,
    ) : Movement(
        durationInMillis = 2000 // TODO : Change to 4000
    ) {
        enum class To {
            SQUARE, FLOWER, MID, STAR
        }
    }
}