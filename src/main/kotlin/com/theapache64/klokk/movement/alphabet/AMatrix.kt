package com.theapache64.klokk.movement.alphabet

import com.theapache64.klokk.model.ClockData

object AMatrix : AlphabetMatrix() {
    override fun getRow1(): List<ClockData?> {
        return listOf(
            null,
            clockDataBottomBoomerang(),
            null,
        )
    }

    override fun getRow2(): List<ClockData?> {
        return listOf(
            clockDataTopRightCurve(),
            clockDataHorizontal(),
            clockDataTopLeftCurve()
        )
    }

    override fun getRow3(): List<ClockData?> {
        return listOf(
            clockDataVertical(),
            null,
            clockDataVertical()
        )
    }
}