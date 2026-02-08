package dev.datlag.kanakoru.dollarn

import arrow.core.raise.Raise
import arrow.core.raise.context.ensure
import arrow.core.raise.context.raise
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

class DollarN(
    templates: ImmutableMap<Char, ImmutableList<ImmutableList<Point>>>
) {
    private val numResamplePoints = 96
    private val squareSize = 250F
    private val origin = Point(0F, 0F)

    private val templates: ImmutableList<Template> = templates.map { (key, strokeList) ->
        Template(
            key = key,
            points = normalize(strokeList).toImmutableList(),
            strokeCount = strokeList.size
        )
    }.toImmutableList()

    private val maxTemplateStrokeCount
        get() = this.templates.maxOfOrNull { it.strokeCount } ?: 0

    context(_: Raise<Error>)
    fun recognize(rawStrokes: List<List<Point>>): Result {
        ensure(templates.isNotEmpty()) {
            raise(Error.NoTemplateProvided)
        }
        ensure(rawStrokes.size <= maxTemplateStrokeCount) {
            raise(Error.TooManyStrokes)
        }
        ensure(rawStrokes.isNotEmpty()) {
            raise(Error.NoStrokes)
        }

        val points = normalize(rawStrokes)
        val (bestTemplate, bestDistance) = templates.map { template ->
            template to pathDistance(points, template.points)
        }.minByOrNull { (_, distance) -> distance } ?: raise(Error.NoMatch)

        return Result(
            key = bestTemplate.key,
            score = calculateScore(bestDistance)
        )
    }

    private fun calculateScore(distance: Float): Float {
        if (distance == Float.MAX_VALUE) {
            return 0F
        }

        val diagonal = sqrt(2F * squareSize * squareSize)
        val halfDiagonal = diagonal / 2F
        val score = 1F - (distance / (halfDiagonal))

        return score.coerceIn(0F, 1F)
    }

    private fun normalize(strokes: List<List<Point>>): List<Point> {
        val combinedPoints = strokes.flatten()

        return combinedPoints
            .takeIf { it.isNotEmpty() }
            ?.let { points ->
                translateTo(scaleTo(resample(points, numResamplePoints), squareSize), origin)
            } ?: emptyList()
    }

    private fun resample(points: List<Point>, n: Int): List<Point> {
        val interval = pathLength(points) / (n - 1)
        val newPoints = mutableListOf(points.first())
        val srcPoints = points.toMutableList()
        var distance = 0F
        var i = 1

        while (i < srcPoints.size) {
            val p1 = srcPoints[i - 1]
            val p2 = srcPoints[i]
            val d = p1.distanceTo(p2)

            if (distance + d >= interval) {
                val ratio = (interval - distance) / d
                val q = Point(
                    x = p1.x + ratio * (p2.x - p1.x),
                    y = p1.y + ratio * (p2.y - p1.y)
                )

                newPoints.add(q)
                srcPoints.add(i, q)
                distance = 0F
            } else {
                distance += d
            }
            i++
        }

        while (newPoints.size < n) {
            newPoints.add(srcPoints.last())
        }
        return newPoints
    }

    private fun scaleTo(points: List<Point>, size: Float): List<Point> {
        val box = boundingBox(points)
        val maxDimension = max(box.width, box.height)
        val scale = if (maxDimension > 0.001F) {
            size / maxDimension
        } else {
            1F
        }

        return points.map { p ->
            Point(p.x * scale, p.y * scale)
        }
    }

    private fun translateTo(points: List<Point>, to: Point): List<Point> {
        val centroid = centroid(points)

        return points.map { p ->
            Point(
                x = p.x + to.x - centroid.x,
                y = p.y + to.y - centroid.y
            )
        }
    }

    private fun pathDistance(pts1: List<Point>, pts2: List<Point>): Float {
        val length = min(pts1.size, pts2.size)

        return pts1.zip(pts2)
            .take(length)
            .fold(0F) { acc, (p1, p2) -> acc + p1.distanceTo(p2) } / length
    }

    private fun pathLength(points: List<Point>): Float {
        return points.zipWithNext().fold(0F) { acc, (p1, p2) -> acc + p1.distanceTo(p2) }
    }

    private fun boundingBox(points: List<Point>): Box {
        var minX = Float.MAX_VALUE
        var maxX = Float.MIN_VALUE
        var minY = Float.MAX_VALUE
        var maxY = Float.MIN_VALUE

        points.forEach { p ->
            if (p.x < minX) minX = p.x
            if (p.x > maxX) maxX = p.x
            if (p.y < minY) minY = p.y
            if (p.y > maxY) maxY = p.y
        }

        return Box(minX, minY, maxX - minX, maxY - minY)
    }

    private fun centroid(points: List<Point>): Point {
        val (sumX, sumY) = points.fold(0F to 0F) { (x, y), p ->
            (x + p.x) to (y + p.y)
        }

        return Point(sumX / points.size, sumY / points.size)
    }

    private data class Template(
        val key: Char,
        val points: ImmutableList<Point>,
        val strokeCount: Int
    )

    data class Result(
        val key: Char,
        val score: Float
    )

    @Serializable
    sealed interface Error {

        @Serializable
        data object NoTemplateProvided : Error

        @Serializable
        data object TooManyStrokes : Error

        @Serializable
        data object NoStrokes : Error

        @Serializable
        data object NoMatch : Error
    }
}