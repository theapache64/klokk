package com.theapache64.klokk.movement.core

import com.theapache64.klokk.model.ClockData

abstract class BaseMatrix {

    abstract fun getMatrix(): List<List<ClockData?>>

    protected fun clockDataRightAngledBottomRight() = ClockData(degreeOne = 90f, degreeTwo = 180f)
    protected fun clockDataHorizontal() = ClockData(degreeOne = 90f, degreeTwo = 270f)
    protected fun clockDataRightAngledBottomLeft() = ClockData(degreeOne = 180f, degreeTwo = 270f)
    protected fun clockDataVertical() = ClockData(degreeOne = 0f, degreeTwo = 180f)
    protected fun clockDataRightAngledTopLeft() = ClockData(degreeOne = 0f, degreeTwo = 270f)
    protected fun clockDataRightAngledTopRight() = ClockData(degreeOne = 0f, degreeTwo = 90f)
    protected fun clockDataVerticalBottomHalf() = ClockData(degreeOne = 180f, degreeTwo = 180f)
    protected fun clockDataVerticalTopHalf() = ClockData(degreeOne = 0f, degreeTwo = 0f)
    protected fun clockDataBottomLeftCurve() = ClockData(degreeOne = 0f, degreeTwo = 225f)
    protected fun clockDataTopRightCurve() = ClockData(degreeOne = 45f, degreeTwo = 180f)
    protected fun clockDataTopLeftCurve() = ClockData(degreeOne = 180f, degreeTwo = 315f)
    protected fun clockDataBottomBoomerang() = ClockData(degreeOne = 135f, degreeTwo = 225f)
}