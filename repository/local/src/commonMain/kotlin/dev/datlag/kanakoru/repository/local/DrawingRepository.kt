package dev.datlag.kanakoru.repository.local

import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import de.cketti.codepoints.deluxe.toCodePoint
import dev.datlag.kanakoru.model.JapaneseChar
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlin.coroutines.CoroutineContext
import kotlin.time.Clock

class DrawingRepository(
    private val localDataSource: KanaKoruDB,
    private val realtimeCoroutineContext: CoroutineContext
) : Comparator<DrawingTable?> {

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
            .distinctUntilChanged()
            .mapLatest { list ->
                list.mapNotNull { table ->
                    (hiraganaCodePointMap[table.char] ?: return@mapNotNull null) to table
                }.toMap().toImmutableMap()
            }.distinctUntilChanged()
    }

    val recommendedHiragana by lazy {
        hiraganaStats.mapLatest { statsMap ->
            JapaneseChar.Hiragana.chars.minWithOrNull { charA, charB ->
                compare(statsMap[charA], statsMap[charB])
            } ?: JapaneseChar.Hiragana.a
        }.distinctUntilChanged()
    }

    val katakanaStats by lazy {
        queries
            .getDrawingStatsForGroup(katakanaCodePointMap.keys)
            .asFlow()
            .mapToList(realtimeCoroutineContext)
            .distinctUntilChanged()
            .mapLatest { list ->
                list.mapNotNull { table ->
                    (katakanaCodePointMap[table.char] ?: return@mapNotNull null) to table
                }.toMap().toImmutableMap()
            }.distinctUntilChanged()
    }

    val recommendedKatakana by lazy {
        katakanaStats.mapLatest { statsMap ->
            JapaneseChar.Katakana.chars.minWithOrNull { charA, charB ->
                compare(statsMap[charA], statsMap[charB])
            } ?: JapaneseChar.Katakana.a
        }.distinctUntilChanged()
    }

    override fun compare(a: DrawingTable?, b: DrawingTable?): Int {
        val scoreA = if (a?.score?.isNaN() == true) 0.0 else a?.score ?: 0.0
        val scoreB = if (b?.score?.isNaN() == true) 0.0 else b?.score ?: 0.0

        val countA = a?.reviewCount ?: 0
        val countB = b?.reviewCount ?: 0

        if (countA <= 0 && countB > 0) {
            return -1
        }
        if (countB <= 0 && countA > 0) {
            return 1
        }

        val isWeakA = scoreA < 0.8
        val isWeakB = scoreB < 0.8

        if (isWeakA && !isWeakB) {
            return -1
        }
        if (isWeakB && !isWeakA) {
            return 1
        }

        if (isWeakA && isWeakB) {
            return scoreA.compareTo(scoreB)
        }

        val timeA = a?.lastReview ?: Long.MAX_VALUE
        val timeB = b?.lastReview ?: Long.MAX_VALUE

        return timeA.compareTo(timeB)
    }

    suspend fun saveProgress(char: JapaneseChar, score: Float) {
        val codePoint = char.value.toCodePoint()

        queries.transaction {
            val currentStats = queries.getDrawingStats(
                codePoint.value.toLong()
            ).awaitAsOneOrNull()

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
            ).await()
        }
    }

    companion object {
        private const val OLD_SCORE_MULTIPLIER = 0.3F
        private const val NEW_SCORE_MULTIPLIER = 0.7F
    }

}