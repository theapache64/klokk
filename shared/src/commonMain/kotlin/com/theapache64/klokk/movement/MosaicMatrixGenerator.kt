package com.theapache64.klokk.movement

import com.theapache64.klokk.COLUMNS
import com.theapache64.klokk.ROWS
import com.theapache64.klokk.model.ClockData
import com.theapache64.klokk.movement.core.MatrixGenerator
import com.theapache64.klokk.movement.core.Movement
import kotlin.math.*

/**
 * Responsible for generating complex mesmerizing mosaic patterns
 */
class MosaicMatrixGenerator(data: Movement.Mosaic) : MatrixGenerator<Movement.Mosaic>(data) {

    companion object {
        private const val CENTER_ROW = ROWS / 2f - 0.5f
        private const val CENTER_COL = COLUMNS / 2f - 0.5f

        fun getMosaicMatrix(mosaic: Movement.Mosaic): List<List<ClockData>> {
            return when (mosaic.to) {
                Movement.Mosaic.To.MONA_LISA -> getMonaLisaMatrix()
                Movement.Mosaic.To.MANDALA -> getMandalaMatrix()
                Movement.Mosaic.To.GALAXY -> getGalaxyMatrix()
                Movement.Mosaic.To.HYPNOTIC_RINGS -> getHypnoticRingsMatrix()
                Movement.Mosaic.To.WAVE_INTERFERENCE -> getWaveInterferenceMatrix()
                Movement.Mosaic.To.INFINITY -> getInfinityMatrix()
                Movement.Mosaic.To.PEACOCK -> getPeacockMatrix()
                Movement.Mosaic.To.VORTEX -> getVortexMatrix()
                Movement.Mosaic.To.FIBONACCI -> getFibonacciMatrix()
                Movement.Mosaic.To.OPTICAL_DEPTH -> getOpticalDepthMatrix()
                // New hypnotic patterns
                Movement.Mosaic.To.KALEIDOSCOPE -> getKaleidoscopeMatrix()
                Movement.Mosaic.To.AURORA -> getAuroraMatrix()
                Movement.Mosaic.To.TORNADO -> getTornadoMatrix()
                Movement.Mosaic.To.HEARTBEAT -> getHeartbeatMatrix()
                Movement.Mosaic.To.BLACK_HOLE -> getBlackHoleMatrix()
                Movement.Mosaic.To.ELECTRIC -> getElectricMatrix()
                Movement.Mosaic.To.OCEAN_WAVES -> getOceanWavesMatrix()
                Movement.Mosaic.To.BLOOM -> getBloomMatrix()
                Movement.Mosaic.To.TESSERACT -> getTesseractMatrix()
                Movement.Mosaic.To.STARBURST -> getStarburstMatrix()
            }
        }

        /**
         * MONA_LISA - Attempts to create a face-like portrait pattern
         * Uses clock hands to suggest facial features: eyes, nose, smile, hair outline
         */
        private fun getMonaLisaMatrix(): List<List<ClockData>> {
            // 8 rows x 15 columns - create a face portrait
            // Row 0-1: Hair/forehead
            // Row 2: Eyes
            // Row 3: Nose bridge
            // Row 4: Nose tip
            // Row 5: Mouth/smile
            // Row 6-7: Chin/neck

            val matrix = Array(ROWS) { Array(COLUMNS) { ClockData(225f, 225f) } }

            // Hair outline (top curves) - rows 0-1
            for (col in 3..11) {
                val distFromCenter = abs(col - 7)
                val hairAngle = 270f - distFromCenter * 15f
                matrix[0][col] = ClockData(hairAngle, hairAngle + 180)
                if (col in 4..10) {
                    matrix[1][col] = ClockData(270f, 90f) // vertical hair strands
                }
            }

            // Face outline left
            matrix[1][3] = ClockData(200f, 340f)
            matrix[2][2] = ClockData(180f, 0f)
            matrix[3][2] = ClockData(180f, 0f)
            matrix[4][2] = ClockData(160f, 20f)
            matrix[5][3] = ClockData(135f, 45f)

            // Face outline right
            matrix[1][11] = ClockData(340f, 160f)
            matrix[2][12] = ClockData(0f, 180f)
            matrix[3][12] = ClockData(0f, 180f)
            matrix[4][12] = ClockData(20f, 160f)
            matrix[5][11] = ClockData(45f, 225f)

            // Left eye (row 2, cols 4-5)
            matrix[2][4] = ClockData(45f, 135f)   // left corner
            matrix[2][5] = ClockData(315f, 225f)  // right corner (forms eye shape)

            // Right eye (row 2, cols 9-10)
            matrix[2][9] = ClockData(45f, 135f)
            matrix[2][10] = ClockData(315f, 225f)

            // Eyebrows
            matrix[1][4] = ClockData(330f, 210f)
            matrix[1][5] = ClockData(350f, 190f)
            matrix[1][9] = ClockData(190f, 350f)
            matrix[1][10] = ClockData(210f, 330f)

            // Nose (cols 7, rows 2-4)
            matrix[2][7] = ClockData(270f, 90f)   // nose bridge
            matrix[3][7] = ClockData(270f, 90f)   // nose middle
            matrix[4][6] = ClockData(45f, 135f)   // nose left
            matrix[4][7] = ClockData(270f, 90f)   // nose tip
            matrix[4][8] = ClockData(135f, 45f)   // nose right

            // Mona Lisa smile (subtle curve, row 5)
            matrix[5][5] = ClockData(30f, 150f)   // left smile corner (slight upturn)
            matrix[5][6] = ClockData(350f, 170f)  // left lip
            matrix[5][7] = ClockData(0f, 180f)    // center lip
            matrix[5][8] = ClockData(10f, 190f)   // right lip
            matrix[5][9] = ClockData(150f, 30f)   // right smile corner

            // Chin (row 6-7)
            matrix[6][6] = ClockData(225f, 315f)
            matrix[6][7] = ClockData(270f, 90f)
            matrix[6][8] = ClockData(315f, 225f)
            matrix[7][7] = ClockData(270f, 90f)   // chin point

            // Neck
            matrix[7][6] = ClockData(250f, 110f)
            matrix[7][8] = ClockData(290f, 70f)

            return matrix.map { it.toList() }
        }

        /**
         * MANDALA - Complex radial symmetry with multiple concentric patterns
         * Creates a meditative, symmetric design radiating from center
         */
        private fun getMandalaMatrix(): List<List<ClockData>> {
            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        val dx = col - CENTER_COL
                        val dy = (row - CENTER_ROW) * 1.875f // aspect ratio correction
                        val distance = sqrt(dx * dx + dy * dy)
                        val angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()

                        // Create 8-fold symmetry
                        val symmetryAngle = ((angle + 360) % 45f) // 8-fold
                        val ring = (distance * 2).toInt()

                        // Different patterns for different rings
                        val (deg1, deg2) = when (ring % 4) {
                            0 -> Pair(angle, angle + 180) // pointing outward
                            1 -> Pair(angle + 45, angle + 225) // 45° offset
                            2 -> Pair(angle + 90, angle + 270) // perpendicular
                            else -> Pair(angle - 45, angle + 135) // -45° offset
                        }

                        // Add petal effect at certain angles
                        val petalBoost = if (symmetryAngle < 22.5f) 30f else 0f

                        rowList.add(
                            ClockData(
                                degreeOne = (deg1 + petalBoost) % 360,
                                degreeTwo = (deg2 + petalBoost) % 360
                            )
                        )
                    }
                    add(rowList)
                }
            }
        }

        /**
         * GALAXY - Spiral galaxy with logarithmic spiral arms
         * Creates the appearance of rotating spiral arms like the Milky Way
         */
        private fun getGalaxyMatrix(): List<List<ClockData>> {
            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        val dx = col - CENTER_COL
                        val dy = (row - CENTER_ROW) * 1.875f
                        val distance = sqrt(dx * dx + dy * dy)
                        val angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()

                        // Logarithmic spiral: r = a * e^(b*theta)
                        // Reverse: theta = ln(r/a) / b
                        val spiralTightness = 0.3f
                        val spiralAngle = angle + distance * 40f * spiralTightness

                        // Two spiral arms (180° apart)
                        val arm1 = spiralAngle % 360
                        val arm2 = (spiralAngle + 180) % 360

                        // Clocks align tangent to spiral
                        val tangentAngle = spiralAngle + 90

                        // Core is denser - needles converge
                        val coreFactor = max(0f, 1f - distance / 4f)
                        val spread = 90f * (1f - coreFactor)

                        rowList.add(
                            ClockData(
                                degreeOne = tangentAngle - spread / 2,
                                degreeTwo = tangentAngle + spread / 2
                            )
                        )
                    }
                    add(rowList)
                }
            }
        }

        /**
         * HYPNOTIC_RINGS - Concentric rings that create illusion of rotation/movement
         * Classic hypnotic spiral effect
         */
        private fun getHypnoticRingsMatrix(): List<List<ClockData>> {
            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        val dx = col - CENTER_COL
                        val dy = (row - CENTER_ROW) * 1.875f
                        val distance = sqrt(dx * dx + dy * dy)
                        val angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()

                        // Concentric rings with phase shift per ring
                        val ringIndex = (distance * 1.5f).toInt()
                        val phaseShift = ringIndex * 30f // each ring rotated 30°

                        // Alternating ring direction creates hypnotic effect
                        val direction = if (ringIndex % 2 == 0) 1 else -1
                        val ringAngle = angle * direction + phaseShift

                        // Needles form a tangent to the circle with slight offset
                        rowList.add(
                            ClockData(
                                degreeOne = ringAngle + 90,
                                degreeTwo = ringAngle - 90
                            )
                        )
                    }
                    add(rowList)
                }
            }
        }

        /**
         * WAVE_INTERFERENCE - Two wave sources creating interference pattern
         * Simulates ripples in water from two stones
         */
        private fun getWaveInterferenceMatrix(): List<List<ClockData>> {
            // Two wave sources
            val source1Col = CENTER_COL - 3
            val source1Row = CENTER_ROW
            val source2Col = CENTER_COL + 3
            val source2Row = CENTER_ROW

            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        // Distance from each source
                        val dx1 = col - source1Col
                        val dy1 = (row - source1Row) * 1.5f
                        val dist1 = sqrt(dx1 * dx1 + dy1 * dy1)

                        val dx2 = col - source2Col
                        val dy2 = (row - source2Row) * 1.5f
                        val dist2 = sqrt(dx2 * dx2 + dy2 * dy2)

                        // Wave phase from each source
                        val wavelength = 2f
                        val phase1 = (dist1 / wavelength) * 360f
                        val phase2 = (dist2 / wavelength) * 360f

                        // Interference: superposition of waves
                        val combinedPhase = (phase1 + phase2) / 2f
                        val interference = cos(Math.toRadians((phase1 - phase2).toDouble())).toFloat()

                        // Amplitude affects needle spread
                        val spread = 90f * abs(interference)

                        rowList.add(
                            ClockData(
                                degreeOne = combinedPhase - spread,
                                degreeTwo = combinedPhase + spread
                            )
                        )
                    }
                    add(rowList)
                }
            }
        }

        /**
         * INFINITY - Figure 8 / lemniscate pattern
         * Mathematical infinity symbol flowing through the display
         */
        private fun getInfinityMatrix(): List<List<ClockData>> {
            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        // Lemniscate of Bernoulli: (x² + y²)² = a²(x² - y²)
                        val x = (col - CENTER_COL) / 3f
                        val y = (row - CENTER_ROW) / 2f

                        // Calculate distance to nearest point on lemniscate
                        // Parametric: x = a*cos(t)/(1+sin²(t)), y = a*sin(t)*cos(t)/(1+sin²(t))
                        var minDist = Float.MAX_VALUE
                        var nearestAngle = 0f

                        for (t in 0 until 360 step 5) {
                            val tRad = Math.toRadians(t.toDouble())
                            val sinT = sin(tRad)
                            val cosT = cos(tRad)
                            val denom = 1 + sinT * sinT

                            val lx = (3.5 * cosT / denom).toFloat()
                            val ly = (2.0 * sinT * cosT / denom).toFloat()

                            val dist = sqrt((x - lx) * (x - lx) + (y - ly) * (y - ly))
                            if (dist < minDist) {
                                minDist = dist
                                nearestAngle = t.toFloat()
                            }
                        }

                        // Clocks flow along the infinity curve
                        val flowAngle = nearestAngle + 90 // tangent direction
                        val intensity = max(0f, 1f - minDist * 2)

                        // Closer to curve = more aligned needles
                        val spread = 180f * (1f - intensity)

                        rowList.add(
                            ClockData(
                                degreeOne = flowAngle - spread / 2,
                                degreeTwo = flowAngle + spread / 2
                            )
                        )
                    }
                    add(rowList)
                }
            }
        }

        /**
         * PEACOCK - Peacock feather fan pattern radiating outward
         * Creates the distinctive eye-spot pattern of peacock feathers
         */
        private fun getPeacockMatrix(): List<List<ClockData>> {
            // Fan originates from bottom center
            val originCol = CENTER_COL
            val originRow = ROWS + 2f // below the visible area

            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        val dx = col - originCol
                        val dy = row - originRow
                        val distance = sqrt(dx * dx + dy * dy)
                        val angle = Math.toDegrees(atan2(dx.toDouble(), -dy.toDouble())).toFloat()

                        // Create feather "eyes" at certain distances
                        val featherRing = (distance / 2.5f).toInt()
                        val isEyeRing = featherRing == 2 || featherRing == 3

                        // Radial segments for individual feathers
                        val featherSegment = ((angle + 90) / 12f).toInt() // ~15 feathers
                        val segmentAngle = featherSegment * 12f - 90

                        if (isEyeRing) {
                            // Eye spots - concentric circles
                            val eyeCenterAngle = segmentAngle
                            val angleToEye = abs(angle - eyeCenterAngle)
                            if (angleToEye < 6f) {
                                // Inside eye - circular pattern
                                rowList.add(
                                    ClockData(
                                        degreeOne = angle + 90,
                                        degreeTwo = angle - 90
                                    )
                                )
                            } else {
                                // Eye border
                                rowList.add(
                                    ClockData(
                                        degreeOne = angle,
                                        degreeTwo = angle + 180
                                    )
                                )
                            }
                        } else {
                            // Feather barbs - radiate outward
                            val barbAngle = angle + sin(Math.toRadians(distance * 30.0)).toFloat() * 15f
                            rowList.add(
                                ClockData(
                                    degreeOne = barbAngle,
                                    degreeTwo = barbAngle + 180
                                )
                            )
                        }
                    }
                    add(rowList)
                }
            }
        }

        /**
         * VORTEX - Deep vortex/whirlpool with sense of depth
         * Creates illusion of being pulled into a spinning vortex
         */
        private fun getVortexMatrix(): List<List<ClockData>> {
            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        val dx = col - CENTER_COL
                        val dy = (row - CENTER_ROW) * 1.875f
                        val distance = sqrt(dx * dx + dy * dy)
                        val angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()

                        // Vortex spiral - tighter toward center
                        val spiralFactor = 1f / (distance + 0.5f) // stronger rotation near center
                        val vortexAngle = angle + distance * 60f * spiralFactor

                        // Depth effect - needles converge more toward center
                        val depthFactor = min(1f, distance / 5f)
                        val spread = 180f * depthFactor // 0° at center, 180° at edge

                        // Tangent to spiral for flow effect
                        val flowAngle = vortexAngle + 90

                        rowList.add(
                            ClockData(
                                degreeOne = flowAngle - spread / 2,
                                degreeTwo = flowAngle + spread / 2
                            )
                        )
                    }
                    add(rowList)
                }
            }
        }

        /**
         * FIBONACCI - Fibonacci spiral pattern (golden ratio)
         * Nature's most beautiful pattern - sunflower seeds, shells
         */
        private fun getFibonacciMatrix(): List<List<ClockData>> {
            val goldenAngle = 137.508f // degrees

            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        val dx = col - CENTER_COL
                        val dy = (row - CENTER_ROW) * 1.875f
                        val distance = sqrt(dx * dx + dy * dy)
                        val angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()

                        // Find which Fibonacci spiral arm this point is near
                        // Each seed n is at angle n * golden_angle and radius sqrt(n)
                        val seedIndex = (distance * distance).toInt()
                        val fibAngle = (seedIndex * goldenAngle) % 360

                        // Calculate the local spiral direction
                        val spiralDirection = angle - fibAngle
                        val normalizedDir = ((spiralDirection + 540) % 360) - 180

                        // Two interlocking spiral families (clockwise and counter-clockwise)
                        val spiral1 = (angle + distance * 20f) % 360
                        val spiral2 = (angle - distance * 32f) % 360 // Fibonacci ratio ~1.618

                        // Blend based on position
                        val blend = (sin(Math.toRadians(angle * 2.0)) + 1) / 2f

                        rowList.add(
                            ClockData(
                                degreeOne = spiral1 * blend.toFloat() + spiral2 * (1 - blend).toFloat(),
                                degreeTwo = spiral1 * blend.toFloat() + spiral2 * (1 - blend).toFloat() + 180
                            )
                        )
                    }
                    add(rowList)
                }
            }
        }

        /**
         * OPTICAL_DEPTH - Creates 3D depth illusion
         * Makes the flat display appear to have depth/curvature
         */
        private fun getOpticalDepthMatrix(): List<List<ClockData>> {
            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        val dx = col - CENTER_COL
                        val dy = (row - CENTER_ROW) * 1.875f
                        val distance = sqrt(dx * dx + dy * dy)
                        val angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()

                        // Simulate a sphere bulging out
                        // Points near center appear closer (larger apparent size)
                        val maxRadius = 6f
                        val normalizedDist = min(1f, distance / maxRadius)

                        // Sphere surface normal - points outward from sphere surface
                        val sphereDepth = sqrt(max(0f, 1f - normalizedDist * normalizedDist))
                        val surfaceAngle = Math.toDegrees(atan2(sphereDepth.toDouble(), normalizedDist.toDouble())).toFloat()

                        // Light source from top-left
                        val lightAngle = 315f
                        val lightDiff = ((angle - lightAngle + 540) % 360) - 180

                        // Shading based on surface normal vs light direction
                        val shading = cos(Math.toRadians(lightDiff.toDouble())).toFloat() * sphereDepth

                        // Needles create shading effect
                        // Lit areas: needles spread, shadow areas: needles converge
                        val baseAngle = angle + 90 // tangent to radial
                        val shadingSpread = 45f + shading * 90f

                        rowList.add(
                            ClockData(
                                degreeOne = baseAngle - shadingSpread,
                                degreeTwo = baseAngle + shadingSpread
                            )
                        )
                    }
                    add(rowList)
                }
            }
        }

        // ==================== NEW HYPNOTIC PATTERNS ====================

        /**
         * KALEIDOSCOPE - 12-fold symmetry like looking through a kaleidoscope
         * Creates mesmerizing symmetric reflections
         */
        private fun getKaleidoscopeMatrix(): List<List<ClockData>> {
            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        val dx = col - CENTER_COL
                        val dy = (row - CENTER_ROW) * 1.875f
                        val distance = sqrt(dx * dx + dy * dy)
                        var angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()

                        // 12-fold symmetry - fold angle into 30° wedge
                        angle = ((angle % 30) + 30) % 30

                        // Mirror within the wedge
                        if (angle > 15) angle = 30 - angle

                        // Create layered effect based on distance
                        val layer = (distance * 1.2f).toInt()
                        val layerAngle = angle + layer * 60f

                        // Alternating convergent/divergent pattern
                        val spread = if (layer % 2 == 0) 30f else 150f

                        rowList.add(
                            ClockData(
                                degreeOne = layerAngle,
                                degreeTwo = layerAngle + spread
                            )
                        )
                    }
                    add(rowList)
                }
            }
        }

        /**
         * AURORA - Northern lights wave effect
         * Flowing curtains of light like aurora borealis
         */
        private fun getAuroraMatrix(): List<List<ClockData>> {
            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        // Multiple overlapping sine waves for aurora curtain effect
                        val wave1 = sin(col * 0.5 + row * 0.3) * 40f
                        val wave2 = sin(col * 0.3 - row * 0.2) * 30f
                        val wave3 = sin(col * 0.7 + row * 0.1) * 20f

                        val combinedWave = wave1 + wave2 + wave3

                        // Vertical flow with horizontal undulation
                        val baseAngle = 270f + combinedWave.toFloat()

                        // Intensity varies by row (stronger at top)
                        val intensity = 1f - (row.toFloat() / ROWS)
                        val spread = 20f + intensity * 60f

                        rowList.add(
                            ClockData(
                                degreeOne = baseAngle - spread / 2,
                                degreeTwo = baseAngle + spread / 2
                            )
                        )
                    }
                    add(rowList)
                }
            }
        }

        /**
         * TORNADO - Funnel-shaped rotating vortex
         * Creates the appearance of a tornado/whirlwind
         */
        private fun getTornadoMatrix(): List<List<ClockData>> {
            // Funnel center at top-center
            val funnelTopCol = CENTER_COL
            val funnelTopRow = -2f

            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        // Distance from funnel axis (which expands as row increases)
                        val funnelWidth = 0.5f + row * 0.4f // funnel expands downward
                        val dx = (col - funnelTopCol) / funnelWidth
                        val dy = row - funnelTopRow

                        val distFromAxis = abs(dx)
                        val angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()

                        // Rotation speed increases toward center of funnel
                        val rotationIntensity = max(0f, 1f - distFromAxis / 3f)
                        val spiralAngle = angle + rotationIntensity * 180f + row * 30f

                        // Inside funnel vs outside
                        val isInsideFunnel = distFromAxis < 2f
                        val spread = if (isInsideFunnel) 30f + rotationIntensity * 60f else 120f

                        rowList.add(
                            ClockData(
                                degreeOne = spiralAngle,
                                degreeTwo = spiralAngle + spread
                            )
                        )
                    }
                    add(rowList)
                }
            }
        }

        /**
         * HEARTBEAT - Pulsing heart shape
         * Creates a heart pattern that appears to pulse
         */
        private fun getHeartbeatMatrix(): List<List<ClockData>> {
            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        // Heart curve parametric equation check
                        val x = (col - CENTER_COL) / 3.5f
                        val y = (CENTER_ROW - row + 1) / 2.5f // flip y, shift up

                        // Heart equation: (x² + y² - 1)³ - x²y³ = 0
                        // Distance to heart surface
                        val heartValue = (x * x + y * y - 1).pow(3) - x * x * y * y * y

                        // Determine if inside/outside/on heart
                        val distToHeart = abs(heartValue)
                        val isInsideHeart = heartValue < 0

                        if (distToHeart < 0.5f) {
                            // On the heart outline - flow along it
                            val tangentAngle = Math.toDegrees(atan2(y.toDouble(), x.toDouble())).toFloat() + 90
                            rowList.add(
                                ClockData(
                                    degreeOne = tangentAngle,
                                    degreeTwo = tangentAngle + 180
                                )
                            )
                        } else if (isInsideHeart) {
                            // Inside heart - radiate outward from center
                            val angle = Math.toDegrees(atan2((row - CENTER_ROW).toDouble(), (col - CENTER_COL).toDouble())).toFloat()
                            rowList.add(
                                ClockData(
                                    degreeOne = angle + 180,
                                    degreeTwo = angle
                                )
                            )
                        } else {
                            // Outside heart - point toward heart
                            val angle = Math.toDegrees(atan2((row - CENTER_ROW).toDouble(), (col - CENTER_COL).toDouble())).toFloat()
                            rowList.add(
                                ClockData(
                                    degreeOne = angle,
                                    degreeTwo = angle + 180
                                )
                            )
                        }
                    }
                    add(rowList)
                }
            }
        }

        /**
         * BLACK_HOLE - Event horizon with gravitational lensing effect
         * Space bending around a singularity
         */
        private fun getBlackHoleMatrix(): List<List<ClockData>> {
            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        val dx = col - CENTER_COL
                        val dy = (row - CENTER_ROW) * 1.875f
                        val distance = sqrt(dx * dx + dy * dy)
                        val angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()

                        // Event horizon radius
                        val eventHorizon = 2f

                        if (distance < eventHorizon) {
                            // Inside event horizon - all points toward singularity
                            rowList.add(
                                ClockData(
                                    degreeOne = angle + 180,
                                    degreeTwo = angle + 180
                                )
                            )
                        } else {
                            // Gravitational lensing - light bends around
                            val bendFactor = eventHorizon * eventHorizon / (distance * distance)
                            val bentAngle = angle + bendFactor * 90f

                            // Accretion disk effect - tangential flow
                            val diskAngle = angle + 90 + bendFactor * 180f

                            rowList.add(
                                ClockData(
                                    degreeOne = diskAngle,
                                    degreeTwo = bentAngle
                                )
                            )
                        }
                    }
                    add(rowList)
                }
            }
        }

        /**
         * ELECTRIC - Lightning/electric field lines
         * Creates the appearance of electric field between charges
         */
        private fun getElectricMatrix(): List<List<ClockData>> {
            // Two charges: positive on left, negative on right
            val charge1Col = CENTER_COL - 4
            val charge1Row = CENTER_ROW
            val charge2Col = CENTER_COL + 4
            val charge2Row = CENTER_ROW

            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        // Vector from each charge
                        val dx1 = col - charge1Col
                        val dy1 = (row - charge1Row) * 1.5f
                        val dist1 = sqrt(dx1 * dx1 + dy1 * dy1) + 0.1f

                        val dx2 = col - charge2Col
                        val dy2 = (row - charge2Row) * 1.5f
                        val dist2 = sqrt(dx2 * dx2 + dy2 * dy2) + 0.1f

                        // Electric field is sum of fields from both charges
                        // Field points away from positive, toward negative
                        val ex = dx1 / (dist1 * dist1 * dist1) - dx2 / (dist2 * dist2 * dist2)
                        val ey = dy1 / (dist1 * dist1 * dist1) - dy2 / (dist2 * dist2 * dist2)

                        val fieldAngle = Math.toDegrees(atan2(ey.toDouble(), ex.toDouble())).toFloat()
                        val fieldStrength = sqrt(ex * ex + ey * ey)

                        // Stronger field = more aligned needles
                        val spread = max(10f, 180f - fieldStrength * 500f)

                        rowList.add(
                            ClockData(
                                degreeOne = fieldAngle - spread / 2,
                                degreeTwo = fieldAngle + spread / 2
                            )
                        )
                    }
                    add(rowList)
                }
            }
        }

        /**
         * OCEAN_WAVES - Rolling ocean waves
         * Creates the appearance of waves rolling toward shore
         */
        private fun getOceanWavesMatrix(): List<List<ClockData>> {
            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        // Wave function - multiple overlapping waves
                        val wavePhase1 = col * 0.8f + row * 0.3f
                        val wavePhase2 = col * 0.5f - row * 0.2f

                        val wave1 = sin(wavePhase1.toDouble()).toFloat()
                        val wave2 = sin(wavePhase2.toDouble()).toFloat() * 0.5f

                        val combinedWave = wave1 + wave2

                        // Wave crest vs trough
                        val isCrest = combinedWave > 0.3f
                        val isTrough = combinedWave < -0.3f

                        val baseAngle = when {
                            isCrest -> 315f + combinedWave * 30f  // curling over
                            isTrough -> 135f + combinedWave * 30f // pulling back
                            else -> 270f + combinedWave * 45f     // middle
                        }

                        // Foam effect at crests
                        val spread = if (isCrest) 60f else 120f

                        rowList.add(
                            ClockData(
                                degreeOne = baseAngle,
                                degreeTwo = baseAngle + spread
                            )
                        )
                    }
                    add(rowList)
                }
            }
        }

        /**
         * BLOOM - Flower blooming outward
         * Petals unfurling from center
         */
        private fun getBloomMatrix(): List<List<ClockData>> {
            val petalCount = 8

            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        val dx = col - CENTER_COL
                        val dy = (row - CENTER_ROW) * 1.875f
                        val distance = sqrt(dx * dx + dy * dy)
                        val angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()

                        // Petal shape using rose curve: r = cos(k*theta)
                        val petalAngle = angle * petalCount / 2
                        val petalShape = abs(cos(Math.toRadians(petalAngle.toDouble()))).toFloat()

                        // Distance along petal
                        val petalDistance = distance / (petalShape * 4 + 1)

                        // Clocks curve outward along petals
                        val curlFactor = petalShape * (1 - petalDistance / 5)
                        val petalFlow = angle + curlFactor * 45f

                        // Spread based on position on petal
                        val spread = 30f + (1 - petalShape) * 120f

                        rowList.add(
                            ClockData(
                                degreeOne = petalFlow,
                                degreeTwo = petalFlow + spread
                            )
                        )
                    }
                    add(rowList)
                }
            }
        }

        /**
         * TESSERACT - 4D hypercube projection illusion
         * Creates the appearance of a rotating 4D cube
         */
        private fun getTesseractMatrix(): List<List<ClockData>> {
            // Inner and outer cube centers
            val innerSize = 1.5f
            val outerSize = 3.5f

            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        val x = col - CENTER_COL
                        val y = (row - CENTER_ROW) * 1.875f

                        // Check if on inner cube edges
                        val onInnerX = abs(abs(x) - innerSize) < 0.8f && abs(y) <= innerSize
                        val onInnerY = abs(abs(y) - innerSize) < 0.8f && abs(x) <= innerSize

                        // Check if on outer cube edges
                        val onOuterX = abs(abs(x) - outerSize) < 0.8f && abs(y) <= outerSize
                        val onOuterY = abs(abs(y) - outerSize) < 0.8f && abs(x) <= outerSize

                        // Check if on connecting edges (corners)
                        val isCornerRegion = abs(x) > innerSize && abs(y) > innerSize &&
                                abs(x) < outerSize && abs(y) < outerSize

                        val clockData = when {
                            onInnerX || onOuterX -> ClockData(0f, 180f)      // horizontal edge
                            onInnerY || onOuterY -> ClockData(90f, 270f)     // vertical edge
                            isCornerRegion -> {
                                // Diagonal connecting edges
                                val diagonalAngle = Math.toDegrees(atan2(y.toDouble(), x.toDouble())).toFloat()
                                ClockData(diagonalAngle, diagonalAngle + 180)
                            }
                            else -> {
                                // Interior space - subtle rotation effect
                                val dist = sqrt(x * x + y * y)
                                val angle = Math.toDegrees(atan2(y.toDouble(), x.toDouble())).toFloat()
                                ClockData(angle + dist * 10, angle + dist * 10 + 90)
                            }
                        }
                        rowList.add(clockData)
                    }
                    add(rowList)
                }
            }
        }

        /**
         * STARBURST - Exploding star with rays
         * Dramatic rays shooting outward from center
         */
        private fun getStarburstMatrix(): List<List<ClockData>> {
            val rayCount = 16

            return mutableListOf<List<ClockData>>().apply {
                repeat(ROWS) { row ->
                    val rowList = mutableListOf<ClockData>()
                    repeat(COLUMNS) { col ->
                        val dx = col - CENTER_COL
                        val dy = (row - CENTER_ROW) * 1.875f
                        val distance = sqrt(dx * dx + dy * dy)
                        val angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()

                        // Determine which ray this point is on
                        val rayAngle = 360f / rayCount
                        val normalizedAngle = ((angle % rayAngle) + rayAngle) % rayAngle
                        val distFromRayCenter = abs(normalizedAngle - rayAngle / 2)

                        // On a ray vs between rays
                        val isOnRay = distFromRayCenter < rayAngle / 4

                        if (isOnRay) {
                            // On ray - point outward with energy
                            val energyPulse = sin(distance * 0.8).toFloat() * 15f
                            rowList.add(
                                ClockData(
                                    degreeOne = angle + energyPulse,
                                    degreeTwo = angle + energyPulse
                                )
                            )
                        } else {
                            // Between rays - swirl effect
                            val swirlAngle = angle + 90 + distance * 5
                            rowList.add(
                                ClockData(
                                    degreeOne = swirlAngle,
                                    degreeTwo = swirlAngle + 60
                                )
                            )
                        }
                    }
                    add(rowList)
                }
            }
        }
    }

    override fun generateMatrix(): List<List<ClockData>> {
        return getMosaicMatrix(data)
    }
}
