package dev.datlag.kanakoru.repository.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import de.cketti.codepoints.deluxe.toCodePoint
import dev.datlag.kanakoru.model.JapaneseChar
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.mapLatest
import kotlin.coroutines.CoroutineContext
import kotlin.time.Clock

class DrawingRepository(
    private val localDataSource: KanaKoruDB,
    private val realtimeCoroutineContext: CoroutineContext
) {

    private val queries: DrawingTableQueries
        get() = localDataSource.drawingTableQueries

    private val hiraganaCodePointMap by lazy {
        JapaneseChar.Hiragana.chars.associateBy {
            it.value.toCodePoint().value.toLong()
        }.toImmutableMap()
    }

    private val katakanaCodePointMap by lazy {
        JapaneseChar.Katakana.chars.associateBy {
            it.value.toCodePoint().value.toLong()
        }
    }

    val hiraganaStats by lazy {
        queries
            .getDrawingStatsForGroup(hiraganaCodePointMap.keys)
            .asFlow()
            .mapToList(realtimeCoroutineContext)
            .mapLatest { list ->
                list.mapNotNull { table ->
                    (hiraganaCodePointMap[table.char] ?: return@mapNotNull null) to table
                }.toMap().toImmutableMap()
            }
    }

    val katakanaStats by lazy {
        queries
            .getDrawingStatsForGroup(katakanaCodePointMap.keys)
            .asFlow()
            .mapToList(realtimeCoroutineContext)
            .mapLatest { list ->
                list.mapNotNull { table ->
                    (katakanaCodePointMap[table.char] ?: return@mapNotNull null) to table
                }.toMap().toImmutableMap()
            }
    }

    suspend fun saveProgress(char: JapaneseChar, score: Float) {
        val codePoint = char.value.toCodePoint()

        queries.transaction {
            val currentStats = queries.getDrawingStats(
                codePoint.value.toLong()
            ).executeAsOneOrNull()

            val newScore = if (currentStats == null) {
                score.toDouble()
            } else {
                val oldScore = currentStats.score ?: Double.NaN
                if (oldScore <= 0.0 || oldScore.isNaN()) {
                    score.toDouble()
                } else {
                    oldScore * OLD_SCORE_MULTIPLIER + score * NEW_SCORE_MULTIPLIER
                }
            }

            val newCount = currentStats?.reviewCount?.plus(1) ?: 1

            queries.upsertDrawingProgress(
                char = codePoint.value.toLong(),
                newScore = newScore,
                newCount = newCount,
                timestamp = Clock.System.now().epochSeconds
            )
        }
    }

    companion object {
        private const val OLD_SCORE_MULTIPLIER = 0.3F
        private const val NEW_SCORE_MULTIPLIER = 0.7F
    }

}