package com.theapache64.klokk.movement.ripple

import com.theapache64.klokk.model.ClockData

class RippleData(
    private val startOne: Float,
    private val startTwo: Float,
    private val endOne: Float,
    private val endTwo: Float,
) {

    companion object {
        private fun getBaseRows(
            startOne: Float,
            startTwo: Float,
            endOne: Float,
            endTwo: Float,
        ): MutableList<List<ClockData>> {
            return mutableListOf<List<ClockData>>().apply {
                repeat(RippleMatrixGenerator.ROW_CLOCK_COUNT) {
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
        }

        private fun MutableList<ClockData>.generateRow(
            startNeedleOneDegree: Float,
            startNeedleTwoDegree: Float,
            endNeedleOneDegree: Float,
            endNeedleTwoDegree: Float,
        ) {
            val intervalOne = (endNeedleOneDegree - startNeedleOneDegree) / RippleMatrixGenerator.COLUMN_CLOCK_COUNT
            val intervalTwo = (endNeedleTwoDegree - startNeedleTwoDegree) / RippleMatrixGenerator.COLUMN_CLOCK_COUNT
            var needleOne = startNeedleOneDegree
            var needleTwo = startNeedleTwoDegree

            repeat(RippleMatrixGenerator.COLUMN_CLOCK_COUNT) {
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
    }

    private val baseRows by lazy {
        getBaseRows(startOne, startTwo, endOne, endTwo)
    }
    val grid00 by lazy { baseRows }
    val grid01 by lazy { baseRows.map { it.reverseAndMirrorHorizontally() }.toMutableList() }
    val grid10 by lazy { baseRows.reversed().map { it.mirrorVertically() }.toMutableList() }
    val grid11 by lazy { grid10.map { it.reverseAndMirrorHorizontally() }.toMutableList() }
}
