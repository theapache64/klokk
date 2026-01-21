package com.theapache64.klokk.movement.time

import com.theapache64.klokk.model.ClockData

object EightMatrix : DigitMatrix() {
    override fun getRow1(): List<ClockData?> {
        return listOf(
            time_3_30(),
            time_3_45(),
            time_6_45()
        )
    }

    override fun getRow2(): List<ClockData?> {
        return listOf(
            time_6(),
            time_6_30(),
            time_6()
        )
    }

    override fun getRow3(): List<ClockData?> {
        return listOf(
            ClockData(degreeOne = 0f, degreeTwo = 135f),
            time_12(),
            ClockData(degreeOne = 0f, degreeTwo = 225f),
        )
    }

    override fun getRow4(): List<ClockData?> {
        return listOf(
            time_1_30(),
            time_6_30(),
            ClockData(degreeOne = 180f, degreeTwo = 315f)
        )
    }

    override fun getRow5(): List<ClockData?> {
        return listOf(
            time_6(),
            time_12(),
            time_6()
        )
    }

    override fun getRow6(): List<ClockData?> {
        return listOf(
            time_3(),
            time_3_45(),
            time_9()
        )
    }

}