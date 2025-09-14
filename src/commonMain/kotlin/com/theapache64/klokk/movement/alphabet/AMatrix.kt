package com.theapache64.klokk.movement.alphabet

import com.theapache64.klokk.model.ClockData

object AMatrix : AlphabetMatrix() {
    override fun getRow1(): List<ClockData?> {
        return listOf(
            null,
            time_4_40(),
            null,
        )
    }

    override fun getRow2(): List<ClockData?> {
        return listOf(
            time_1_30(),
            time_3_45(),
            time_6_50()
        )
    }

    override fun getRow3(): List<ClockData?> {
        return listOf(
            time_6(),
            null,
            time_6()
        )
    }
}