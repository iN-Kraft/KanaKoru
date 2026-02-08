package dev.datlag.kanakoru.feature.kana

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.datlag.kanakoru.dollarn.Point
import dev.datlag.kanakoru.feature.kana.navigation.Kana
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.ui.NavBackStack
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun EntryProviderScope<NavKey>.featureKana(backStack: NavBackStack<NavKey>) {
    val onBack: () -> Unit = { backStack.pop() }
    val onKana: (JapaneseChar) -> Unit = { backStack.push(Kana.Draw(it)) }

    entry<Kana> { KanaScreen(it, onBack, onKana) }
    entry<Kana.Hiragana> { KanaScreen(Kana.Hiragana, onBack, onKana) }
    entry<Kana.Katakana> { KanaScreen(Kana.Katakana, onBack, onKana) }

    entry<Kana.Draw> { KanaDrawScreen(it.char, onBack) }
}

fun convertPathToPoints(paths: List<Path>, sampleDistance: Float = 5F): ImmutableList<ImmutableList<Point>> {
    val allStrokes = mutableListOf<ImmutableList<Point>>()
    val measure = PathMeasure()

    for (path in paths) {
        measure.setPath(path, false)
        val length = measure.length

        if (length > 0) {
            val currentStroke = mutableListOf<Point>()
            var distance = 0F

            while (distance < length) {
                val offset = measure.getPosition(distance)
                currentStroke.add(Point(offset.x, offset.y))
                distance += sampleDistance
            }

            val endOffset = measure.getPosition(length)
            currentStroke.add(Point(endOffset.x, endOffset.y))
            allStrokes.add(currentStroke.toImmutableList())
        }
    }

    return allStrokes.toImmutableList()
}