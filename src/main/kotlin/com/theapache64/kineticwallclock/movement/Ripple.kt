package com.theapache64.kineticwallclock.movement

import com.theapache64.kineticwallclock.COLUMNS
import com.theapache64.kineticwallclock.model.ClockData

fun main(args: Array<String>) {

}

const val ROW_CLOCK_COUNT = 8

private val row1 = mutableListOf<ClockData>().apply {
    generateRow(
        startNeedleOneDegree = 80f,
        startNeedleTwoDegree = 160f,
        endNeedleOneDegree = 110f,
        endNeedleTwoDegree = 170f
    )
}

private val row2 = mutableListOf<ClockData>().apply {
    generateRow(
        startNeedleOneDegree = 85f,
        startNeedleTwoDegree = 150f,
        endNeedleOneDegree = 120f,
        endNeedleTwoDegree = 160f
    )
}

private val row3 = mutableListOf<ClockData>().apply {
    generateRow(
        startNeedleOneDegree = 85f,
        startNeedleTwoDegree = 130f,
        endNeedleOneDegree = 130f,
        endNeedleTwoDegree = 150f
    )
}

private val row4 = mutableListOf<ClockData>().apply {
    generateRow(
        startNeedleOneDegree = 80f,
        startNeedleTwoDegree = 120f,
        endNeedleOneDegree = 135f,
        endNeedleTwoDegree = 135f
    )
}

private val grid00 = mutableListOf<List<ClockData>>().apply {
    add(row1)
    add(row2)
    add(row3)
    add(row4)
}

private val grid01 = mutableListOf<List<ClockData>>().apply {
    add(row1.reverseAndMirrorHorizontally())
    add(row2.reverseAndMirrorHorizontally())
    add(row3.reverseAndMirrorHorizontally())
    add(row4.reverseAndMirrorHorizontally())
}

private val grid10 = mutableListOf<List<ClockData>>().apply {
    add(row4.mirrorVertically())
    add(row3.mirrorVertically())
    add(row2.mirrorVertically())
    add(row1.mirrorVertically())
}

private val grid11 = mutableListOf<List<ClockData>>().apply {
    grid10.forEach {
        add(it.toMutableList().reverseAndMirrorHorizontally())
    }
}

/**
 * Thanks to Zhuinden ;)
 */
fun getMirroredAngleFor(current: Float): Float {
    return if (current > 0 && current < 180) {
        180 - current
    } else {
        (360 - (current % 180))
    }
}

private fun MutableList<ClockData>.mirrorVertically(): List<ClockData> {
    return this.map {
        it.copy(
            degreeOne = getMirroredAngleFor(it.degreeOne),
            degreeTwo = getMirroredAngleFor(it.degreeTwo)
        )
    }
}


private fun MutableList<ClockData>.reverseAndMirrorHorizontally(): List<ClockData> {
    return reversed().map {
        val degreeOne = 360 - it.degreeOne
        val degreeTwo = 360 - it.degreeTwo
        it.copy(
            degreeOne = degreeOne,
            degreeTwo = degreeTwo
        )
    }
}


private fun MutableList<ClockData>.generateRow(
    startNeedleOneDegree: Float,
    startNeedleTwoDegree: Float,
    endNeedleOneDegree: Float,
    endNeedleTwoDegree: Float,
) {
    val intervalOne = (endNeedleOneDegree - startNeedleOneDegree) / ROW_CLOCK_COUNT
    val intervalTwo = (endNeedleTwoDegree - startNeedleTwoDegree) / ROW_CLOCK_COUNT
    var needleOne = startNeedleOneDegree
    var needleTwo = startNeedleTwoDegree

    repeat(ROW_CLOCK_COUNT) {
        add(
            ClockData(
                degreeOne = needleOne,
                degreeTwo = needleTwo
            )
        )

        // Increment x and y
        needleOne += intervalOne
        needleTwo += intervalTwo
    }
}

fun getRippleMatrix(ripple: Movement.Ripple): List<List<ClockData>> {
    return mutableListOf<List<ClockData>>().apply {

        when (ripple.to) {
            Movement.Ripple.To.START -> {
                repeat(4) { rowIndex ->
                    val list = mergeHorizontally(grid00, grid01, rowIndex)
                    add(list)
                }

                repeat(4) { i ->
                    val list = mergeHorizontally(grid10, grid11, i)
                    add(list)
                }
            }
            Movement.Ripple.To.END -> {

                repeat(4) { rowIndex ->
                    val list = mergeHorizontallyAndFlip(grid00, grid01, rowIndex)
                    add(list)
                }

                repeat(4) { i ->
                    val list = mergeHorizontallyAndFlip(grid10, grid11, i)
                    add(list)
                }
            }
        }
    }
}

fun mergeHorizontally(
    grid1: MutableList<List<ClockData>>,
    grid2: MutableList<List<ClockData>>,
    rowIndex: Int,
): List<ClockData> {
    return mutableListOf<ClockData>().apply {
        val row1 = grid1[rowIndex]
        val row2 = grid2[rowIndex]
        addAll(row1)
        addAll(row2)
    }.subList(0, COLUMNS)
}

fun mergeHorizontallyAndFlip(
    grid1: MutableList<List<ClockData>>,
    grid2: MutableList<List<ClockData>>,
    rowIndex: Int,
): List<ClockData> {
    return mutableListOf<ClockData>().apply {
        val row1 = grid1[rowIndex]
        val row2 = grid2[rowIndex]
        addAll(row1)
        addAll(row2)
    }.subList(0, COLUMNS).map {

        it.copy(
            degreeOne = it.degreeOne + 360,
            degreeTwo = it.degreeTwo - 360,
        )
    }
}
