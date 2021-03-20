package com.theapache64.kineticwallclock.movement

sealed class Movement(
    val durationInMillis: Int = 2000,
) {
    data class StandBy(
        val degree: Int = 270,
    ) : Movement()

    data class Flower(
        val state: State = State.STAND_BY,
    ) : Movement(
        durationInMillis = 4000
    ) {
        enum class State {
            STAND_BY, OPEN, MID, CLOSE
        }
    }
}