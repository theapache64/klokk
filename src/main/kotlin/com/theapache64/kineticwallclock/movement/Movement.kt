package com.theapache64.kineticwallclock.movement

sealed class Movement(
    val durationInMillis: Int = 2000,
) {
    data class StandBy(
        val degree: Int = 270,
    ) : Movement()

    data class Flower(
        val isOpen: Boolean = true,
    ) : Movement(
        durationInMillis = 4000
    )
}