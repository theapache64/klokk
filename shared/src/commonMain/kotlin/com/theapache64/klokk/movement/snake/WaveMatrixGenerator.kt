package com.theapache64.klokk.movement.snake

import com.theapache64.klokk.COLUMNS
import com.theapache64.klokk.ROWS
import com.theapache64.klokk.model.ClockData
import com.theapache64.klokk.movement.core.MatrixGenerator
import com.theapache64.klokk.movement.core.Movement

class WaveMatrixGenerator(data: Movement.Wave) : MatrixGenerator<Movement.Wave>(data) {

    companion object {

        private const val tilt = 22f
        private const val evenStartDegree = 270 - tilt
        private const val oddStartDegree = 270 + tilt
        private const val evenEndDegree = 90 - tilt
        private const val oddEndDegree = 90 + tilt

        val openClockData = ClockData(
            evenStartDegree,
            evenEndDegree
        )

        val closeClockData = ClockData(
            oddStartDegree,
            oddEndDegree
        )
    }

    override fun generateMatrix(): List<List<ClockData>> = mutableListOf<List<ClockData>>().apply {

        val firstRow = mutableListOf<ClockData>().apply {
            repeat(COLUMNS) { index ->
                val isEven = index % 2 == 0
                when (data.state) {
                    Movement.Wave.State.START -> {
                        if (isEven) {
                            add(openClockData)
                        } else {
                            add(closeClockData)
                        }
                    }

                    Movement.Wave.State.END -> {
                        if (isEven) {
                            add(closeClockData)
                        } else {
                            add(openClockData)
                        }
                    }
                }

            }
        }

        // Copy the first row
        repeat(ROWS) {
            add(firstRow)
        }
    }
}