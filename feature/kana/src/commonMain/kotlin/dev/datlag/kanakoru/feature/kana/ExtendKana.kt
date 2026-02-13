package dev.datlag.kanakoru.feature.kana

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.datlag.kanakoru.feature.kana.navigation.Kana
import dev.datlag.kanakoru.feature.level.navigation.Level
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.ui.NavBackStack

fun EntryProviderScope<NavKey>.featureKana(backStack: NavBackStack<NavKey>) {
    val onBack: () -> Unit = { backStack.pop() }
    val onKana: (JapaneseChar) -> Unit = { backStack.push(Level(it)) }

    entry<Kana> { KanaScreen(it, onBack, onKana) }
    entry<Kana.Hiragana> { KanaScreen(Kana.Hiragana, onBack, onKana) }
    entry<Kana.Katakana> { KanaScreen(Kana.Katakana, onBack, onKana) }
}