package com.theapache64.klokk.movement.time

import com.theapache64.klokk.model.ClockData

object TwoMatrix : DigitMatrix() {

    override fun getRow1(): List<ClockData?> {
        return listOf(
            time_3_30(),
            time_3_45(),
            time_6_45()
        )
    }

    override fun getRow2(): List<ClockData?> {
        return listOf(
            time_3(),
            time_6_45(),
            time_6()
        )
    }

    override fun getRow3(): List<ClockData?> {
        return listOf(
            time_3_30(),
            time_9(),
            time_6()
        )
    }

    override fun getRow4(): List<ClockData?> {
        return listOf(
            time_6(),
            time_3_30(),
            time_9()
        )
    }

    override fun getRow5(): List<ClockData?> {
        return listOf(
            time_6(),
            time_3(),
            time_6_45()
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