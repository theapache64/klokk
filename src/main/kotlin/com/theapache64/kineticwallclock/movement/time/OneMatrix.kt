package com.theapache64.kineticwallclock.movement.time

import com.theapache64.kineticwallclock.model.ClockData

object OneMatrix : DigitMatrix() {

    override fun getRow1(): List<ClockData> {
        return listOf(
            ClockData(degreeOne = 90f, degreeTwo = 180f),
            ClockData(degreeOne = 90f, degreeTwo = 270f),
            ClockData(degreeOne = 180f, degreeTwo = 270f),
        )
    }

    override fun getRow2(): List<ClockData> {
        return listOf(
            ClockData(degreeOne = 0f, degreeTwo = 90f),
            ClockData(degreeOne = 180f, degreeTwo = 270f),
            ClockData(degreeOne = 0f, degreeTwo = 180f),
        )
    }

    override fun getRow3(): List<ClockData?> {
        return listOf(
            null,
            ClockData(degreeOne = 0f, degreeTwo = 180f),
            ClockData(degreeOne = 0f, degreeTwo = 180f),
        )
    }

    override fun getRow4(): List<ClockData?> {
        return getRow3()
    }

    override fun getRow5(): List<ClockData?> {
        return getRow4()
    }

    override fun getRow6(): List<ClockData?> {
        return listOf(
            null,
            ClockData(degreeOne = 0f, degreeTwo = 90f),
            ClockData(degreeOne = 0f, degreeTwo = 270f),
        )
    }


}