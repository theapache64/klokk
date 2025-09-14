package com.theapache64.klokk.movement.alphabet

import com.theapache64.klokk.COLUMNS
import com.theapache64.klokk.model.ClockData
import com.theapache64.klokk.movement.StandByMatrixGenerator
import com.theapache64.klokk.movement.core.MatrixGenerator
import com.theapache64.klokk.movement.core.Movement

/**
 * TODO : WIP (TBH, too lazy to create matrix map for all alphabets :/)
 */
class TextMatrixGenerator(data: Movement.Text) : MatrixGenerator<Movement.Text>(data) {

    companion object {

        private const val LETTER_WIDTH = 3
        const val MAX_CHARS = COLUMNS / LETTER_WIDTH

        private val keyMap by lazy {
            mapOf(
                'A' to AMatrix
            )
        }
    }

    override fun generateMatrix(): List<List<ClockData>> {
        val fullMatrix = StandByMatrixGenerator(Movement.StandBy)
            .getVerifiedMatrix()
            .map { it.toMutableList() }
            .toMutableList()

        for ((index, char) in data.text.withIndex()) {
            val matrix = keyMap[char] ?: throw IllegalArgumentException("Couldn't find matrix for char '$char'")
            replace(
                fullMatrix,
                matrix.getMatrix(),
                Pair(1, index * 3)
            )
        }
        return fullMatrix
    }

    override fun getUnitRowCount(): Int = 3
    override fun getUnitColumnCount(): Int = 3
}