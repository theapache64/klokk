package com.theapache64.kineticwallclock.movement.time

import com.theapache64.kineticwallclock.model.ClockData

object EightMatrix : DigitMatrix() {
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
            clockDataVerticalBottomHalf(),
            clockDataVertical()
        )
    }

    override fun getRow3(): List<ClockData?> {
        return listOf(
            ClockData(degreeOne = 0f, degreeTwo = 135f),
            clockDataVerticalTopHalf(),
            ClockData(degreeOne = 0f, degreeTwo = 225f),
        )
    }

    override fun getRow4(): List<ClockData?> {
        return listOf(
            clockDataRightCurve(),
            clockDataVerticalBottomHalf(),
            ClockData(degreeOne = 180f, degreeTwo = 315f)
        )
    }

    override fun getRow5(): List<ClockData?> {
        return listOf(
            clockDataVertical(),
            clockDataVerticalTopHalf(),
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