package com.theapache64.klokk.movement.time

import com.theapache64.klokk.model.ClockData

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
            clockDataBottomLeftCurve(),
            clockDataBottomLeftCurve()
        )
    }


    override fun getRow4(): List<ClockData?> {
        return listOf(
            clockDataTopRightCurve(),
            clockDataTopRightCurve(),
            null
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