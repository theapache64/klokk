package com.theapache64.klokk.movement.alphabet

import com.theapache64.klokk.model.ClockData
import com.theapache64.klokk.movement.core.BaseMatrix

abstract class AlphabetMatrix : BaseMatrix() {
    abstract fun getRow1(): List<ClockData?>
    abstract fun getRow2(): List<ClockData?>
    abstract fun getRow3(): List<ClockData?>

    override fun getMatrix(): List<List<ClockData?>> {
        return listOf(
            getRow1(),
            getRow2(),
            getRow3(),
        )
    }
}