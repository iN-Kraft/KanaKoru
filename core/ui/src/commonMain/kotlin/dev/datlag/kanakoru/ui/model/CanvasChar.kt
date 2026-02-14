package dev.datlag.kanakoru.ui.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.vector.PathParser
import dev.datlag.kanakoru.dollarn.Point
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kommons.cache.EvictionPolicy
import dev.datlag.kommons.cache.InMemoryCache
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable

@Immutable
data class CanvasChar(
    val char: Char,
    val originalWidth: Float,
    val originalHeight: Float,
    val strokes: ImmutableList<Stroke>
) {

    val points = strokes.map { it.points }.toImmutableList()

    fun takeStrokes(count: Int): CanvasChar {
        val safeCount = count.coerceIn(0, strokes.size)

        return copy(
            strokes = this.strokes.take(safeCount).toImmutableList()
        )
    }

    fun splitAfterStrokes(count: Int): Pair<CanvasChar, CanvasChar> {
        val splitIndex = count.coerceIn(1, strokes.size)
        val chunkedStrokes = strokes.chunked(splitIndex)
        val staticPart = copy(
            strokes = chunkedStrokes.first().toImmutableList()
        )
        val activePart = copy(
            strokes = chunkedStrokes.drop(1).flatten().toImmutableList()
        )

        return staticPart to activePart
    }

    @Immutable
    data class Stroke(
        val index: Int,
        val path: Path,
        val points: ImmutableList<Point>,
        val startOffset: Offset
    )

    companion object {
        private val charCache = InMemoryCache<CharCacheKey, CanvasChar>(maxSize = 10) {
            evictionPolicy = EvictionPolicy.LRU
        }

        operator fun invoke(
            char: Char,
            width: Number,
            height: Number,
            rawPathData: List<String>
        ): CanvasChar {
            val cacheKey = CharCacheKey(char, width.toFloat(), height.toFloat())
            charCache.tryGet(cacheKey)?.let {
                return it
            }

            val measure = PathMeasure()
            val strokes = rawPathData.mapIndexed { index, pathData ->
                val path = PathParser().parsePathString(pathData).toPath()
                val points = convertPathToPoints(path, measure)
                val startOffset = points.firstOrNull()?.let { Offset(it.x, it.y) }

                Stroke(
                    index = index,
                    path = path,
                    points = points,
                    startOffset = startOffset ?: Offset.Unspecified,
                )
            }.toImmutableList()

            return CanvasChar(
                char = char,
                originalWidth = width.toFloat(),
                originalHeight = height.toFloat(),
                strokes = strokes
            ).also {
                charCache.tryPut(cacheKey, it)
            }
        }

        operator fun invoke(japaneseChar: JapaneseChar) = invoke(
            char = japaneseChar.value,
            width = japaneseChar.path.width,
            height = japaneseChar.path.height,
            rawPathData = japaneseChar.path.data
        )

        private fun convertPathToPoints(
            path: Path,
            measure: PathMeasure,
            sampleDistance: Float = 5F
        ): ImmutableList<Point> {
            measure.setPath(path, false)
            val length = measure.length

            if (length <= 0) {
                return persistentListOf()
            }

            val points = generateSequence(0F) { it + sampleDistance }
                .takeWhile { it < length }
                .map { distance ->
                    val offset = measure.getPosition(distance)
                    Point(offset.x, offset.y)
                }
                .toMutableList()

            val endOffset = measure.getPosition(length)
            points.add(Point(endOffset.x, endOffset.y))

            return points.toImmutableList()
        }

        @Serializable
        private data class CharCacheKey(
            val char: Char,
            val width: Float,
            val height: Float,
        )
    }
}
