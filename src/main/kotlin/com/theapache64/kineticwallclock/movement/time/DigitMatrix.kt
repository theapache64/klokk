package com.theapache64.kineticwallclock.movement.time

import com.theapache64.kineticwallclock.model.ClockData

abstract class DigitMatrix {
    abstract fun getRow1(): List<ClockData>
    abstract fun getRow2(): List<ClockData>
    abstract fun getRow3(): List<ClockData>
    abstract fun getRow4(): List<ClockData>
    abstract fun getRow5(): List<ClockData>
    abstract fun getRow6(): List<ClockData>

    fun getMatrix(): List<List<ClockData>> {
        return mutableListOf<List<ClockData>>().apply {

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

}