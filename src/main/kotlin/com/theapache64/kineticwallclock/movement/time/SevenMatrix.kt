package com.theapache64.kineticwallclock.movement.time

import com.theapache64.kineticwallclock.model.ClockData

object SevenMatrix : DigitMatrix() {
    override fun getRow1(): List<ClockData?> {
        return listOf(
            clockDataRightAngledBottomRight(),
            clockDataHorizontal(),
            clockDataRightAngledBottomLeft()
        )
    }

    override fun getRow2(): List<ClockData?> {
        return listOf(
            clockDataRightAngledTopRight(),
            clockDataRightAngledBottomLeft(),
            clockDataVertical()
        )
    }

    override fun getRow3(): List<ClockData?> {
        return listOf(
            null,
            clockDataLeftCurve(),
            clockDataLeftCurve()
        )
    }

    private fun clockDataLeftCurve(): ClockData {
        return ClockData(
            degreeOne = 0f,
            degreeTwo = 225f
        )
    }

    override fun getRow4(): List<ClockData?> {
        return listOf(
            clockDataRightCurve(),
            clockDataRightCurve(),
            null
        )
    }

    private fun clockDataRightCurve(): ClockData {
        return ClockData(
            degreeOne = 45f,
            degreeTwo = 180f
        )
    }

    override fun getRow5(): List<ClockData?> {
        return listOf(
            clockDataVertical(),
            clockDataVertical(),
            null,
        )
    }

    override fun getRow6(): List<ClockData?> {
        return listOf(
            clockDataRightAngledTopRight(),
            clockDataRightAngledTopLeft(),
            null
        )
    }
}