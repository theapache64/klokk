package com.theapache64.klokk.movement

import com.theapache64.klokk.COLUMNS
import com.theapache64.klokk.ROWS
import com.theapache64.klokk.model.ClockData
import com.theapache64.klokk.movement.core.MatrixGenerator
import com.theapache64.klokk.movement.core.Movement
import kotlin.math.ceil

/**
 * A ripple matrix with 4x4 mirrored-flipped-matrix
 */
class RippleMatrixGenerator(data: Movement.Ripple) : MatrixGenerator<Movement.Ripple>(data) {
    companion object {
        private const val ROW_CLOCK_COUNT = ROWS / 2
        private val COLUMN_CLOCK_COUNT = ceil(COLUMNS / 2.toDouble()).toInt()

        private val rows = mutableListOf<List<ClockData>>().apply {
            val startOne = 45f
            val startTwo = 135f // the [0,0]

            val endOne = 135f
            val endTwo = 135f // [last,last]

            repeat(ROW_CLOCK_COUNT) {
                val row = mutableListOf<ClockData>().apply {
                    generateRow(
                        startNeedleOneDegree = startOne,
                        startNeedleTwoDegree = startTwo,
                        endNeedleOneDegree = endOne,
                        endNeedleTwoDegree = endTwo,
                    )
                }

                add(row)
            }
        }

        private val timeTableRows = mutableListOf<List<ClockData>>().apply {
            val startOne = 0f
            val startTwo = 0f // the [0,0]

            val endOne = 360f
            val endTwo = 360f // [last,last]

            repeat(ROW_CLOCK_COUNT) {
                val row = mutableListOf<ClockData>().apply {
                    generateRow(
                        startNeedleOneDegree = startOne,
                        startNeedleTwoDegree = startTwo,
                        endNeedleOneDegree = endOne,
                        endNeedleTwoDegree = endTwo,
                    )
                }

                add(row)
            }
        }

        private val grid00 = rows
        private val grid01 = rows.map { it.reverseAndMirrorHorizontally() }.toMutableList()
        private val grid10 = rows.reversed().map { it.mirrorVertically() }.toMutableList()
        private val grid11 = grid10.map { it.reverseAndMirrorHorizontally() }.toMutableList()

        private val timeTableGrid00 = timeTableRows
        private val timeTableGrid01 = timeTableRows.map { it.reverseAndMirrorHorizontally() }.toMutableList()
        private val timeTableGrid10 = timeTableRows.reversed().map { it.mirrorVertically() }.toMutableList()
        private val timeTableGrid11 = timeTableGrid10.map { it.reverseAndMirrorHorizontally() }.toMutableList()

        /**
         * Thanks to Zhuinden ;)
         */
        private fun getMirroredAngleFor(current: Float): Float {
            return if (current > 0 && current < 180) {
                180 - current
            } else {
                (360 - (current % 180))
            }
        }

        private fun List<ClockData>.mirrorVertically(): List<ClockData> {
            return this.map {
                ClockData(
                    degreeOne = getMirroredAngleFor(it.degreeOne),
                    degreeTwo = getMirroredAngleFor(it.degreeTwo)
                )
            }
        }


        private fun List<ClockData>.reverseAndMirrorHorizontally(): List<ClockData> {
            return reversed().map {
                val degreeOne = 360 - it.degreeOne
                val degreeTwo = 360 - it.degreeTwo
                ClockData(
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
            val intervalOne = (endNeedleOneDegree - startNeedleOneDegree) / COLUMN_CLOCK_COUNT
            val intervalTwo = (endNeedleTwoDegree - startNeedleTwoDegree) / COLUMN_CLOCK_COUNT
            var needleOne = startNeedleOneDegree
            var needleTwo = startNeedleTwoDegree

            repeat(COLUMN_CLOCK_COUNT) {
                add(
                    ClockData(
                        degreeOne = needleOne,
                        degreeTwo = needleTwo,
                    )
                )

                // Increment x and y
                needleOne += intervalOne
                needleTwo += intervalTwo
            }
        }

        fun getRippleMatrix(ripple: Movement.Ripple): List<List<ClockData>> {
            return mutableListOf<List<ClockData>>().apply {

                repeat(ROW_CLOCK_COUNT) { rowIndex ->
                    val list = when (ripple.to) {
                        Movement.Ripple.To.START -> mergeHorizontally(grid00, grid01, rowIndex)
                        Movement.Ripple.To.END -> mergeHorizontallyAndFlip(grid00, grid01, rowIndex)
                        Movement.Ripple.To.TIME_TABLE -> mergeHorizontally(timeTableGrid00, timeTableGrid01, rowIndex)
                    }

                    add(list)
                }

                repeat(ROW_CLOCK_COUNT) { i ->
                    val list = when (ripple.to) {
                        Movement.Ripple.To.START -> mergeHorizontally(grid10, grid11, i)
                        Movement.Ripple.To.END -> mergeHorizontallyAndFlip(grid10, grid11, i)
                        Movement.Ripple.To.TIME_TABLE -> mergeHorizontally(timeTableGrid10, timeTableGrid11, i)
                    }
                    add(list)
                }
            }
        }

        private fun mergeHorizontally(
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

        private fun mergeHorizontallyAndFlip(
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

                ClockData(
                    degreeOne = it.degreeOne + 360,
                    degreeTwo = it.degreeTwo - 360,
                )
            }
        }

    }

    override fun generateMatrix(): List<List<ClockData>> {
        return getRippleMatrix(data)
    }


}