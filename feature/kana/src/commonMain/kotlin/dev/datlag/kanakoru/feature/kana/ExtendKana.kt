package dev.datlag.kanakoru.feature.kana

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.datlag.kanakoru.feature.kana.navigation.Kana
import dev.datlag.kanakoru.ui.NavBackStack

fun EntryProviderScope<NavKey>.featureKana(backStack: NavBackStack<NavKey>) {
    val onBack: () -> Unit = { backStack.pop() }

    entry<Kana> { KanaScreen(it, onBack) }
    entry<Kana.Hiragana> { KanaScreen(Kana.Hiragana, onBack) }
    entry<Kana.Katakana> { KanaScreen(Kana.Katakana, onBack) }
}