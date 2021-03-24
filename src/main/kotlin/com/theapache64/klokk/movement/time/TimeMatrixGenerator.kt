package com.theapache64.klokk.movement.time

import com.theapache64.klokk.DIGIT_COLUMNS
import com.theapache64.klokk.DIGIT_ROWS
import com.theapache64.klokk.model.ClockData
import com.theapache64.klokk.movement.StandByMatrixGenerator
import com.theapache64.klokk.movement.core.MatrixGenerator
import com.theapache64.klokk.movement.core.Movement
import com.theapache64.klokk.movement.globalStandByClockData
import java.text.SimpleDateFormat

/**
 * Responsible to show time animation and digits.
 */
class TimeMatrixGenerator(data: Movement.Time) : MatrixGenerator<Movement.Time>(data) {
    companion object {

        private val timeFormat = SimpleDateFormat("HHmmss")

        private val h1Position = Pair(1, 1)
        private val h2Position = Pair(1, 4)
        private val m1Position = Pair(1, 8)
        private val m2Position = Pair(1, 11)

        private fun getMatrixFor(digit: Int): List<List<ClockData?>> {
            val digitMatrix: DigitMatrix = when (digit) {
                0 -> ZeroMatrix
                1 -> OneMatrix
                2 -> TwoMatrix
                3 -> ThreeMatrix
                4 -> FourMatrix
                5 -> FiveMatrix
                6 -> SixMatrix
                7 -> SevenMatrix
                8 -> EightMatrix
                9 -> NineMatrix
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

    private fun getTimeMatrix(time: Movement.Time): List<List<ClockData>> {

        val timeSplit = timeFormat.format(time.date)
            .split("")
            .filter { it.isNotEmpty() }
            .map { it.toInt() }

        val h1Matrix = getMatrixFor(timeSplit[0])
        val h2Matrix = getMatrixFor(timeSplit[1])
        val m1Matrix = getMatrixFor(timeSplit[2])
        val m2Matrix = getMatrixFor(timeSplit[3])
        val secondsClockData =
            getSecondsClockData(timeSplit.subList(4, timeSplit.size).joinToString(separator = "").toInt()) // joining

        return mutableListOf<List<ClockData>>().apply {
            val fullMatrix = StandByMatrixGenerator(Movement.StandBy)
                .getVerifiedMatrix()
                .map { it.toMutableList() }
                .toMutableList()

            // first standby

            // Now change what we need

            // First hour
            replace(fullMatrix, h1Matrix, h1Position)
            replace(fullMatrix, h2Matrix, h2Position)
            replace(fullMatrix, m1Matrix, m1Position)
            replace(fullMatrix, m2Matrix, m2Position)

            // Replace all standBy matrix with
            repeat(fullMatrix.size) { i ->
                val columns = fullMatrix[i]
                repeat(columns.size) { j ->
                    val element = fullMatrix[i][j]
                    if (element == globalStandByClockData) {
                        fullMatrix[i][j] = secondsClockData
                    }
                }
            }


            // Finally add to list
            addAll(fullMatrix)
        }
    }

    private fun getSecondsClockData(seconds: Int): ClockData {
        val needleDegree = seconds / 60f * 360
        println("$seconds -> $needleDegree degrees")
        return ClockData(degreeOne = needleDegree, degreeTwo = needleDegree)
    }

    override fun generateMatrix(): List<List<ClockData>> {
        return getTimeMatrix(data)
    }

    override fun getUnitRowCount(): Int = DIGIT_ROWS
    override fun getUnitColumnCount(): Int = DIGIT_COLUMNS
}