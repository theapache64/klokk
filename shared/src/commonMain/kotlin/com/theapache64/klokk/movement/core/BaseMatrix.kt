package com.theapache64.klokk.movement.core

import com.theapache64.klokk.model.ClockData

abstract class BaseMatrix {

    abstract fun getMatrix(): List<List<ClockData?>>

    protected fun time_3_30() = ClockData(degreeOne = 90f, degreeTwo = 180f)
    protected fun time_3_45() = ClockData(degreeOne = 90f, degreeTwo = 270f)
    protected fun time_6_45() = ClockData(degreeOne = 180f, degreeTwo = 270f)
    protected fun time_6() = ClockData(degreeOne = 0f, degreeTwo = 180f)
    protected fun time_9() = ClockData(degreeOne = 0f, degreeTwo = 270f)
    protected fun time_3() = ClockData(degreeOne = 0f, degreeTwo = 90f)
    protected fun time_6_30() = ClockData(degreeOne = 180f, degreeTwo = 180f)
    protected fun time_12() = ClockData(degreeOne = 0f, degreeTwo = 0f)
    protected fun time_7() = ClockData(degreeOne = 0f, degreeTwo = 225f)
    protected fun time_1_30() = ClockData(degreeOne = 45f, degreeTwo = 180f)
    protected fun time_6_50() = ClockData(degreeOne = 180f, degreeTwo = 315f)
    protected fun time_4_40() = ClockData(degreeOne = 135f, degreeTwo = 225f)
}

fun main(args: Array<String>) {

}