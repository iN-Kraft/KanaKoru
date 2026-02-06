package dev.datlag.kanakoru.dollarn

import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

class DollarNRecognizer(
    key: Char,
    rawStrokes: List<List<Point>>
) {

    private val numResamplePoints = 96
    private val squareSize = 250F
    private val origin = Point(0F, 0F)
    private val templates = listOf(Template(key, normalize(rawStrokes)))

    fun recognize(rawStrokes: List<List<Point>>): RecognitionResult {
        require(templates.isNotEmpty())

        val points = normalize(rawStrokes)
        var bestTemplate: Template? = null
        var bestDistance = Float.MAX_VALUE

        for (template in templates) {
            val dist = pathDistance(points, template.points)

            if (dist < bestDistance) {
                bestDistance = dist
                bestTemplate = template
            }
        }

        val score = if (bestDistance == Float.MAX_VALUE) {
            0F
        } else {
            (1.0f - (bestDistance / (0.5 * sqrt((squareSize * squareSize) + (squareSize * squareSize))))).toFloat()
        }
        val finalScore = score.coerceIn(0F, 1F)

        return RecognitionResult(
            key = bestTemplate?.key,
            score = finalScore
        )
    }

    private fun normalize(strokes: List<List<Point>>): List<Point> {
        val combinedPoints = strokes.flatten()
        if (combinedPoints.isEmpty()) {
            return emptyList()
        }

        val resampled = resample(combinedPoints, numResamplePoints)
        val scaled = scaleTo(resampled, squareSize)

        return translateTo(scaled, origin)
    }

    private fun resample(points: List<Point>, n: Int): List<Point> {
        val interval = pathLength(points) / (n - 1)
        var D = 0F
        val newPoints = mutableListOf(points.first())
        val srcPoints = points.toMutableList()

        var i = 1
        while (i < srcPoints.size) {
            val p1 = srcPoints[i - 1]
            val p2 = srcPoints[i]
            val d = p1.distanceTo(p2)

            if ((D + d) >= interval) {
                val qx = p1.x + ((interval - D) / d) * (p2.x - p1.x)
                val qy = p1.y + ((interval - D) / d) * (p2.y - p1.y)
                val q = Point(qx, qy)

                newPoints.add(q)
                srcPoints.add(i, q)
                D = 0F
            } else {
                D += d
            }
            i++
        }

        if (newPoints.size == n - 1) {
            newPoints.add(srcPoints.last())
        }
        return newPoints
    }

    private fun scaleTo(points: List<Point>, size: Float): List<Point> {
        val box = boundingBox(points)
        val newPoints = mutableListOf<Point>()
        val maxDimension = max(box.width, box.height)
        val scale = if (maxDimension > 0) size / maxDimension else 1F

        for (p in points) {
            val qx = p.x * scale
            val qy = p.y * scale

            newPoints.add(Point(qx, qy))
        }
        return newPoints
    }

    private fun translateTo(points: List<Point>, to: Point): List<Point> {
        val centroid = centroid(points)
        val newPoints = mutableListOf<Point>()

        for (p in points) {
            val qx = p.x + to.x - centroid.x
            val qy = p.y + to.y - centroid.y

            newPoints.add(Point(qx, qy))
        }
        return newPoints
    }

    private fun pathDistance(pts1: List<Point>, pts2: List<Point>): Float {
        var d = 0F
        val length = min(pts1.size, pts2.size)

        for (i in 0 until length) {
            d += pts1[i].distanceTo(pts2[i])
        }
        return d / length
    }

    private fun pathLength(points: List<Point>): Float {
        var d = 0F

        for (i in 1 until points.size) {
            d += points[i - 1].distanceTo(points[i])
        }
        return d
    }

    private fun boundingBox(points: List<Point>): Box {
        var minX = Float.MAX_VALUE
        var maxX = Float.MIN_VALUE
        var minY = Float.MAX_VALUE
        var maxY = Float.MIN_VALUE

        for (p in points) {
            if (p.x < minX) {
                minX = p.x
            }
            if (p.x > maxX) {
                maxX = p.x
            }
            if (p.y < minY) {
                minY = p.y
            }
            if (p.y > maxY) {
                maxY = p.y
            }
        }
        return Box(minX, minY, maxX - minX, maxY - minY)
    }

    private fun centroid(points: List<Point>): Point {
        var x = 0F
        var y = 0F

        for (p in points) {
            x += p.x
            y += p.y
        }
        return Point(x / points.size, y / points.size)
    }

    data class Template(
        val key: Char,
        val points: List<Point>
    )

    data class RecognitionResult(val key: Char?, val score: Float)
}