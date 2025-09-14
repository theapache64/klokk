package com.theapache64.klokk.movement.time

import com.theapache64.klokk.model.ClockData

object SevenMatrix : DigitMatrix() {
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
            null,
            time_7(),
            time_7()
        )
    }


    override fun getRow4(): List<ClockData?> {
        return listOf(
            time_1_30(),
            time_1_30(),
            null
        )
    }


    override fun getRow5(): List<ClockData?> {
        return listOf(
            time_6(),
            time_6(),
            null,
        )
    }

    override fun getRow6(): List<ClockData?> {
        return listOf(
            time_3(),
            time_9(),
            null
        )
    }
}