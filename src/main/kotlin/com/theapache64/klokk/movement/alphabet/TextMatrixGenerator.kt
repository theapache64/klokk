package com.theapache64.klokk.movement.alphabet

import com.theapache64.klokk.model.ClockData
import com.theapache64.klokk.movement.core.MatrixGenerator
import com.theapache64.klokk.movement.core.Movement

class TextMatrixGenerator(data: Movement.Text) : MatrixGenerator<Movement.Text>(data) {
    override fun generateMatrix(): List<List<ClockData>> {
        TODO("Not yet implemented")
    }
}