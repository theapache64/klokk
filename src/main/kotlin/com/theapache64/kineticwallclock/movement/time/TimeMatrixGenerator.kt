package com.theapache64.kineticwallclock.movement.time

import com.theapache64.kineticwallclock.DIGIT_COLUMNS
import com.theapache64.kineticwallclock.DIGIT_ROWS
import com.theapache64.kineticwallclock.model.ClockData
import com.theapache64.kineticwallclock.movement.StandByMatrixGenerator
import com.theapache64.kineticwallclock.movement.core.MatrixGenerator
import com.theapache64.kineticwallclock.movement.core.Movement

class TimeMatrixGenerator(data: Movement.Time) : MatrixGenerator<Movement.Time>(data) {
    companion object {

        private val h1Position = Pair(1, 1)
        private val h2Position = Pair(1, 4)
        private val m1Position = Pair(1, 8)
        private val m2Position = Pair(1, 11)

        fun getTimeMatrix(time: Movement.Time): List<List<ClockData>> {

            // TODO : Calculate time dynamically
            val h1 = 4
            val h2 = 5
            val m1 = 2
            val m2 = 3

            val h1Matrix = getMatrixFor(h1)
            val h2Matrix = getMatrixFor(h2)
            val m1Matrix = getMatrixFor(m1)
            val m2Matrix = getMatrixFor(m2)

            return mutableListOf<List<ClockData>>().apply {
                val standByMatrix = StandByMatrixGenerator(Movement.StandBy())
                    .getVerifiedMatrix()
                    .map { it.toMutableList() }
                    .toMutableList()

                // first standby

                // Now change what we need

                // First hour
                replace(standByMatrix, h1Matrix, h1Position)
                replace(standByMatrix, h2Matrix, h2Position)
                replace(standByMatrix, m1Matrix, m1Position)
                replace(standByMatrix, m2Matrix, m2Position)


                // Finally add to list
                addAll(standByMatrix)
            }
        }

        private fun replace(
            standByMatrix: MutableList<MutableList<ClockData>>,
            replacementMatrix: List<List<ClockData?>>,
            replacementCoordinate: Pair<Int, Int>,
        ) {
            repeat(DIGIT_ROWS) { i ->
                repeat(DIGIT_COLUMNS) { j ->
                    val element = replacementMatrix[i][j]
                    if (element != null) {
                        val targetX = replacementCoordinate.first + i
                        val targetY = replacementCoordinate.second + j
                        standByMatrix[targetX][targetY] = element
                    }
                }
            }
        }

        private fun getMatrixFor(digit: Int): List<List<ClockData?>> {
            val digitMatrix: DigitMatrix = when (digit) {
                0 -> ZeroMatrix
                1 -> OneMatrix
                2 -> TwoMatrix
                3 -> ThreeMatrix
                4 -> FourMatrix
                5 -> FiveMatrix
                else -> throw IllegalAccessException("Matrix not defined for $digit")
            }

            return verifyIntegrityAndReturn(digitMatrix.getMatrix())
        }


        private fun verifyIntegrityAndReturn(matrix: List<List<ClockData?>>): List<List<ClockData?>> {
            require(matrix.size == DIGIT_ROWS) {
                "No of digit rows should be $DIGIT_ROWS but found ${matrix.size}"
            }
            for (columns in matrix) {
                require(columns.size == DIGIT_COLUMNS) {
                    "No of digit columns should be $DIGIT_COLUMNS, but found ${columns.size}"
                }
            }
            return matrix
        }

    }

    override fun generateMatrix(): List<List<ClockData>> {
        return getTimeMatrix(data)
    }
}