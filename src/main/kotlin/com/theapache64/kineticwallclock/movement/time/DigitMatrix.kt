package com.theapache64.kineticwallclock.movement.time

import com.theapache64.kineticwallclock.model.ClockData

abstract class DigitMatrix {
    abstract fun getRow1(): List<ClockData?>
    abstract fun getRow2(): List<ClockData?>
    abstract fun getRow3(): List<ClockData?>
    abstract fun getRow4(): List<ClockData?>
    abstract fun getRow5(): List<ClockData?>
    abstract fun getRow6(): List<ClockData?>

    fun getMatrix(): List<List<ClockData?>> {
        return mutableListOf<List<ClockData?>>().apply {

            // first row |^^^|
            add(getRow1())

            // Second row
            add(getRow2())

            // Third row
            add(getRow3())

            // 4th row same as 3rd row
            add(getRow4())

            // 5th row same as 2nd button middle change
            add(getRow5())

            // last row
            add(getRow6())
        }
    }

    protected fun clockDataRightAngledBottomRight() = ClockData(degreeOne = 90f, degreeTwo = 180f)
    protected fun clockDataHorizontal() = ClockData(degreeOne = 90f, degreeTwo = 270f)
    protected fun clockDataRightAngledBottomLeft() = ClockData(degreeOne = 180f, degreeTwo = 270f)
    protected fun clockDataVertical() = ClockData(degreeOne = 0f, degreeTwo = 180f)
    protected fun clockDataRightAngledTopLeft() = ClockData(degreeOne = 0f, degreeTwo = 270f)
    protected fun clockDataRightAngledTopRight() = ClockData(degreeOne = 0f, degreeTwo = 90f)
    protected fun clockDataVerticalHalfBottom() = ClockData(degreeOne = 180f, degreeTwo = 180f)
    protected fun clockDataVerticalHalfTop() = ClockData(degreeOne = 0f, degreeTwo = 0f)

}