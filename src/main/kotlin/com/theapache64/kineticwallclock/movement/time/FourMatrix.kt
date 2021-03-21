package com.theapache64.kineticwallclock.movement.time

import com.theapache64.kineticwallclock.model.ClockData

object FourMatrix : DigitMatrix() {
    override fun getRow1(): List<ClockData?> {
        return listOf(
            clockDataRightAngledBottomRight(),
            clockDataRightAngledBottomLeft(),
            clockDataRightAngledBottomLeft()
        )
    }

    override fun getRow2(): List<ClockData?> {
        return listOf(
            clockDataVertical(),
            clockDataVertical(),
            clockDataVertical()
        )
    }

    override fun getRow3(): List<ClockData?> {
        return listOf(
            clockDataVertical(),
            clockDataRightAngledTopRight(),
            clockDataVertical()
        )
    }

    override fun getRow4(): List<ClockData?> {
        return listOf(
            clockDataRightAngledTopRight(),
            clockDataRightAngledBottomLeft(),
            clockDataVertical()
        )
    }

    override fun getRow5(): List<ClockData?> {
        return listOf(
            null,
            clockDataVertical(),
            clockDataVertical()
        )
    }

    override fun getRow6(): List<ClockData?> {
        return listOf(
            null,
            clockDataRightAngledTopRight(),
            clockDataRightAngledTopLeft()
        )
    }
}