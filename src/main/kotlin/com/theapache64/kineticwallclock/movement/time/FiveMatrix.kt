package com.theapache64.kineticwallclock.movement.time

import com.theapache64.kineticwallclock.model.ClockData

object FiveMatrix : DigitMatrix() {
    
    override fun getRow1(): List<ClockData?> {
        return listOf(
            clockDataRightAngledBottomRight(),
            clockDataHorizontal(),
            clockDataRightAngledBottomLeft()
        )
    }

    override fun getRow2(): List<ClockData?> {
        return listOf(
            clockDataVertical(),
            clockDataRightAngledBottomRight(),
            clockDataRightAngledTopLeft()
        )
    }

    override fun getRow3(): List<ClockData?> {
        return listOf(
            clockDataVertical(),
            clockDataRightAngledTopRight(),
            clockDataRightAngledBottomLeft()
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
            clockDataRightAngledBottomRight(),
            clockDataRightAngledTopLeft(),
            clockDataVertical()
        )
    }

    override fun getRow6(): List<ClockData?> {
        return listOf(
            clockDataRightAngledTopRight(),
            clockDataHorizontal(),
            clockDataRightAngledTopLeft()
        )
    }
}