package com.theapache64.klokk.model

/**
 * To hold individual clock data
 */
data class ClockData(
    var degreeOne: Float,
    var degreeTwo: Float,
    var delay: Int = 0,
)