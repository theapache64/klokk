package com.theapache64.klokk.movement.time

import com.theapache64.klokk.model.ClockData

object OneMatrix : DigitMatrix() {

    override fun getRow1(): List<ClockData> {
        return listOf(
            clockDataRightAngledBottomRight(),
            clockDataHorizontal(),
            clockDataRightAngledBottomLeft(),
        )
    }

    override fun getRow2(): List<ClockData> {
        return listOf(
            clockDataRightAngledTopRight(),
            clockDataRightAngledBottomLeft(),
            clockDataVertical(),
        )
    }


    override fun getRow3(): List<ClockData?> {
        return listOf(
            null,
            clockDataVertical(),
            clockDataVertical(),
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
            clockDataRightAngledTopRight(),
            clockDataRightAngledTopLeft(),
        )
    }



}